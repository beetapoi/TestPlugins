package com.cloudstream.extensions.samehadaku

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.api.*
import org.jsoup.Jsoup

class SamehadakuProvider : MainAPI() {
    override var name = "Samehadaku"

    override var mainUrl = "https://samehadaku.college"
    override var lang = "id"
    override val hasMainPage = true

    override val mainPage = mainPageOf(
        "$mainUrl/anime-terbaru/" to "Anime Terbaru"
    )

    override suspend fun search(query: String): List<SearchResponse> {
        val url = "$mainUrl/?s=$query"
        val document = app.get(url).document
        return document.select("div.post-thumbnail").mapNotNull {
            val title = it.selectFirst("img")?.attr("alt") ?: return@mapNotNull null
            val href = it.selectFirst("a")?.attr("href") ?: return@mapNotNull null
            val posterUrl = it.selectFirst("img")?.attr("src")
            MovieSearchResponse(
                title = title,
                url = href,
                apiName = this.name,
                posterUrl = posterUrl,
                year = null
            )
        }
    }

    override suspend fun load(url: String): LoadResponse? {
        val document = app.get(url).document
        val title = document.selectFirst("h1.entry-title")?.text() ?: return null
        val posterUrl = document.selectFirst("img.wp-post-image")?.attr("src")
        val episodes = document.select("div.episode-box a").mapNotNull {
            val episodeTitle = it.text()
            val episodeUrl = it.attr("href")
            Episode(episodeUrl, name = episodeTitle)
        }
        return AnimeLoadResponse(
            title = title,
            url = url,
            apiName = this.name,
            posterUrl = posterUrl,
            episodes = episodes
        )
    }
}
