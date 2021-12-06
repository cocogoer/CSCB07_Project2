package com.example.cscb07_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NewCustomOrderInfoAdapter extends RecyclerView.Adapter<NewCustomOrderInfoAdapter.CustomProductHolder> {

    private Context context;
    private List<CustomProductView> data;
    private OnWidgetDealListener listener;


    public NewCustomOrderInfoAdapter(Context context, List<CustomProductView> data) {
        this.context = context;
        this.data = data;
    }

    public NewCustomOrderInfoAdapter(Context context, List<CustomProductView> data, OnWidgetDealListener listener) {
        this.context = context;
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CustomProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomProductHolder(LayoutInflater.from(context).inflate(R.layout.order_info, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomProductHolder holder, int position) {
        CustomProductView customProductView = data.get(position);
        holder.name.setText(customProductView.getProductName());
        holder.quantity.setText(String.valueOf(customProductView.getProductQuantity()));
        holder.price.setText(String.valueOf(customProductView.getProductPrice()));
        holder.tvStoreName.setText(customProductView.getStoreName());
        holder.btnDeleteShoppingCar.setOnClickListener(v -> {
            if (listener != null) {
                listener.deleteShoppingCar(customProductView);
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public void update() {
        data.clear();
        notifyDataSetChanged();
    }

    public void update(List<CustomProductView> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    static class CustomProductHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView tvStoreName;
        TextView quantity;
        TextView price;
        Button btnDeleteShoppingCar;

        public CustomProductHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.ProductName);
            tvStoreName = itemView.findViewById(R.id.tvStoreName);
            quantity = (TextView) itemView.findViewById(R.id.ProductQuantity);
            price = (TextView) itemView.findViewById(R.id.ProductPrice);
            btnDeleteShoppingCar = (Button) itemView.findViewById(R.id.btnDeleteShoppingCar);

        }
    }

    public interface OnWidgetDealListener {
        void deleteShoppingCar(CustomProductView customProductView);
    }
}
