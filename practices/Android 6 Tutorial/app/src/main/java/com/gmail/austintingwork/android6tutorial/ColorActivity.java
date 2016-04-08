package com.gmail.austintingwork.android6tutorial;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;

public class ColorActivity extends Activity {


    private LinearLayout color_gallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);
        proccessViews();

        ColorListener colorListener = new ColorListener();
        for (Colors c :Colors.values()) {
            Button button = new Button(this);
            button.setId(c.parseColor());
            LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(128, 128);
            layout.setMargins(6, 6, 6, 6);
            button.setLayoutParams(layout);
            button.setBackgroundColor(c.parseColor());
            button.setOnClickListener(colorListener);
            color_gallery.addView(button);
        }
    }

    private void proccessViews() {
        color_gallery = (LinearLayout) findViewById(R.id.color_gallery);

    }
    private class ColorListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent result = getIntent();
            result.putExtra("colorId", view.getId());
            setResult(Activity.RESULT_OK, result);
            finish();
        }
    }
}
