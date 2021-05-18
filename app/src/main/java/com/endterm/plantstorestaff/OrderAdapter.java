package com.endterm.plantstorestaff;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.endterm.plantstorestaff.Model.Order;
import com.endterm.plantstorestaff.Model.PlantOrder;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    ArrayList<Order> listOrder;
    Context context;

    public OrderAdapter(Context context,ArrayList<Order> listOrder ) {
        this.listOrder = listOrder;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new OrderAdapter.OrderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull  OrderAdapter.OrderViewHolder holder, int position) {
        Order order = listOrder.get(position);
        holder.address.setText(order.getAddress());
        holder.name.setText(order.getName());
        holder.phone.setText(order.getPhone());
        holder.status.setText(order.getStatus());
        holder.total.setText(order.getTotal());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sender = new Intent(v.getContext(), PlantOfOrder.class);
                sender.putExtra("BUNDLE", listOrder.get(position).getPlantOrders());
                v.getContext().startActivity(sender);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOrder.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder{

        TextView address, name, phone, status, total;

        public OrderViewHolder(@NonNull  View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.tv_address_order);
            name = itemView.findViewById(R.id.tv_name_order);
            phone = itemView.findViewById(R.id.tv_phone_order);
            status = itemView.findViewById(R.id.tv_status_order);
            total = itemView.findViewById(R.id.tv_total_order);
        }
    }
}
