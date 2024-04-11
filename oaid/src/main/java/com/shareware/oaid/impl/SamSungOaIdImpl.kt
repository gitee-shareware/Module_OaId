package com.shareware.oaid.impl

import aidl.samsung.android.deviceidservice.IDeviceIdService
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.SharedPreferences
import android.os.IBinder
import com.shareware.oaid.IOaIdSupport
import com.shareware.oaid.OaIdGenerator


/**
 * desc: 功能描述
 * email: mobiledeveloper@qq.com
 */
class SamSungOaIdImpl(context: Context, sp: SharedPreferences) : IOaIdSupport {
    init {
        if (supported(context)) {
            val intent = Intent()
            intent.setClassName(
                "com.samsung.android.deviceidservice",
                "com.samsung.android.deviceidservice.DeviceIdService"
            )
            try {
                context.bindService(intent, object : ServiceConnection {
                    override fun onServiceConnected(name: ComponentName, service: IBinder) {
                        try {
                            val id = IDeviceIdService.Stub.asInterface(service).oaid
                            if (!id.isNullOrEmpty()) {
                                sp.edit().putString("device.oa.id", id).apply()
                            }
                            OaIdGenerator.notifyOaIdResult(id)
                        } catch (ignore: Exception) {
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
            context.packageManager.getPackageInfo("com.samsung.android.deviceidservice", 0) != null
        } catch (e: Exception) {
            false
        }
    }
}