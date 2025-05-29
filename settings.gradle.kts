pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        id("com.android.application") version "8.7.1"
        id("com.android.library") version "8.7.1"
        id("org.jetbrains.kotlin.android") version "2.0.21"
        id("org.jetbrains.kotlin.plugin.serialization") version "2.0.20"
        id("org.jetbrains.kotlin.plugin.compose") version "2.0.21"
        id("com.google.devtools.ksp") version "2.0.21-1.0.26"
        id("com.google.dagger.hilt.android") version "2.49"
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
    versionCatalogs {
        create("libs") {
        }
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "AdaptiveStreamingPlayer"
include(":app")
include(":ImageCoil")
include(":qrcodescanner")
