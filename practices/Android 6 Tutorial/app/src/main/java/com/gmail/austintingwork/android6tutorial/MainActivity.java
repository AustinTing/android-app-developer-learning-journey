package com.gmail.austintingwork.android6tutorial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int layoutId = android.R.layout.simple_list_item_1;
        String[] data = {
            "Android 6 Turtorial",
            "Learn and Practice",
            "Have Fun"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,layoutId, data);
        ListView listView = (ListView)findViewById(R.id.item_list);
        listView.setAdapter(adapter);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
