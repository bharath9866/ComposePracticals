// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.7.1" apply false
    id("org.jetbrains.kotlin.android") version "2.0.21" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.20" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21" apply false
    id("com.android.library") version "8.7.1" apply false
    id("com.google.devtools.ksp") version "2.0.21-1.0.26" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://maven.google.com") }
        maven { url = uri("https://oss.sonatype.org") }
        maven { url = uri("https://jitpack.io") }
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-serialization:2.0.20")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.49")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}

// Custom task for deep cleaning the project
tasks.register("deepClean") {
    group = "build"
    description = "Deletes build directories to fix build issues"
    
    notCompatibleWithConfigurationCache("This task modifies build directories")
    
    doLast {
        // Delete all build directories in all projects
        allprojects.forEach { project ->
            delete(project.layout.buildDirectory)
        }
        
        println("Deep clean completed. Build directories have been cleaned.")
        println("Note: To clean .gradle directory, stop Gradle daemon with './gradlew --stop' first.")
        println("To clean the Gradle cache completely: './gradlew --stop' and manually delete '.gradle' folder")
    }
}

allprojects {
    tasks.withType<JavaCompile>().configureEach {
        options.isFork = true
    }
    
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        compilerOptions {
            freeCompilerArgs.addAll(
                "-opt-in=kotlin.RequiresOptIn",
                "-Xskip-prerelease-check",
                "-Xallow-unstable-dependencies"
            )
        }
    }
}