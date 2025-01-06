    plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "vn.edu.usth.mcma"
    compileSdk = 34

    defaultConfig {
        applicationId = "vn.edu.usth.mcma"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    dataBinding {
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

    // Thư viện Flexbox
    implementation("com.google.android.flexbox:flexbox:3.0.0")

    // Thư viện RecyclerView, CardView, Glide
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")

    // Thư viện Material Design
    implementation("com.google.android.material:material:1.5.0")

    // Thư viện khác
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Kiểm thử
    testImplementation(libs.junit)

    // Retrofit and OkHttp
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")

    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //helper
    compileOnly(libs.projectlombok.lombok)
    annotationProcessor(libs.projectlombok.lombok)

}
