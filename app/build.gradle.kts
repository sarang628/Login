plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.6"
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_17
        sourceCompatibility = JavaVersion.VERSION_17
    }

    hilt {
        enableTransformForLocalTests = true
    }
}

dependencies {
    /** HILT */
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose) // hiltViewModel

    /** Retrofit */
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    /** Room */
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    implementation(libs.androidx.room.paging)

    /** TEST Start */
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation(libs.kotlinx.coroutines.test) // coroutines unit test
    androidTestImplementation(libs.androidx.ui.test.junit4) // Test rules and transitive dependencies
    debugImplementation(libs.androidx.ui.test.manifest) // Needed for createAndroidComposeRule, but not createComposeRule

    // Hilt Start
    // For Robolectric tests.
    testImplementation(libs.hilt.android.testing)
    kaptTest(libs.hilt.android.compiler)
    testAnnotationProcessor(libs.hilt.android.compiler)

    // For instrumented tests.
    androidTestImplementation(libs.hilt.android.testing)
    kaptAndroidTest(libs.hilt.android.compiler)
    androidTestAnnotationProcessor(libs.hilt.android.compiler)
    // Hilt End
    /** TEST End */

    /** Compose */
    androidTestImplementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui) //없으면 @Composable import 안됨
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview) // Android Studio Preview support
    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.material3) //JetNews Main 따라하기
    implementation(libs.androidx.material3.windows.size)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.navigation.compose)
    androidTestImplementation(libs.ui.test.junit4) //runTest
    debugImplementation(libs.ui.test.manifest) // Needed for createAndroidComposeRule, but not createComposeRule:

    /** Navigation start */
    // Kotlin
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Feature module Support
    implementation(libs.androidx.navigation.dynamic.features.fragment)

    // Testing Navigation
    androidTestImplementation(libs.androidx.navigation.testing)

    // Jetpack Compose Integration
    implementation(libs.androidx.navigation.compose)
    /** Navigation end */

    implementation(project(":library"))

    implementation(libs.torangrepository)
    implementation(libs.theme)
}