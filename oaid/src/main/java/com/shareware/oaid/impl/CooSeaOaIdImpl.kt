package com.shareware.oaid.impl

import android.app.KeyguardManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import com.shareware.oaid.IOaIdSupport
import com.shareware.oaid.OaIdGenerator


/**
 * desc: 功能描述
 * email: mobiledeveloper@qq.com
 */
class CooSeaOaIdImpl(context: Context, sp: SharedPreferences) : IOaIdSupport {
    private val keyguardManager: KeyguardManager?

    init {
        keyguardManager = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager?
        if (supported(context)) {
            try {
                if (keyguardManager != null) {
                    val id = keyguardManager.javaClass.getDeclaredMethod("obtainOaid")
                        .invoke(keyguardManager)
                    if (id != null) {
                        sp.edit().putString("device.oa.id", id.toString()).apply()
                        OaIdGenerator.notifyOaIdResult(id.toString(), true)
                    } else {
                        OaIdGenerator.notifyOaIdResult(null, true)
                    }
                }
            } catch (error: Throwable) {
                OaIdGenerator.notifyOaIdResult(error.message, false)
            }
        } else {
            OaIdGenerator.notifyOaIdResult("not support CooSea, ${Build.MODEL}", false)
        }
    }

    override fun supported(context: Context): Boolean {
        if (keyguardManager == null) {
            return false
        }
        return try {
            keyguardManager.javaClass.getDeclaredMethod("isSupported")
                .invoke(keyguardManager) as Boolean
        } catch (e: Exception) {
            false
        }
    }
}