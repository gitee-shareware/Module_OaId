package com.shareware.oaid.impl

import aidl.asus.msa.SupplementaryDID.IDidAidlInterface
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
class AsusOaIdImpl(context: Context, sp: SharedPreferences) : IOaIdSupport {
    init {
        if (supported(context)) {
            try {
                val intent = Intent("com.asus.msa.action.ACCESS_DID")
                val pkg = "com.asus.msa.SupplementaryDID"
                intent.component = ComponentName(pkg, "$pkg.SupplementaryDIDService")
                context.bindService(intent, object : ServiceConnection {
                    override fun onServiceConnected(name: ComponentName, service: IBinder) {
                        try {
                            val id = IDidAidlInterface.Stub.asInterface(service).oaid ?: return
                            if (id.isNotEmpty()) {
                                sp.edit().putString("device.oa.id", id).apply()
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
            context.packageManager.getPackageInfo("com.asus.msa.SupplementaryDID", 0) != null
        } catch (e: Exception) {
            false
        }
    }
}