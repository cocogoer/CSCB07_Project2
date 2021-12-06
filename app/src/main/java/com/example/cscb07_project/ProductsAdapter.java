package com.example.cscb07_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductsAdapter extends BaseAdapter{

    Context context;
    private int resource;
    ArrayList<Product> products = new ArrayList<Product>();


    public ProductsAdapter (Context context, int resource, ArrayList<Product> products){
        this.context = context;
        this.products = products;
        this.resource = resource;
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

    private static class ViewHolder {
        TextView tvProductName;
        TextView tvProductQuantity;
        TextView tvProductPrice;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.tvProductName = (TextView) convertView.findViewById(R.id.textViewOrderName);
            viewHolder.tvProductQuantity = (TextView) convertView.findViewById(R.id.textViewQuantity);
            viewHolder.tvProductPrice = (TextView) convertView.findViewById(R.id.textViewPrice);

            convertView.setTag(convertView);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Product p = (Product) getItem(position);

        String productQuantity = ((Integer) p.getInventory_quantity()).toString();
        String productPrice = ((Double) p.getPrice()).toString();

        viewHolder.tvProductName.setText(p.getName());
        viewHolder.tvProductPrice.setText(productPrice);
        viewHolder.tvProductQuantity.setText(productQuantity);


        return convertView;
    }

}
