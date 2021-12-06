package com.example.cscb07_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.Viewholder> {
    public Context context;
    public ArrayList<cardmodel> ModelArrayList;

    // Constructor
    public OrderAdapter(Context context, ArrayList<cardmodel> ModelArrayList) {
        this.context = context;
        this.ModelArrayList = ModelArrayList;
    }

    @NonNull
    @Override
    public OrderAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.start_orders, parent, false);
        return new OrderAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.Viewholder holder, int position) {
        // to set data to textview and imageview of each card layout
        cardmodel model = ModelArrayList.get(position);
        holder.orderidTV.setText(model.getOrderid());
        holder.statusTV.setText(model.getStatus());

    }

    @Override
    public int getItemCount() {
        return ModelArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        public TextView orderidTV, statusTV;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            orderidTV = itemView.findViewById(R.id.orderid);
            statusTV = itemView.findViewById(R.id.status);
        }
    }
}