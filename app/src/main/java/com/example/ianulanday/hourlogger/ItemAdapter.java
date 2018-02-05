package com.example.ianulanday.hourlogger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ianulanday on 2/2/2018.
 */

public class ItemAdapter extends BaseAdapter {

    LayoutInflater mInflater;
    ArrayList<String> items;
    ArrayList<String> itemTimes;

    public ItemAdapter(Context c, ArrayList<String> i, ArrayList<String> j) {
        items = i;
        itemTimes = j;
        mInflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {return items.size();}

    @Override
    public Object getItem(int i) {return items;}

    @Override
    public long getItemId(int i) {return i;}

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = mInflater.inflate(R.layout.logger_list_detail, null);
        TextView itemName = v.findViewById(R.id.item_name);
        TextView itemTime = v.findViewById(R.id.item_time);

        String name = items.get(i);
        String time = itemTimes.get(i);

        itemName.setText(name);
        itemTime.setText(time);

        return v;
    }


}
