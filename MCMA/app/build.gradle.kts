    plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "vn.edu.usth.mcma"
    compileSdk = 34

    defaultConfig {
        applicationId = "vn.edu.usth.mcma"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    dataBinding{
        enable = true
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)

    implementation("com.github.bumptech.glide:glide:4.16.0")

    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}