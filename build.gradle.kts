// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.org.jetbrains.kotlin.kapt) apply false
    alias(libs.plugins.hilt) apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.10" apply false
    id("org.jetbrains.kotlin.jvm") version "1.8.20"
    `maven-publish`
}

extra.apply {
    set("compileSdk", 34)
    set("minSdk", 26)
    set("targetSdk", 34)
    set("buildToolsVersion", "30.0.3")
    set("compose_version", "1.6.0")
    set("room_version", "2.5.1")
    set("hiltVersion", "2.46")
    set("nav_version", "2.8.0-alpha08")
    set("navigationVersion", "2.4.0-alpha06")
    set("repositoryVersion", "cf300ac841")
    set("designVersion", "a7236c358c")
    set("themeVersion", "28a1702347")
}

/*repositories {
    mavenCentral()
}*/

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

kotlin {
    jvmToolchain(8)
}