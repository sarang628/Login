plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-android")
    id("kotlin-kapt")
    alias(libs.plugins.hilt)
    id("maven-publish")
    id("kotlinx-serialization")
}

android {
    compileSdk = rootProject.extra["compileSdk"] as Int
    namespace = "com.sarang.torang"
    defaultConfig {
        minSdk = rootProject.extra["minSdk"] as Int
        targetSdk = rootProject.extra["targetSdk"] as Int
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunner = "com.sarang.torang.CustomTestRunner"
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
        targetCompatibility = JavaVersion.VERSION_17
        sourceCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.6"
    }
}

dependencies {
    // HILT
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose) // hiltViewModel

    // Testing Start
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation(libs.kotlinx.coroutines.test) // coroutines unit test
    androidTestImplementation(libs.androidx.ui.test.junit4) // Test rules and transitive dependencies
    debugImplementation(libs.androidx.ui.test.manifest) // Needed for createAndroidComposeRule, but not createComposeRule
    testImplementation(libs.mockito.core) // Mockito
    testImplementation(libs.mockito.inline)
    testImplementation(libs.kotlinx.coroutines.test) // Coroutine Test
    testImplementation(libs.androidx.core.testing) // AndroidX Core Testing
    // Testing End

    // Compose
    androidTestImplementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui) //없으면 @Composable import 안됨
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview) // Android Studio Preview support
    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.material3) //JetNews Main 따라하기
    implementation(libs.androidx.material3.windows.size)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // Navigation start
    // Kotlin
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Feature module Support
    implementation(libs.androidx.navigation.dynamic.features.fragment)

    // Testing Navigation
    androidTestImplementation(libs.androidx.navigation.testing)

    // Jetpack Compose Integration
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    // Navigation end

    implementation(libs.androidx.constraintlayout.compose)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = "com.github.jitpack"
                artifactId = "android-example"
                version = "1.0"
            }
        }
    }
}