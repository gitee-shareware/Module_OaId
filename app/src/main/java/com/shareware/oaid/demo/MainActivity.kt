package com.shareware.oaid.demo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.shareware.oaid.IGetter
import com.shareware.oaid.OaIdGenerator
import com.shareware.oaid.demo.R

/**
 * desc: 功能描述
 * email: mobiledeveloper@qq.com
 */
class MainActivity : AppCompatActivity(), IGetter {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        OaIdGenerator.Companion.getOaId(this, this)
    }

    override fun onOAIDGetComplete(result: String) {
        Log.d("MainActivity", "onOAIDGetComplete: $result")
    }

    override fun onOAIDGetError(error: Exception) {
        Log.d("MainActivity", "onOAIDGetError: ${error.message}")
    }
}