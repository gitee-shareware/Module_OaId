package com.shareware.oaid.util

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.telephony.TelephonyManager
import java.util.*


/**
 * desc: 功能描述
 * email: mobiledeveloper@qq.com
 */

@SuppressLint("PrivateApi")
fun sysProperty(key: String): String {
    return try {
        val clazz = Class.forName("android.os.SystemProperties") ?: return ""
        val method = clazz.getMethod("get", String::class.java, String::class.java)
        method.invoke(null, key, "") as? String ?: ""
    } catch (ignore: Throwable) {
        ""
    }
}

/**
 * 华为、荣耀、刷机后的Emui
 */
fun isHuawei(): Boolean {
    return Build.MANUFACTURER.equals("HUAWEI", ignoreCase = true) ||
            Build.BRAND.equals("HUAWEI", ignoreCase = true)
}

/**
 * 是否是荣耀手机
 */
fun isHonor(): Boolean {
    return Build.MANUFACTURER.equals("HONOR", ignoreCase = true) ||
            Build.BRAND.equals("HONOR", ignoreCase = true)
}

/**
 * 是否是刷机后的EMUI系统，或者是NOVA等手机
 */
fun isEmui(): Boolean {
    return sysProperty("ro.build.version.emui").isNotEmpty()
}

/**
 * 维沃手机、爱酷手机
 */
fun isViVo(): Boolean {
    return Build.MANUFACTURER.equals("VIVO", ignoreCase = true) ||
            Build.BRAND.equals("VIVO", ignoreCase = true) ||
            sysProperty("ro.vivo.os.version").isNotEmpty()
}

/**
 * 欧珀手机、真我手机
 */
fun isOppo(): Boolean {
    return Build.MANUFACTURER.equals("OPPO", ignoreCase = true) ||
            Build.BRAND.equals("OPPO", ignoreCase = true) ||
            Build.BRAND.equals("REALME", ignoreCase = true) ||
            sysProperty("ro.build.version.opporom").isNotEmpty()
}

/**
 * 小米手机、红米手机
 */
fun isXiaomi(): Boolean {
    return Build.MANUFACTURER.equals("XIAOMI", ignoreCase = true) ||
            Build.BRAND.equals("XIAOMI", ignoreCase = true) ||
            Build.BRAND.equals("REDMI", ignoreCase = true)
}

/**
 * 是否是刷机后的MIUI系统
 */
fun isMiUi(): Boolean {
    return sysProperty("ro.miui.ui.version.name").isNotEmpty()
}

/**
 * 一加手机
 */
fun isOnePlus(): Boolean {
    return Build.MANUFACTURER.equals("ONEPLUS", ignoreCase = true) ||
            Build.BRAND.equals("ONEPLUS", ignoreCase = true)
}

/**
 * 三星手机
 */
fun isSamsung(): Boolean {
    return Build.MANUFACTURER.equals("SAMSUNG", ignoreCase = true) ||
            Build.BRAND.equals("SAMSUNG", ignoreCase = true)
}

/**
 * 魅族手机
 */
fun isMeiZu(): Boolean {
    return Build.MANUFACTURER.equals("MEIZU", ignoreCase = true) ||
            Build.BRAND.equals("MEIZU", ignoreCase = true) ||
            Build.DISPLAY.uppercase().contains("FLYME")
}

/**
 * 努比亚手机
 */
fun isNubia(): Boolean {
    return Build.MANUFACTURER.equals("NUBIA", ignoreCase = true) ||
            Build.BRAND.equals("NUBIA", ignoreCase = true)
}

/**
 * 黑鲨手机
 */
fun isBlackShark(): Boolean {
    return Build.MANUFACTURER.equals("BLACKSHARK", ignoreCase = true) ||
            Build.BRAND.equals("BLACKSHARK", ignoreCase = true)
}

/**
 * 联想手机、乐檬手机
 */
fun isLenovo(): Boolean {
    return Build.MANUFACTURER.equals("LENOVO", ignoreCase = true) ||
            Build.BRAND.equals("LENOVO", ignoreCase = true) ||
            Build.BRAND.equals("ZUK", ignoreCase = true)
}

/**
 * 华硕手机
 */
fun isASUS(): Boolean {
    return Build.MANUFACTURER.equals("ASUS", ignoreCase = true) ||
            Build.BRAND.equals("ASUS", ignoreCase = true)
}

/**
 * 中兴手机
 */
fun isZTE(): Boolean {
    return Build.MANUFACTURER.equals("ZTE", ignoreCase = true) ||
            Build.BRAND.equals("ZTE", ignoreCase = true)
}

/**
 * 摩托罗拉手机
 */
fun isMotoLora(): Boolean {
    return Build.MANUFACTURER.equals("MOTOLORA", ignoreCase = true) ||
            Build.BRAND.equals("MOTOLORA", ignoreCase = true)
}

/**
 * 酷派手机
 */
fun isCoolPad(): Boolean {
    return Build.MANUFACTURER.equals("COOLPAD", ignoreCase = true) ||
            Build.BRAND.equals("COOLPAD", ignoreCase = true)
}

/**
 * 卓易手机
 */
fun isFreeMe(): Boolean {
    return sysProperty("ro.build.freeme.label").isNotEmpty()
}

/**
 * 酷赛手机
 */
fun isCooSea(): Boolean {
    return sysProperty("ro.odm.manufacturer").equals("PRIZE", ignoreCase = true)
}

/**
 * 判断当前是否为鸿蒙系统
 *
 * @return 是否是鸿蒙系统，是：true，不是：false
 */
fun isHarmonyOs(): Boolean {
    return try {
        val buildExClass = Class.forName("com.huawei.system.BuildEx")
        val osBrand = buildExClass.getMethod("getOsBrand").invoke(buildExClass) ?: return false
        "harmony".equals(osBrand.toString(), ignoreCase = true)
    } catch (e: Throwable) {
        false
    }
}

/**
 * 是否模拟器
 */
fun isEmulator(context: Context): Boolean {
    val checkProperty = (Build.FINGERPRINT.startsWith("generic")
            || Build.FINGERPRINT.lowercase(Locale.getDefault()).contains("vbox")
            || Build.FINGERPRINT.lowercase(Locale.getDefault()).contains("test-keys")
            || Build.MODEL.contains("google_sdk")
            || Build.MODEL.contains("Emulator")
            || Build.MODEL.contains("Android SDK built for x86")
            || Build.MANUFACTURER.contains("Genymotion")
            || Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
            || "google_sdk" == Build.PRODUCT)
    if (checkProperty) {
        return true
    }
    val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    val operatorName = tm.networkOperatorName
    return operatorName.lowercase(Locale.getDefault()) == "android"
}