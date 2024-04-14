package com.shareware.oaid.impl

import aidl.oplus.stdid.IStdID
import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.os.Build
import android.os.IBinder
import com.shareware.oaid.IOaIdSupport
import com.shareware.oaid.OaIdGenerator
import java.security.MessageDigest


/**
 * desc:一加手机
 * email: mobiledeveloper@qq.com
 */
class OppoOaIdImpl2(context: Context, sp: SharedPreferences) : IOaIdSupport {
    init {
        if (supported(context)) {
            val intent = Intent("action.com.oplus.stdid.ID_SERVICE")
            intent.component =
                ComponentName("com.coloros.mcs", "com.oplus.stdid.IdentifyService")
            try {
                context.bindService(intent, object : ServiceConnection {
                    override fun onServiceConnected(name: ComponentName, service: IBinder) {
                        try {
                            val id = getSerId(service, context)
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
            OaIdGenerator.notifyOaIdResult("not support onePlus, ${Build.MODEL}", false)
        }
    }

    @SuppressLint("PackageManagerGetSignatures")
    private fun getSerId(service: IBinder, context: Context): String? {
        val pkgName: String = context.packageName
        val signatures: Array<Signature> = context.packageManager.getPackageInfo(
            pkgName, PackageManager.GET_SIGNATURES
        ).signatures
        val byteArray = signatures[0].toByteArray()
        val messageDigest = MessageDigest.getInstance("SHA1")
        val digest = messageDigest.digest(byteArray)
        return IStdID.Stub.asInterface(service)?.getSerID(pkgName, toHexString(digest), "OUID")
    }

    private fun toHexString(bytes: ByteArray): String {
        val hexDigits =
            charArrayOf(
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
            )
        val size = bytes.size
        val res = CharArray(size shl 1)
        var i = 0
        var j = 0
        while (i < size) {
            res[j++] = hexDigits[bytes[i].toInt().ushr(4) and 0x0f]
            res[j++] = hexDigits[bytes[i].toInt() and 0x0f]
            i++
        }
        return String(res)
    }

    override fun supported(context: Context): Boolean {
        return try {
            context.packageManager.getPackageInfo("com.coloros.mcs", 0) != null
        } catch (e: java.lang.Exception) {
            false
        }
    }
}