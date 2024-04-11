package com.shareware.oaid

import android.content.Context
import com.shareware.oaid.impl.AsusOaIdImpl
import com.shareware.oaid.impl.CooSeaOaIdImpl
import com.shareware.oaid.impl.CoolPadOaIdImpl
import com.shareware.oaid.impl.FreeMeOaIdImpl
import com.shareware.oaid.impl.GmsOaIdImpl
import com.shareware.oaid.impl.HonorOaIdImpl
import com.shareware.oaid.impl.HuaweiOaIdImpl
import com.shareware.oaid.impl.LenovoOaIdImpl
import com.shareware.oaid.impl.MeiZuOaIdImpl
import com.shareware.oaid.impl.NubiaOaIdImpl
import com.shareware.oaid.impl.OppoOaIdImpl
import com.shareware.oaid.impl.OppoOaIdImpl2
import com.shareware.oaid.impl.SamSungOaIdImpl
import com.shareware.oaid.impl.ViVoOaIdImpl
import com.shareware.oaid.impl.XiaoMiOaIdImpl
import com.shareware.oaid.util.isASUS
import com.shareware.oaid.util.isBlackShark
import com.shareware.oaid.util.isCooSea
import com.shareware.oaid.util.isCoolPad
import com.shareware.oaid.util.isEmui
import com.shareware.oaid.util.isFreeMe
import com.shareware.oaid.util.isHonor
import com.shareware.oaid.util.isHuawei
import com.shareware.oaid.util.isLenovo
import com.shareware.oaid.util.isMeiZu
import com.shareware.oaid.util.isMiUi
import com.shareware.oaid.util.isMotoLora
import com.shareware.oaid.util.isNubia
import com.shareware.oaid.util.isOnePlus
import com.shareware.oaid.util.isOppo
import com.shareware.oaid.util.isSamsung
import com.shareware.oaid.util.isViVo
import com.shareware.oaid.util.isXiaomi
import java.util.concurrent.CopyOnWriteArrayList

/**
 * desc: 功能描述
 * email: mobiledeveloper@qq.com
 */
class OaIdGenerator constructor(context: Context) {
    init {
        val applicationContext = context.applicationContext
        val sp = applicationContext.getSharedPreferences("preference.system", Context.MODE_PRIVATE)
        id = sp.getString("device.oa.id", null)
        if (id.isNullOrEmpty()) {
            when {
                isHonor() -> {
                    val honorOaIdImpl = HonorOaIdImpl(applicationContext, sp)
                    if (!honorOaIdImpl.supported(applicationContext)) {
                        HuaweiOaIdImpl(applicationContext, sp)
                    }
                }

                isHuawei() || isEmui() -> {
                    HuaweiOaIdImpl(applicationContext, sp)
                }

                isViVo() -> {
                    ViVoOaIdImpl(applicationContext, sp)
                }

                isOppo() || isOnePlus() -> {
                    val oppoOaIdImpl = OppoOaIdImpl(applicationContext, sp)
                    if (!oppoOaIdImpl.supported(applicationContext)) {
                        OppoOaIdImpl2(applicationContext, sp)
                    }
                }

                isXiaomi() || isBlackShark() || isMiUi() -> {
                    XiaoMiOaIdImpl(applicationContext, sp)
                }

                isMeiZu() -> {
                    MeiZuOaIdImpl(applicationContext, sp)
                }

                isSamsung() -> {
                    SamSungOaIdImpl(applicationContext, sp)
                }

                isASUS() -> {
                    AsusOaIdImpl(applicationContext, sp)
                }

                isLenovo() || isMotoLora() -> {
                    LenovoOaIdImpl(applicationContext, sp)
                }

                isNubia() -> {
                    NubiaOaIdImpl(applicationContext, sp)
                }

                isCoolPad() -> {
                    CoolPadOaIdImpl(applicationContext, sp)
                }

                isCooSea() -> {
                    CooSeaOaIdImpl(applicationContext, sp)
                }

                isFreeMe() -> {
                    FreeMeOaIdImpl(applicationContext, sp)
                }

                else -> {
                    GmsOaIdImpl(applicationContext, sp)
                }
            }
        } else {
            listener.forEach {
                it.onOAIDGetComplete(id!!)
            }
            listener.clear()
        }
    }

    companion object {
        private val listener: CopyOnWriteArrayList<IGetter> = CopyOnWriteArrayList()
        private var id: String? = null

        fun notifyOaIdResult(id: String?) {
            if (this.id.isNullOrEmpty()) {
                this.id = id
            }
            if (listener.isNotEmpty()) {
                listener.forEach {
                    it.onOAIDGetComplete(this.id ?: "")
                }
                listener.clear()
            }
        }

        @JvmStatic
        fun getOaId(context: Context, getter: IGetter) {
            if (id.isNullOrEmpty()) {
                listener.add(getter)
                OaIdGenerator(context)
            } else {
                getter.onOAIDGetComplete(id!!)
            }
        }
    }
}