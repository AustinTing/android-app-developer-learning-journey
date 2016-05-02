package com.gmail.austintingwork.android6tutorial;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

public class PrefActivity extends PreferenceActivity {

    private SharedPreferences sharedPreferences;
    private Preference defaultColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 指定使用的設定畫面配置資源
        // 這行敘述從API Level 11開始會產生警告訊息
        // 不過不會影響應用程式的運作
        addPreferencesFromResource(R.xml.mypreference);
        // 讀取顏色設定元件
        defaultColor = (Preference) findPreference("DEFAULT_COLOR");
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);


    }
    @Override
    protected void onResume() {
        super.onResume();
        // 讀取設定的預設顏色
        int color = sharedPreferences.getInt("DEFAULT_COLOR", -1);
        Log.d("debug", "PrefActivity: onResume: DEFAULT_COLOR"+color);

        if (color != -1) {
            // 設定顏色說明
            defaultColor.setSummary(getString(R.string.default_color_summary) +
                    ": " + ItemActivity.getColors(color));
        }
    }


}