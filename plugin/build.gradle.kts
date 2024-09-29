
plugins {
    alias(libs.plugins.shadow)
}

dependencies {
    implementation(project(":api"))
    implementation(libs.litecommands)
    implementation(libs.ormlite)
    implementation(libs.h2)
    compileOnly(libs.vault)
}

tasks {
    shadowJar {
        archiveBaseName.set(rootProject.name)
        archiveClassifier.set("")
    }
}
