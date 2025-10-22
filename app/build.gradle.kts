import java.io.FileInputStream
import java.util.Properties

val localProperties =
  Properties().apply {
    val localFile = rootProject.file("local.properties")
    if (localFile.exists()) {
      load(FileInputStream(localFile))
    }
  }

val authToken: String = localProperties.getProperty("auth_token", "")

plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.compose)
  id("com.google.devtools.ksp")
  id("com.google.dagger.hilt.android")
}

android {
  namespace = "com.manishjajoriya.moctale"
  compileSdk = 36

  defaultConfig {
    applicationId = "com.manishjajoriya.moctale"
    minSdk = 26
    targetSdk = 36
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    android.buildFeatures.buildConfig = true
    buildConfigField("String", "AUTH_TOKEN", "\"$authToken\"")
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
  }
  buildFeatures { compose = true }
}

//noinspection UseTomlInstead
dependencies {

  // -------------------- viewmodel --------------------
  implementation("androidx.lifecycle:lifecycle-viewmodel:2.9.4")

  // -------------------- navigation --------------------
  implementation("androidx.navigation:navigation-compose:2.9.5")

  // -------------------- retrofit --------------------
  implementation("com.squareup.retrofit2:retrofit:3.0.0")
  implementation("com.squareup.retrofit2:converter-gson:3.0.0")

  // -------------------- coil --------------------
  implementation("io.coil-kt.coil3:coil-compose:3.3.0")
  implementation("io.coil-kt.coil3:coil-network-okhttp:3.3.0")

  // -------------------- dagger hilt --------------------
  implementation("com.google.dagger:hilt-android:2.57.2")
  ksp("com.google.dagger:hilt-android-compiler:2.57.2")
  implementation("androidx.hilt:hilt-navigation-compose:1.3.0")

  // -------------------- android chart --------------------
  implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

  // -------------------- pagination --------------------
  implementation("androidx.paging:paging-runtime:3.3.6")
  implementation("androidx.paging:paging-compose:3.3.6")

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.compose.ui)
  implementation(libs.androidx.compose.ui.graphics)
  implementation(libs.androidx.compose.ui.tooling.preview)
  implementation(libs.androidx.compose.material3)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.compose.ui.test.junit4)
  debugImplementation(libs.androidx.compose.ui.tooling)
  debugImplementation(libs.androidx.compose.ui.test.manifest)
}
