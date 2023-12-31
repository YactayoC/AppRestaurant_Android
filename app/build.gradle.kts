@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
  alias(libs.plugins.androidApplication)
  alias(libs.plugins.kotlinAndroid)
}

android {
  namespace = "com.sebasdev.apprestaurant_android"
  compileSdk = 33

  defaultConfig {
    applicationId = "com.sebasdev.apprestaurant_android"
    minSdk = 24
    targetSdk = 33
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    vectorDrawables {
      useSupportLibrary = true
    }
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.4.3"
  }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

dependencies {

  implementation(libs.core.ktx)
  implementation(libs.lifecycle.runtime.ktx)
  implementation(libs.activity.compose)
  implementation(platform(libs.compose.bom))
  implementation(libs.ui)
  implementation(libs.ui.graphics)
  implementation(libs.ui.tooling.preview)
  implementation(libs.material3)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.test.ext.junit)
  androidTestImplementation(libs.espresso.core)
  androidTestImplementation(platform(libs.compose.bom))
  androidTestImplementation(libs.ui.test.junit4)
  debugImplementation(libs.ui.tooling)
  debugImplementation(libs.ui.test.manifest)

  implementation("androidx.compose.material:material-icons-extended-android:1.5.0-beta01")
  implementation("androidx.navigation:navigation-compose:2.5.3")
  implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
  implementation("com.squareup.retrofit2:retrofit:2.9.0")
  implementation("com.squareup.retrofit2:converter-gson:2.9.0")
  implementation("androidx.compose.runtime:runtime-livedata:1.4.3")
  implementation("io.coil-kt:coil-compose:1.3.0")
  implementation("androidx.datastore:datastore-preferences:1.0.0")
  implementation("androidx.datastore:datastore-rxjava2:1.0.0")
  implementation("androidx.datastore:datastore-rxjava3:1.0.0")
  implementation("com.maxkeppeler.sheets-compose-dialogs:core:1.0.2")
  implementation("com.maxkeppeler.sheets-compose-dialogs:calendar:1.0.2")
}