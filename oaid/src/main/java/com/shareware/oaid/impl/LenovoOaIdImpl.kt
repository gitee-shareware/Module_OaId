package com.shareware.oaid.impl

import aidl.zui.deviceidservice.IDeviceidInterface
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.SharedPreferences
import android.os.Build
import android.os.IBinder
import com.shareware.oaid.IOaIdSupport
import com.shareware.oaid.OaIdGenerator


/**
 * desc: 功能描述
 * email: mobiledeveloper@qq.com
 */
class LenovoOaIdImpl(context: Context, sp: SharedPreferences) : IOaIdSupport {
    init {
        if (supported(context)) {
            try {
                val intent = Intent()
                intent.setClassName(
                    "com.zui.deviceidservice",
                    "com.zui.deviceidservice.DeviceidService"
                )
                context.bindService(intent, object : ServiceConnection {
                    override fun onServiceConnected(name: ComponentName, service: IBinder) {
                        try {
                            val id = IDeviceidInterface.Stub.asInterface(service).oaid
                            if (!id.isNullOrEmpty()) {
                                sp.edit().putString("device.oa.id", id).apply()
                            }
                            OaIdGenerator.notifyOaIdResult(id, true)
                        } catch (error: Exception) {
                            OaIdGenerator.notifyOaIdResult(error.message, false)
                        } finally {
                            context.unbindService(this)
                        }
                    }

                    override fun onServiceDisconnected(name: ComponentName) = Unit

                }, Context.BIND_AUTO_CREATE)
            } catch (error: Throwable) {
                OaIdGenerator.notifyOaIdResult(error.message, false)
            }
        } else {
            OaIdGenerator.notifyOaIdResult("not support Lenovo, ${Build.MODEL}", false)
        }

    }

    override fun supported(context: Context): Boolean {
        return try {
            context.packageManager.getPackageInfo("com.zui.deviceidservice", 0) != null
        } catch (e: Exception) {
            false
        }
    }
}