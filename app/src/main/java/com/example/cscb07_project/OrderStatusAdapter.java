package com.example.cscb07_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class OrderStatusAdapter extends ArrayAdapter<OrderInfo> {

    private Context context;
    private int resource;

    public OrderStatusAdapter(@NonNull Context context, int resource, ArrayList<OrderInfo> completedOrders) {
        super(context, resource, completedOrders);
        this.context = context;
        this.resource = resource;
    }

    private class ViewHolder {
        TextView tvStoreName;
        TextView tvNumberOfItems;
        TextView tvOrderId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OrderInfo order = getItem(position);
        System.out.println("f");
        String storeName = order.storeName;
        String numberOfItems = ((Integer) order.productList.size()).toString();
        String OrderId = order.orderId;

        ViewHolder viewHolder;

        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.tvStoreName = (TextView) convertView.findViewById(R.id.tvStoreName);
            viewHolder.tvNumberOfItems = (TextView) convertView.findViewById(R.id.tvTotalItems);
            viewHolder.tvOrderId = (TextView) convertView.findViewById(R.id.tvOrderId);


            convertView.setTag(convertView);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.tvStoreName.setText(storeName);
        viewHolder.tvNumberOfItems.setText(numberOfItems);
        viewHolder.tvOrderId.setText(OrderId);


        return convertView;
    }

}
