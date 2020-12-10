plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    kotlin("plugin.serialization") version "1.4.20"
}

apply(from = "${project.rootDir}/codequality/ktlint.gradle.kts")

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.2")

    defaultConfig {
        applicationId = "co.softov.zero.android"
        minSdkVersion(24)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "0.1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

//    testOptions {
//        unitTests.isIncludeAndroidResources = true
//    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = true
            setProguardFiles(
                listOf(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            )
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
}

dependencies {

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.20")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.4.20")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.1.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.1")

    // Androidx
    implementation("androidx.core:core-ktx:1.5.0-alpha05")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.fragment:fragment-ktx:1.3.0-beta02")
    implementation("androidx.activity:activity-ktx:1.2.0-beta02")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("androidx.security:security-crypto:1.1.0-alpha03")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.2.0")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")

    // UI
    implementation("com.google.android.material:material:1.2.1")

    // Images
    implementation("io.coil-kt:coil:1.1.0")

    // Navigation
    implementation("com.github.terrakok:cicerone:6.5")

    // Logging
    implementation("com.jakewharton.timber:timber:4.7.1")

    // Testing
    testImplementation("junit:junit:4.13.1")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.29.1-alpha")
    kapt("com.google.dagger:hilt-android-compiler:2.29.1-alpha")
    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha02")
    kapt("androidx.hilt:hilt-compiler:1.0.0-alpha02")

    // Hilt Testing - instrumentation tests
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.29.1-alpha")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.29.1-alpha")
    kaptAndroidTest("androidx.hilt:hilt-compiler:1.0.0-alpha02")

    // Hilt Testing - local unit tests
    testImplementation("com.google.dagger:hilt-android-testing:2.29.1-alpha")
    kaptTest("com.google.dagger:hilt-android-compiler:2.29.1-alpha")
}