plugins {
    id("com.android.application")
    id("kotlin-android")
    // Plugin Flutter harus diterapkan setelah Android dan Kotlin
    id("dev.flutter.flutter-gradle-plugin")
}

android {
    namespace = "com.example.app"
    compileSdk = flutter.compileSdkVersion   // Versi SDK untuk compile
    ndkVersion = flutter.ndkVersion

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    defaultConfig {
        applicationId = "com.example.app"    // Application ID unik
        minSdk = flutter.minSdkVersion       // Minimum Android SDK yang didukung
        targetSdk = flutter.targetSdkVersion // Target Android SDK
        versionCode = flutter.versionCode    // Nomor versi internal
        versionName = flutter.versionName    // Nomor versi yang ditampilkan ke user
    }

    buildTypes {
        getByName("release") {
            // Menggunakan debug signing sementara
            signingConfig = signingConfigs.getByName("debug")
            // Disable minify dan shrinkResources dengan Kotlin DSL
            isMinifyEnabled = false
            isShrinkResources = false
        }
    }

    // Build modern: viewBinding
    buildFeatures {
        viewBinding = true
    }
}

flutter {
    // Path ke project Flutter
    source = "../.."
}
