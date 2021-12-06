package com.example.cscb07_project;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.ArrayList;

public class CustomStoreAdapter extends BaseAdapter {
    Context context;
    List<CustomStoreView> stores;

    public CustomStoreAdapter(Context context, List<CustomStoreView> stores) {
        this.context = context;
        this.stores = stores;
    }

    @Override
    public int getCount() {
        return stores.size();
    }

    @Override
    public Object getItem(int position) {
        return stores.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder =new viewHolder();
            convertView = View.inflate(context,R.layout.item_store,null);
            viewHolder.store = convertView.findViewById(R.id.StoreName);
            viewHolder.owner = convertView.findViewById(R.id.ownerName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (viewHolder) convertView.getTag();
        }

        CustomStoreView store = stores.get(position);
        viewHolder.store.setText(store.getStoreName());
        viewHolder.owner.setText(store.getStoreOwner());

        return convertView;
    }

    static class viewHolder{
        TextView store;
        TextView owner;

    }
}



