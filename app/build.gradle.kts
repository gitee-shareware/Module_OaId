import com.android.plugin.version.lib
import com.shareware.versions.DependenciesScope

plugins {
    id("com.android.application")
    kotlin("android")
    id("com.shareware.android")
}

android {
    namespace = "com.shareware.oaid.demo"
    compileSdk = DependenciesScope.android.compileSdkVersion

    defaultConfig {
        applicationId = "com.shareware.oaid.demo"
        minSdk = DependenciesScope.android.minSdkVersion
        targetSdk = DependenciesScope.android.targetSdkVersion
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = DependenciesScope.android.testInstrumentationRunner

        multiDexEnabled = true
    }

    buildFeatures {
        viewBinding = true
    }

    signingConfigs {
        getByName("release") {
            storeFile = file("./keystore.jks")
            isV1SigningEnabled = true
            isV2SigningEnabled = true
        }
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }

        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_11)
        targetCompatibility(JavaVersion.VERSION_11)
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    testImplementation(lib(DependenciesScope.android.androidJunit))
    androidTestImplementation(lib(DependenciesScope.android.androidTestJunit))
    androidTestImplementation(lib(DependenciesScope.android.androidTestEspresso))

    implementation("${DependenciesScope.android.kotlinVersion}")
    implementation("${DependenciesScope.android.androidCoreKtx}")
    implementation("${DependenciesScope.android.androidAppCompat}")
    implementation("${DependenciesScope.android.androidMaterial}")
    implementation("${DependenciesScope.android.androidConstraint}")
    implementation("${DependenciesScope.android.androidMultidex}")
    implementation("${DependenciesScope.lib.okhttp3LoggingInterceptor}")
}