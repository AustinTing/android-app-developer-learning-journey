package com.gmail.austintingwork.android6tutorial;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.Date;

public class ItemActivity extends AppCompatActivity {
    private EditText title_text, content_text;
    private static String TAG = "debug";
    // 啟動功能用的請求代碼
    private static final int START_CAMERA = 0;
    private static final int START_RECORD = 1;
    private static final int START_LOCATION = 2;
    private static final int START_ALARM = 3;
    private static final int START_COLOR = 4;

    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        proccessViews();

        Intent intent = getIntent();
        String action = intent.getAction();
//        修改記事
        if (action.equals("com.gmail.austintingwork.android6tutorial.EDIT_ITEM")) {
//            接收記事物件與設定標題、內容
            item = (Item) intent.getExtras().getSerializable("com.gmail.austintingwork.android6tutorial.Item");

            title_text.setText(item.getTitle());
            content_text.setText(item.getContent());

        }
//        新增記事
        else {
            item = new Item();
        }
    }

    private void proccessViews() {
        title_text = (EditText) findViewById(R.id.title_text);
        content_text = (EditText) findViewById(R.id.content_text);
    }

    //    activity_item Button ok_item
    public void onSubmit(View view) {
        if (view.getId() == R.id.ok_item) {
            String titleText = title_text.getText().toString();
            String contentText = content_text.getText().toString();

            item.setTitle(titleText);
            item.setContent(contentText);
//            如果是修改
            if (getIntent().getAction().equals("com.gmail.austintingwork.android6tutorial.ADD_ITEM")) {
                item.setLastModify(new Date().getTime());
            } else {
                item.setDatetime(new Date().getTime());
            }
            Intent result = getIntent();
//            設定回傳物件
            result.putExtra("com.gmail.austintingwork.android6tutorial.Item", item);

            setResult(Activity.RESULT_OK, result);

        }
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case START_CAMERA:
                    break;
                case START_RECORD:
                    break;
                case START_LOCATION:
                    break;
                case START_ALARM:
                    break;
                // 設定顏色
                case START_COLOR:
                    int colorId = data.getIntExtra("colorId", Colors.LIGHTGREY.parseColor());

                    item.setColor(getColors(colorId));
                    break;
            }
        }

    }

    private Colors getColors(int color) {
        Colors result = Colors.LIGHTGREY;
        if (color == Colors.BLUE.parseColor()) {
            result = Colors.BLUE;
        } else if (color == Colors.PURPLE.parseColor()) {
            result = Colors.PURPLE;
        } else if (color == Colors.GREEN.parseColor()) {
            result = Colors.GREEN;
        } else if (color == Colors.ORANGE.parseColor()) {
            result = Colors.ORANGE;
        } else if (color == Colors.RED.parseColor()) {
            result = Colors.RED;
        }

        return result;

    }

    public void clickFunction(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.take_picture:
                break;
            case R.id.record_sound:
                break;
            case R.id.set_location:
                break;
            case R.id.set_alarm:
                break;
            case R.id.select_color:
                startActivityForResult(
                        new Intent(this, ColorActivity.class), START_COLOR);
                break;
        }


    }
}
