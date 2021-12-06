package com.example.cscb07_project;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.ArrayList;

public class CustomProductAdapter extends BaseAdapter {
    Context context;
    List<CustomProductView> products;

    public CustomProductAdapter(Context context, List<CustomProductView> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
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
            convertView = View.inflate(context, R.layout.product,null);
            viewHolder.name = convertView.findViewById(R.id.ProductName);//layout里面
            viewHolder.quantity = convertView.findViewById(R.id.ProductQuantity);//layout里面
            viewHolder.price = convertView.findViewById(R.id.ProductPrice);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (viewHolder) convertView.getTag();
        }

        CustomProductView store = products.get(position);
        viewHolder.price.setText(store.getProductPrice());
        viewHolder.name.setText(store.getProductName());
        viewHolder.quantity.setText(store.getProductQuantity());
        return convertView;
    }

    static class viewHolder{
        TextView name;
        TextView quantity;
        TextView price;

    }
}
