package com.shareware.oaid.impl

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.shareware.oaid.OaIdGenerator

class XiaoMiOaIdImpl(context: Context, sp: SharedPreferences) {

    init {
        fetchOaId(context, sp)
    }

    @SuppressLint("PrivateApi")
    private fun fetchOaId(context: Context, sp: SharedPreferences) {
        try {
            val idProviderClass =
                Class.forName("com.android.id.impl.IdProviderImpl") ?: return
            val idProviderImpl = idProviderClass.newInstance() ?: return
            val method = idProviderClass.getMethod("getOAID", Context::class.java)
            val id = method.invoke(idProviderImpl, context) as? String
            if (!id.isNullOrEmpty()) {
                sp.edit().putString("device.oa.id", id).apply()
            }
            OaIdGenerator.notifyOaIdResult(id)
        } catch (ignore: Throwable) {
        }
    }
}