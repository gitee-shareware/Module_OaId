package com.shareware.oaid.impl

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import com.shareware.oaid.IOaIdSupport

/**
 * desc: 努比亚 红魔
 * email: mobiledeveloper@qq.com
 */
class NubiaOaIdImpl(context: Context, sp: SharedPreferences) : IOaIdSupport {
    init {
        if (supported(context)) {
            try {
                val uri = Uri.parse("content://cn.nubia.identity/identity")
                val client = context.contentResolver.acquireContentProviderClient(uri)
                if (client != null) {
                    val bundle = client.call("getOAID", null, null)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        client.close()
                    } else {
                        client.release()
                    }
                    if (bundle != null) {
                        if (bundle.getInt("code", -1) == 0) {
                            val id = bundle.getString("id")
                            if (!id.isNullOrEmpty()) {
                                sp.edit().putString("device.oa.id", id).apply()
                            }
                        }
                    }
                }
            } catch (ignore: Throwable) {
            }
        }
    }

    override fun supported(context: Context): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q;
    }
}