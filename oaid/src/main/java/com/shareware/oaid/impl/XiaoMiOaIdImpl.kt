package com.shareware.oaid.impl

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import com.shareware.oaid.OaIdGenerator

class XiaoMiOaIdImpl(context: Context, sp: SharedPreferences) {

    init {
        fetchOaId(context, sp)
    }

    @SuppressLint("PrivateApi")
    private fun fetchOaId(context: Context, sp: SharedPreferences) {
        try {
            val idProviderClass = Class.forName("com.android.id.impl.IdProviderImpl")
            val idProviderImpl = idProviderClass.newInstance()
            val method = idProviderClass.getMethod("getOAID", Context::class.java)
            val id = method.invoke(idProviderImpl, context) as? String
            if (!id.isNullOrEmpty()) {
                sp.edit().putString("device.oa.id", id).apply()
            }
            OaIdGenerator.notifyOaIdResult(id, true)
        } catch (ignore: Throwable) {
            OaIdGenerator.notifyOaIdResult("not support XiaoMi, ${Build.MODEL}", false)
        }
    }
}