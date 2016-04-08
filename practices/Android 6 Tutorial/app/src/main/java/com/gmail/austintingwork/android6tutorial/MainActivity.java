package com.gmail.austintingwork.android6tutorial;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "info";

    private ListView listView;
    private TextView show_app_name;

    private ItemAdapter itemAdapter;
    private List<Item> items;

    // 選單項目物件
    private MenuItem add_item, search_item, revert_item, delete_item;
    // 已選擇項目數量
    private int selectedCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "MainActivity onCreate: ");
//        MVC
        processViews();
        processControllers();
        items = new ArrayList<Item>();
        items.add(new Item(1, new Date().getTime(), Colors.RED, "Android List 練習", "Hello content", "", "", 0, 0, 0));
        items.add(new Item(2, new Date().getTime(), Colors.BLUE, "Android List 練習2", "Hello content22", "", "", 0, 0, 0));
        items.add(new Item(3, new Date().getTime(), Colors.GREEN, "Android List 練習3", "Hello content33", "", "", 0, 0, 0));

        itemAdapter = new ItemAdapter(this, R.layout.single_item, items);
        listView.setAdapter(itemAdapter);

    }

    private void processViews() {
        listView = (ListView) findViewById(R.id.item_list);
        show_app_name = (TextView) findViewById(R.id.show_app_name);
    }

    private void processControllers() {
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "MainActivity List onItemClick: selectCount = " + selectedCount);
                Item item = itemAdapter.getItem(position);
//                如果已經有勾選的項目
                if (selectedCount > 0) {
                    processMenu(item);
                    itemAdapter.set(position, item);
                } else {

                    Intent intent = new Intent("com.gmail.austintingwork.android6tutorial.EDIT_ITEM");
//                設定記事編號與記事物件
                    intent.putExtra("position", position);
                    intent.putExtra("com.gmail.austintingwork.android6tutorial.Item", item);
                    startActivityForResult(intent, 1);
                }

            }
        };
        listView.setOnItemClickListener(itemClickListener);

        AdapterView.OnItemLongClickListener itemLongClickListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = itemAdapter.getItem(position);
                processMenu(item);
                itemAdapter.set(position, item);
                return true;
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

    private void processMenu(Item item) {
        if (item != null) {
//            設定已勾選狀態
            item.setSelected(!item.isSelected());
            if (item.isSelected()) {
                selectedCount++;
            } else {
                selectedCount--;
            }
            add_item.setVisible(selectedCount == 0);
            search_item.setVisible(selectedCount == 0);
            revert_item.setVisible(selectedCount > 0);
            delete_item.setVisible(selectedCount > 0);
        }
    }

    //    override method from Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
//        取得選單項目物件
        add_item = menu.findItem(R.id.add_item);
        search_item = menu.findItem(R.id.search_item);
        revert_item = menu.findItem(R.id.revert_item);
        delete_item = menu.findItem(R.id.delete_item);
//        設定選單項目
        processMenu(null);

        return true;
    }

    public void clickMenuItem(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.search_item:
                break;
            case R.id.add_item:
                Intent intent = new Intent("com.gmail.austintingwork.android6tutorial.ADD_ITEM");
//                0 新增記事
                startActivityForResult(intent, 0);
                break;
//            取消勾選所有項目
            case R.id.revert_item:
                for (int i = 0; i < itemAdapter.getCount(); i++) {
                    Item ri = itemAdapter.getItem(i);

                    if (ri.isSelected()) {
                        ri.setSelected(false);
                        itemAdapter.set(i, ri);
                    }
                }
                selectedCount = 0;
                processMenu(null);
                break;
            case R.id.delete_item:
                // 沒有選擇
                if (selectedCount == 0) {
                    break;
                }
                // 建立與顯示詢問是否刪除的對話框
                AlertDialog.Builder d = new AlertDialog.Builder(this);
                String message = getString(R.string.delete_item);
                d.setTitle(R.string.delete)
                        .setMessage(String.format(message, selectedCount));
                d.setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 刪除所有已勾選的項目
                                int index = itemAdapter.getCount() - 1;// 從0開始

                                while (index > -1) {
                                    Item item = itemAdapter.get(index);

                                    if (item.isSelected()) {
                                        itemAdapter.remove(item);
                                    }

                                    index--;
                                }

                                // 通知資料改變
                                itemAdapter.notifyDataSetChanged();
                                selectedCount = 0;
                                processMenu(null);
                            }
                        });
                d.setNegativeButton(android.R.string.no, null);
                d.show();

                break;
        }


    }

    //    XML TextView show_app_name onClick
    public void aboutApp(View view) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Item item = (Item) data.getExtras().getSerializable("com.gmail.austintingwork.android6tutorial.Item");

//            如果新增記事
            if (requestCode == 0) {
                // 設定記事物件的編號
                item.setId(items.size() + 1);
                item.setDatetime(new Date().getTime());

                items.add(item);

                itemAdapter.notifyDataSetChanged();

            }
//                修改記事
            else if (requestCode == 1) {
//                如果data裡面沒有position,回傳-1
                int position = data.getIntExtra("position", -1);
                if (position != -1) {
                    items.set(position, item);
                    itemAdapter.notifyDataSetChanged();
                }
            }
        }


    }

}
