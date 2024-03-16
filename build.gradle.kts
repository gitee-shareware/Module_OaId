import com.android.plugin.version.versions
import com.shareware.versions.DependenciesScope

buildscript {

    val pluginCommon by extra("com.gitee.shareware:plugin:1.0.0")
    val pluginGradle by extra("com.android.tools.build:gradle:7.4.2")
    val pluginKotlin by extra("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.22")

    // 基础库版本依赖
    val pluginVersion by extra("com.gitee.shareware:versions-base:1.0.7")

    repositories {
//        maven(uri("file://${File("/Users/maven/repository").absolutePath}"))
        maven("https://mirrors.cloud.tencent.com/nexus/repository/maven-public")
        maven("https://jitpack.io")
//        maven("https://developer.huawei.com/repo/")
        //阿里云Center兜底
        maven("https://maven.aliyun.com/repository/google")
        maven("https://maven.aliyun.com/repository/gradle-plugin")
        maven("https://maven.aliyun.com/repository/central")
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        classpath(pluginKotlin)
        classpath(pluginGradle)
        classpath(pluginCommon)
        classpath(pluginVersion)
    }
}

allprojects {
    repositories {
//        maven(uri("file://${File("/Users/maven/repository").absolutePath}"))
        maven("https://mirrors.cloud.tencent.com/nexus/repository/maven-public")
//        maven("https://storage.flutter-io.cn/download.flutter.io")
        maven("https://developer.huawei.com/repo/")
        maven("https://developer.hihonor.com/repo/")
        maven("https://jitpack.io")
        //阿里云Center兜底
        maven("https://maven.aliyun.com/repository/google")
        maven("https://maven.aliyun.com/repository/gradle-plugin")
        maven("https://maven.aliyun.com/repository/central")
        google()
        mavenCentral()
    }
//    configurations.all {
//        // 强制使用版本
//        resolutionStrategy.force("androidx.annotation:annotation:1.1.0")
//    }
}

afterEvaluate {
    //COMMON
    "${DependenciesScope.android.kotlinVersion versions ("1.7.22")}"
}