plugins {
    id("kotlin")
}

dependencies {
    implementation(project(":core"))
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("org.jsoup:jsoup:1.15.4")
}

cloudstream {
    language = "id"
    iconUrl = "https://samehadaku.college/favicon.ico"
    summary = "Ekstensi untuk streaming konten dari Samehadaku"
    authors = listOf("beetapoi") // Ganti dengan nama Anda
    status = 1 // 1 = stabil, 0 = beta, -1 = tidak aktif
}
