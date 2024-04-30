// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.10" apply false
    id("com.android.library") version "8.2.0" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven {
            url = uri("https://maven.google.com")
            url = uri("https://oss.sonatype.org")
            url = uri("https://jitpack.io")
        }
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.9.0")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.49")
    }
}