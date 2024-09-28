
plugins {
    alias(libs.plugins.shadow)
}

dependencies {
    implementation(project(":api"))
    implementation(libs.litecommands)
    implementation(libs.hikaricp)
    implementation(libs.h2)
    compileOnly(libs.vault)
}

tasks {
    shadowJar {
        archiveClassifier.set("")
    }
}
