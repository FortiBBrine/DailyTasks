plugins {
    alias(libs.plugins.kotlin.jvm)
    `java-library`
}

allprojects {

    apply {
        val libs = rootProject.libs

        plugin(libs.plugins.kotlin.jvm.get().pluginId)
        plugin("java-library")
    }

    group = "me.fortibrine"
    version = "1.0"

    repositories {
        mavenCentral()

        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://repo.panda-lang.org/releases")
        maven("https://jitpack.io")
    }

    tasks {
        withType<JavaCompile>().configureEach {
            options.encoding = "UTF-8"
            targetCompatibility = "1.8"
            sourceCompatibility = "1.8"
        }

        withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    dependencies {
        val libs = rootProject.libs

    }

}