// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.kotlin.plugin.serialization) apply false
    alias(libs.plugins.compose.compiler) apply false
    id("com.android.library") version "8.7.1" apply false
    alias(libs.plugins.ksp) apply false
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
        classpath("org.jetbrains.kotlin:kotlin-serialization:2.0.20")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.49")
    }
}