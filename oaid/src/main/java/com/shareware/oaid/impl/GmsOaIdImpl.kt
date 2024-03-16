package com.shareware.oaid.impl

import aidl.google.android.gms.ads.identifier.internal.IAdvertisingIdService
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.SharedPreferences
import android.os.IBinder
import com.shareware.oaid.IOaIdSupport


/**
 * desc: 功能描述
 * email: mobiledeveloper@qq.com
 */
class GmsOaIdImpl(context: Context, sp: SharedPreferences) : IOaIdSupport {
    init {
        if (supported(context)) {
            try {
                val intent = Intent("com.google.android.gms.ads.identifier.service.START")
                intent.setPackage("com.google.android.gms")
                context.bindService(intent, object : ServiceConnection {
                    override fun onServiceConnected(name: ComponentName, service: IBinder) {
                        try {
                            val adService =
                                IAdvertisingIdService.Stub.asInterface(service) ?: return
                            if (adService.isLimitAdTrackingEnabled(true)) {
                                // 实测在系统设置中停用了广告化功能也是能获取到广告标识符的
                                return
                            }
                            if (!adService.id.isNullOrEmpty()) {
                                sp.edit().putString("device.oa.id", adService.id).apply()
                            }
                        } catch (ignore: Throwable) {
                        } finally {
                            context.unbindService(this)
                        }
                    }

                    override fun onServiceDisconnected(name: ComponentName) = Unit

                }, Context.BIND_AUTO_CREATE)
            } catch (ignore: Throwable) {
            }
        }
    }

    override fun supported(context: Context): Boolean {
        return try {
            context.packageManager.getPackageInfo("com.android.vending", 0) != null
        } catch (e: Exception) {
            false
        }
    }
}