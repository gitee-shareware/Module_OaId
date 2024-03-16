package com.shareware.oaid

import android.content.Context

/**
 * desc: 功能描述
 * email: mobiledeveloper@qq.com
 */
interface IOaIdSupport {
    fun supported(context: Context): Boolean
}