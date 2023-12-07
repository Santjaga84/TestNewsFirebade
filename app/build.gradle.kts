plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("com.google.devtools.ksp")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.testnewsapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.testnewsapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures{
        viewBinding = true
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
    kotlinOptions {
        jvmTarget = "1.8"
    }

}

dependencies {

    implementation(platform("com.google.firebase:firebase-bom:32.6.0"))

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-firestore:24.9.1")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.5")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation ("androidx.fragment:fragment-ktx:1.4.0")

    // Lifecycle

    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")

    // Room

    implementation ("androidx.room:room-runtime:2.4.2")
    implementation ("androidx.room:room-ktx:2.4.2")
    ksp ("androidx.room:room-compiler:2.4.2")

    // Glide

    implementation ("com.github.bumptech.glide:glide:4.13.0")
    ksp ("com.github.bumptech.glide:compiler:4.13.0")

    // Navigation Components

    implementation ("androidx.navigation:navigation-fragment-ktx:2.6.0")
    implementation ("androidx.navigation:navigation-ui-ktx:2.6.0")
}