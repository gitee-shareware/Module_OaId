package com.shareware.oaid.impl

import android.content.Context
import android.content.SharedPreferences
import com.huawei.hms.ads.identifier.AdvertisingIdClient
import com.shareware.oaid.IOaIdSupport
import com.shareware.oaid.OaIdGenerator
import java.util.concurrent.Executors


/**
 * desc:参阅华为官方 HUAWEI Ads SDK
 * email: mobiledeveloper@qq.com
 */
class HuaweiOaIdImpl(context: Context, sp: SharedPreferences) : IOaIdSupport {

    init {
        if (supported(context)) {
            Executors.newSingleThreadExecutor().execute {
                try {
                    // 参阅 https://developer.huawei.com/consumer/cn/doc/HMSCore-Guides/identifier-service-obtaining-oaid-sdk-0000001050064988
                    // 华为官方开发者文档提到“调用getAdvertisingIdInfo接口，获取ID信息，不要在主线程中调用该方法。”
                    val info = AdvertisingIdClient.getAdvertisingIdInfo(context)
                    if (info != null && !info.isLimitAdTrackingEnabled) {
                        if (!info.id.isNullOrEmpty()) {
                            sp.edit().putString("device.oa.id", info.id).apply()
                        }
                        OaIdGenerator.notifyOaIdResult(info.id)
                    } else {
                        OaIdGenerator.notifyOaIdResult(null)
                    }
                } catch (ignore: Exception) {
                }
            }
        }
    }

    override fun supported(context: Context): Boolean {
        try {
            if (AdvertisingIdClient.isAdvertisingIdAvailable(context)) {
                return true
            }
            val pm = context.packageManager
            if (pm.getPackageInfo("com.huawei.hwid", 0) != null) {
                return true
            }
            if (pm.getPackageInfo("com.huawei.hwid.tv", 0) != null) {
                return true
            }
            if (pm.getPackageInfo("com.huawei.hms", 0) != null) {
                return true
            }
        } catch (ignore: Exception) {
        }
        return false
    }
}