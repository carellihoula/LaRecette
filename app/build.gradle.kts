plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id ("kotlin-parcelize")

}

android {
    namespace = "com.example.larecette"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.larecette"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    var nav_version = "2.3.5"
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //  navigation dependencies with kotlin
    implementation ("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation ("androidx.navigation:navigation-ui-ktx:$nav_version")

    // Dépendance pour utiliser les unités SDP (Scalable Size Unit) fournies par Intuit
    // Permet de définir des dimensions scalables pour une mise en page réactive.
    implementation ("com.intuit.sdp:sdp-android:1.0.6")

    // Dépendance pour utiliser les unités SSP (Scalable Size Unit for Text) fournies par Intuit
    // Permet de définir des tailles de texte scalables pour une mise en page réactive.
    implementation ("com.intuit.ssp:ssp-android:1.0.6")

    //Dépendance pour afficher des GIFs animés dans l'application
    implementation ("pl.droidsonroids.gif:android-gif-drawable:1.2.17")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.github.bumptech.glide:glide:4.11.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.11.0")
    implementation ("androidx.cardview:cardview:1.0.0")


}