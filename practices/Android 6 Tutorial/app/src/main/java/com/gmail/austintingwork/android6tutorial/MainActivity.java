package com.gmail.austintingwork.android6tutorial;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ListView listView ;
    private TextView show_app_name ;

    private static final String[] data = {
            "Android 6 Turtorial",
            "Learn and Practice",
            "Have Fun"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        processViews();
        processControllers();

        int layoutId = android.R.layout.simple_list_item_1;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, layoutId, data);
        listView.setAdapter(adapter);




    }

    private void processViews(){
        listView = (ListView) findViewById(R.id.item_list);
        show_app_name = (TextView) findViewById(R.id.show_app_name);
    }

    private void processControllers(){
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, data[position], Toast.LENGTH_SHORT).show();
            }
        };
        listView.setOnItemClickListener(itemClickListener);

        AdapterView.OnItemLongClickListener itemLongClickListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Long:"+data[position], Toast.LENGTH_SHORT).show();
                return false;
            }
        };
        listView.setOnItemLongClickListener(itemLongClickListener);

        View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle(R.string.about)
                        .setMessage("長按")
                        .show();
                return false;
            }
        };
        show_app_name.setOnLongClickListener(longClickListener);

    }



    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void clickMenuItem(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.search_item:
                break;
            case R.id.add_item:
                break;
            case R.id.revert_item:
                break;
            case R.id.delete_item:
                break;
        }
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("MenuItem Test")
                .setMessage(item.getTitle())
                .setIcon(item.getIcon())
                .show();

    }

    public void aboutApp(View view) {
        Toast.makeText(this, R.string.app_name, Toast.LENGTH_LONG).show();
    }
}
