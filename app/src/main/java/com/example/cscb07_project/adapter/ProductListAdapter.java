package com.example.cscb07_project.adapter;

//public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.Viewholder> {
//    public Context context;
//    public ArrayList<productDetail> ModelArrayList;

    // Constructor
//    public ProductListAdapter(Context context, ArrayList<productDetail> ModelArrayList) {
//        this.context = context;
 //       this.ModelArrayList = ModelArrayList;
 //   }
//    @NonNull
//    @Override
//    public ProductListAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
//        View view = LayoutInflater.from(context).inflate(R.layout.activity_product_detail, parent, false);
//        return new Viewholder(view);
//    }
//    public static class Viewholder extends RecyclerView.ViewHolder {

//        public TextView productNameTV, productPriceTV,productQuantityTV;

//        public Viewholder(@NonNull View itemView) {
//            super(itemView);
//            productNameTV = itemView.findViewById(R.id.productNameTV);
//            productPriceTV = itemView.findViewById(R.id.productPriceTV);
//            productQuantityTV = itemView.findViewById(R.id.productQuantityTV);
//        }
//    }

//    @SuppressLint("SetTextI18n")
//    @Override
//    public void onBindViewHolder(@NonNull ProductListAdapter.Viewholder holder, int position) {
        // to set data to textview and imageview of each card layout

//        productDetail model = ModelArrayList.get(position);
//        holder.productNameTV.setText(model.getName());
//        holder.productPriceTV.setText((model.price).toString());
//        holder.productQuantityTV.setText((model.quantity).toString());
//    }

//    @Override
//    public int getItemCount() {
//        return ModelArrayList.size();
//    }

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.cscb07_project.R;
import com.example.cscb07_project.model.productDetail;

import java.util.ArrayList;

//}
public class ProductListAdapter extends ArrayAdapter<productDetail> {

    private Context context;
    private int resource;

    public ProductListAdapter(@NonNull Context context, int resource, ArrayList<productDetail> ModelArrayList) {
        super(context, resource, ModelArrayList);
        this.context = context;
        this.resource = resource;
    }

    private class ViewHolder {
        TextView productNameTV;
        TextView productPriceTV;
        TextView productQuantityTV;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        productDetail model = getItem(position);



        ViewHolder viewHolder;

        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.productNameTV = (TextView) convertView.findViewById(R.id.productNameTV);
            viewHolder.productPriceTV = (TextView) convertView.findViewById(R.id.productPriceTV);
            viewHolder.productQuantityTV = (TextView) convertView.findViewById(R.id.productQuantityTV);


            convertView.setTag(convertView);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.productNameTV.setText(model.getName());
        viewHolder.productPriceTV.setText((model.price).toString());
        viewHolder.productQuantityTV.setText((model.quantity).toString());


        return convertView;
    }

}
