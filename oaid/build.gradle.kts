import com.android.plugin.version.lib
import com.shareware.versions.DependenciesScope

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("com.shareware.android")
//    id("org.jetbrains.dokka")
}

android {
    compileSdk = DependenciesScope.android.compileSdkVersion

    defaultConfig {
        minSdk = DependenciesScope.android.minSdkVersion
        targetSdk = DependenciesScope.android.targetSdkVersion

        testInstrumentationRunner = DependenciesScope.android.testInstrumentationRunner
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
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
        jvmTarget = JavaVersion.VERSION_1_8.toString()
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
    // 荣耀的最新版本广告标识服务SDK，参阅 https://developer.hihonor.com/cn/kitdoc?kitId=11030&navigation=guides&docId=intergrate.md
    implementation("com.hihonor.mcs:ads-identifier:1.0.2.301")
    // 华为的最新版本广告标识服务SDK，参阅 https://developer.huawei.com/consumer/cn/doc/HMSCore-Guides/identifier-service-version-change-history-0000001050066927
    implementation("com.huawei.hms:ads-identifier:3.4.62.300")
}

uploadArchive {
    groupId = "com.gitee.shareware"
    artifactId = "oaid"
    version = "1.0.4"
    mavenUrlOrLocalPath = "/Users/maven/repository"
}

//tasks.dokkaHtml.configure {
//    outputDirectory.set(buildDir.resolve("dokka"))
//}