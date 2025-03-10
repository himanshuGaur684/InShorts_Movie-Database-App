plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
//    alias(libs.plugins.ksp)
    id("kotlin-parcelize")
    kotlin("kapt")
    alias(libs.plugins.dagger)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "dev.himanshu.inshorts.task.moviedatabaseapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "dev.himanshu.inshorts.task.moviedatabaseapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // optional - RxJava3 support for Room
    implementation(libs.androidx.room.rxjava3)
    kapt(libs.androidx.room.compiler)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler) // Use ksp instead of kapt for Hilt

    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.adapter.rxjava3)
    implementation(libs.rxjava)
    implementation(libs.rxandroid)

    implementation(libs.navigation.compose)
    implementation(libs.hilt.compose.navigation)

    implementation(libs.kotlinx.serialization)

    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.compose)

    // coil image loading
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    implementation("com.google.accompanist:accompanist-pager-indicators:0.36.0")
}
