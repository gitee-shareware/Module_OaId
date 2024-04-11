package com.shareware.oaid.demo;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.shareware.oaid.IGetter;
import com.shareware.oaid.OaIdGenerator;

/**
 * desc: 功能描述
 * email: mobiledeveloper@qq.com
 */
public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OaIdGenerator.Companion.getOaId(this, new IGetter() {
            @Override
            public void onOAIDGetComplete(@NonNull String result) {
                Log.d("TestActivity", "onOAIDGetComplete: " + result);
            }
        });
    }
}
