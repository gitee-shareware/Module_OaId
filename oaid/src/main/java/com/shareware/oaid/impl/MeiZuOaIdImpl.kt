package com.shareware.oaid.impl

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import com.shareware.oaid.IOaIdSupport
import com.shareware.oaid.OaIdGenerator


/**
 * desc: 功能描述
 * email: mobiledeveloper@qq.com
 */
class MeiZuOaIdImpl(context: Context, sp: SharedPreferences) : IOaIdSupport {
    init {
        if (supported(context)) {
            try {
                val uri = Uri.parse("content://com.meizu.flyme.openidsdk/")
                val query = context.contentResolver.query(uri, null, null, arrayOf("oaid"), null)
                if (query != null) {
                    query.use {
                        it.moveToFirst()
                        val columnIndex = it.getColumnIndex("value")
                        val id = it.getString(columnIndex)
                        if (!id.isNullOrEmpty()) {
                            sp.edit().putString("device.oa.id", id).apply()
                        }
                        OaIdGenerator.notifyOaIdResult(id, true)
                    }
                } else {
                    OaIdGenerator.notifyOaIdResult(null, true)
                }
            } catch (error: Throwable) {
                OaIdGenerator.notifyOaIdResult(error.message, false)
            }
        } else {
            OaIdGenerator.notifyOaIdResult("not support MeiZu, ${Build.MODEL}", false)
        }
    }

    override fun supported(context: Context): Boolean {
        return try {
            context.packageManager.resolveContentProvider("com.meizu.flyme.openidsdk", 0) != null
        } catch (ignore: Exception) {
            false
        }
    }
}