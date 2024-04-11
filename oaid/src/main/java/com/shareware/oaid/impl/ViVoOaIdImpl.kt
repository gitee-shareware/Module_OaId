package com.shareware.oaid.impl

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import com.shareware.oaid.IOaIdSupport
import com.shareware.oaid.OaIdGenerator
import com.shareware.oaid.util.sysProperty

/**
 * desc: 参阅 com.umeng.umsdk:oaid_vivo:1.0.0.1
 * 即 com.vivo.identifier.IdentifierManager
 * email: mobiledeveloper@qq.com
 */
class ViVoOaIdImpl(context: Context, sp: SharedPreferences) : IOaIdSupport {
    init {
        if (supported(context)) {
            try {
                val uri = Uri.parse("content://com.vivo.vms.IdProvider/IdentifierId/OAID")
                context.contentResolver.query(uri, null, null, null, null)?.use {
                    it.moveToFirst()
                    val columnIndex = it.getColumnIndex("value")
                    val id = it.getString(columnIndex)
                    if (!id.isNullOrEmpty()) {
                        sp.edit().putString("device.oa.id", id).apply()
                    }
                    OaIdGenerator.notifyOaIdResult(id)
                }
            } catch (ignore: Throwable) {
            }
        }
    }

    override fun supported(context: Context): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            return false;
        }
        return sysProperty("persist.sys.identifierid.supported") == "1";
    }
}