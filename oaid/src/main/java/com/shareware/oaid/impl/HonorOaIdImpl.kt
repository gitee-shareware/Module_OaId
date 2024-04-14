package com.shareware.oaid.impl

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import com.hihonor.ads.identifier.AdvertisingIdClient
import com.shareware.oaid.IOaIdSupport
import com.shareware.oaid.OaIdGenerator
import java.util.concurrent.Executors


/**
 * desc:荣耀官方SDK
 * email: mobiledeveloper@qq.com
 */
class HonorOaIdImpl(context: Context, sp: SharedPreferences) : IOaIdSupport {

    init {
        if (supported(context)) {
            Executors.newSingleThreadExecutor().execute {
                // 参阅 https://developer.hihonor.com/cn/kitdoc?kitId=11030&navigation=ref&docId=AdvertisingIdClient.md
                try {
                    // 如果用户手机中，HMS Core（APK）版本在2.6.2以下，无法获取OAID，将抛出IOException。
                    val info = AdvertisingIdClient.getAdvertisingIdInfo(context)
                    // isLimit:实测在系统设置中关闭了广告标识符，将获取到固定的一大堆0
                    if (info != null && !info.isLimit) {
                        if (!info.id.isNullOrEmpty()) {
                            sp.edit().putString("device.oa.id", info.id).apply()
                        }
                        OaIdGenerator.notifyOaIdResult(info.id, true)
                    } else {
                        OaIdGenerator.notifyOaIdResult(null, true)
                    }
                } catch (error: Exception) {
                    OaIdGenerator.notifyOaIdResult(error.message, false)
                }
            }
        } else {
            OaIdGenerator.notifyOaIdResult("not support Honor, ${Build.MODEL}", false)
        }
    }

    override fun supported(context: Context): Boolean {
        // 核心标识：com.hihonor.id 或 com.hihonor.id.HnOaIdService
        return AdvertisingIdClient.isAdvertisingIdAvailable(context);
    }
}