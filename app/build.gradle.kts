plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.plugin.serialization)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.adaptivestreamingplayer"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.adaptivestreamingplayer"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.example.adaptivestreamingplayer.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
        debug {
            isDebuggable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        dataBinding = true
        viewBinding = true
        buildConfig = true
    }
    dataBinding {
        enable = true
    }
    viewBinding {
        enable = true
    }
    lint {
        abortOnError = false
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    flavorDimensions += "adaptivestreaming"
    productFlavors {
        create("prod") {
            dimension = "adaptivestreaming"
            buildConfigField("String", "SL_WEBSOCKET", "${properties["PROD_SL_WEBSOCKET"]}")
            buildConfigField("String", "SELFLEARN_BASE_URL", "${properties["PROD_SL_BASE_URL"]}")
            buildConfigField("String", "GOAL_SETTING", "${properties["PROD_GOAL_SETTING"]}")
            buildConfigField("String", "CMS_BASE_URL", "${properties["PROD_CMS_BASE_URL"]}")
            buildConfigField("String", "UAM_BASE_URL", "${properties["PROD_UAM_BASE_URL"]}")
            buildConfigField("String", "NEW_QB_BASE_URL", "${properties["PROD_NEW_QB_BASE_URL"]}")
            buildConfigField("String", "NEW_ASM_BASE_URL", "${properties["PROD_NEW_ASM_BASE_URL"]}")
            buildConfigField("String", "MY_ACTIVITY_BASE_URL", "${properties["PROD_MY_ACTIVITY_BASE_URL"]}")
            buildConfigField("String", "HEAP_APP_ID", "${properties["HEAP_KEY_PROD"]}")
            buildConfigField("String", "GATEWAY_BASE_URL", "${properties["PROD_GATEWAY_BASE_URL"]}")
            buildConfigField("String", "API_KEY", "${properties["API_KEY"]}")
        }
        create("preprod") {
            dimension = "adaptivestreaming"
            buildConfigField("String", "SL_WEBSOCKET", "${properties["PREPROD_SL_WEBSOCKET"]}")
            buildConfigField("String", "SELFLEARN_BASE_URL", "${properties["PREPROD_SL_BASE_URL"]}")
            buildConfigField("String", "GOAL_SETTING", "${properties["PREPROD_GOAL_SETTING"]}")
            buildConfigField("String", "CMS_BASE_URL", "${properties["PREPROD_CMS_BASE_URL"]}")
            buildConfigField("String", "UAM_BASE_URL", "${properties["PREPROD_UAM_BASE_URL"]}")
            buildConfigField("String", "NEW_QB_BASE_URL", "${properties["PREPROD_NEW_QB_BASE_URL"]}")
            buildConfigField("String", "NEW_ASM_BASE_URL", "${properties["PREPROD_NEW_ASM_BASE_URL"]}")
            buildConfigField("String", "MY_ACTIVITY_BASE_URL", "${properties["PREPROD_MY_ACTIVITY_BASE_URL"]}")
            buildConfigField("String", "HEAP_APP_ID", "${properties["HEAP_KEY_DEV"]}")
            buildConfigField("String", "GATEWAY_BASE_URL", "${properties["PREPROD_GATEWAY_BASE_URL"]}")
            buildConfigField("String", "API_KEY", "${properties["API_KEY"]}")
        }
        create("staging") {
            dimension = "adaptivestreaming"
            buildConfigField("String", "SL_WEBSOCKET", "${properties["STAG_SL_WEBSOCKET"]}")
            buildConfigField("String", "SELFLEARN_BASE_URL", "${properties["STAG_SL_BASE_URL"]}")
            buildConfigField("String", "CMS_BASE_URL", "${properties["STAG_CMS_BASE_URL"]}")
            buildConfigField("String", "UAM_BASE_URL", "${properties["STAG_UAM_BASE_URL"]}")
            buildConfigField("String", "NEW_QB_BASE_URL", "${properties["STAG_NEW_QB_BASE_URL"]}")
            buildConfigField("String", "NEW_ASM_BASE_URL", "${properties["STAG_NEW_ASM_BASE_URL"]}")
            buildConfigField("String", "MY_ACTIVITY_BASE_URL", "${properties["STAGING_MY_ACTIVITY_BASE_URL"]}")
            buildConfigField("String", "HEAP_APP_ID", "${properties["HEAP_KEY_DEV"]}")
            buildConfigField("String", "GOAL_SETTING", "${properties["STAG_GOAL_SETTING"]}")
            buildConfigField("String", "GATEWAY_BASE_URL", "${properties["STAG_GATEWAY_BASE_URL"]}")
            buildConfigField("String", "API_KEY", "${properties["API_KEY"]}")
        }
    }
}

dependencies {

    implementation(project(":ImageCoil"))

    // Implementation dependencies
    implementation(libs.appcompat)
    implementation(libs.places)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.text.google.fonts)
    implementation(libs.compose.ui.graphics)
    implementation(libs.navigation.compose)

    // Build-In
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(libs.foundation)
    implementation(libs.accompanist.pager.indicator)
    implementation(platform(libs.compose.bom))
    implementation(libs.material3)

    // Video Player
    implementation(libs.media3.exoplayer)
    implementation(libs.media3.ui)
    implementation(libs.media3.exoplayer.dash)
    implementation(libs.media3.exoplayer.hls)
    implementation(libs.media3.cast)
    implementation(libs.media3.datasource.cronet)
    implementation(libs.media3.common)

    // Memory Card
    implementation(libs.cardview)
    implementation(libs.glide)
    annotationProcessor(libs.glide.compiler)
    annotationProcessor(libs.glide.ksp)
    //implementation(libs.glidetovectoryou)
    implementation(libs.retrofit2.converter.gson)
    implementation(libs.lottie)
    implementation(libs.material)

    // Ktor
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.logging)
    implementation(libs.logback.classic)
    implementation(libs.ktor.client.gson)
    implementation(libs.kotlinx.serialization.json)

    // Dagger - Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.foundation.android)
    ksp(libs.hilt.android.compiler)
    ksp(libs.hilt.compiler.androidx)
    implementation(libs.hilt.navigation.compose)

    implementation(libs.material.icons.extended)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.ui.viewbinding)

    // Coil Library
    implementation(libs.coil.compose)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Coroutine Lifecycle Scopes
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.runtime.ktx)

    // Navigation Components
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

    // Activity KTX for viewModels()
    implementation(libs.activity.ktx)

    // Timber
    implementation(libs.timber)

    // Room
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)

    // Local Unit Tests
    implementation(libs.test.core)
    testImplementation(libs.junit)
    testImplementation(libs.hamcrest.all)
    testImplementation(libs.arch.core.testing)
    testImplementation(libs.robolectric)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.google.truth)
    testImplementation(libs.mockito.core)
    testImplementation("io.mockk:mockk:1.13.8")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.9.0")

    // Instrumented Unit Tests
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.arch.core.testing)
    androidTestImplementation(libs.google.truth)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.ui.test.manifest)
    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.android.compiler)

    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)

    implementation(libs.libphonenumber)
    testImplementation(libs.libphonenumber)
    androidTestImplementation(libs.libphonenumber)

    implementation(libs.compose.unstyled)

}