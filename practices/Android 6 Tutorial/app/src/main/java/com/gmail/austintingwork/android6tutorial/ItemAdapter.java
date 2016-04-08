package com.gmail.austintingwork.android6tutorial;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by cellbody on 2016/4/6.
 */
public class ItemAdapter extends ArrayAdapter<Item> {
    private int resource;
    private List<Item> items;
    private static final String TAG = "debug";

    public ItemAdapter(Context context, int resource,List<Item> items) {
        super(context, resource, items);
        this.resource = resource;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout itemView;
        // 讀取目前位置的記事物件
        final Item item = getItem(position);
        if (convertView == null) {
            // 建立項目畫面元件
            itemView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li = (LayoutInflater)
                    getContext().getSystemService(inflater);
            li.inflate(resource, itemView, true);
        }
        else {
            itemView = (LinearLayout) convertView;
        }

        RelativeLayout typeColor = (RelativeLayout) itemView.findViewById(R.id.type_color);
        ImageView selectItem = (ImageView) itemView.findViewById(R.id.select_item);
        TextView titleView = (TextView) itemView.findViewById(R.id.title_text);
        TextView dateView = (TextView) itemView.findViewById(R.id.date_text);

        GradientDrawable background = (GradientDrawable) typeColor.getBackground();
        background.setColor(item.getColor().parseColor());

        titleView.setText(item.getTitle());
        dateView.setText(item.getLocaleDatetime());
//        判斷以沒被點擊
        selectItem.setVisibility(item.isSelected() ? View.VISIBLE :View.INVISIBLE);
        return itemView;

    }

    // 設定指定編號的記事資料
//    很詭異
    public void set(int index, Item item) {
        Log.i(TAG, "ItemAdapter set index: "+index);
        if (index >= 0 && index < items.size()) {
            items.set(index, item);
            notifyDataSetChanged();
        }
    }

    // 讀取指定編號的記事資料
    public Item get(int index) {
        Log.i(TAG, "ItemAdapter get index: "+index);
        return items.get(index);
    }
}
