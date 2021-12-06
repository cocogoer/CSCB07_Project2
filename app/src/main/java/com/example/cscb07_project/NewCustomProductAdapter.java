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

public class NewCustomProductAdapter extends RecyclerView.Adapter<NewCustomProductAdapter.CustomProductHolder> {

    private Context context;
    private List<CustomProductView> data;
    private OnWidgetDealListener listener;

    public NewCustomProductAdapter(Context context, List<CustomProductView> data, OnWidgetDealListener listener) {
        this.context = context;
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CustomProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomProductHolder(LayoutInflater.from(context).inflate(R.layout.product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomProductHolder holder, int position) {
        CustomProductView customProductView = data.get(position);
        holder.name.setText(customProductView.getProductName());
        holder.quantity.setText(String.valueOf(customProductView.getProductQuantity()));
        holder.price.setText(String.valueOf(customProductView.getProductPrice()));

        holder.btnAddShoppingCar.setOnClickListener(v -> {
            if (listener != null) {
                listener.addShoppingCar(customProductView);
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    static class CustomProductHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView quantity;
        TextView price;
        Button btnAddShoppingCar;

        public CustomProductHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.ProductName);
            quantity = (TextView) itemView.findViewById(R.id.ProductQuantity);
            price = (TextView) itemView.findViewById(R.id.ProductPrice);
            btnAddShoppingCar = (Button) itemView.findViewById(R.id.btnAddShoppingCar);

        }
    }

    public interface OnWidgetDealListener {
        void addShoppingCar(CustomProductView customProductView);
    }
}
