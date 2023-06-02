plugins {
    id("com.android.application")
    id("kotlinx-serialization")
    kotlin("android")
    kotlin("kapt")
}

val name = "top.chilfish.labs"

android {
    namespace = name
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = name
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        dataBinding = true
    }
    
    buildToolsVersion = "33.0.2"
}

dependencies {
    implementation(libs.androidx.ktx)
    implementation(libs.kotlin.stdlib)
    implementation(libs.coroutines)
    implementation(libs.coroutines.core)
    implementation(libs.serialization)

    //UI
    implementation(libs.material)
    implementation(libs.material3)

    implementation(libs.coil)
    implementation(libs.appcompat)


    // Jetpack
    implementation(libs.lifecycle)
    implementation(libs.lifecycle.viewModel)

    implementation(libs.navigation)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    implementation(libs.dataStore)
    implementation(libs.dataStore.preferences)

    // room
    implementation(libs.room)
    annotationProcessor(libs.room.compiler)
    kapt(libs.room.compiler)
    implementation(libs.room.ktx)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.androidx.hilt.compiler)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.okhttp)
    implementation(libs.liangjingkanji.net)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.espresso)
}