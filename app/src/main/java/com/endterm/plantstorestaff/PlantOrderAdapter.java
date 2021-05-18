package com.endterm.plantstorestaff;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.endterm.plantstorestaff.Model.PlantOrder;

import java.util.ArrayList;

public class PlantOrderAdapter extends RecyclerView.Adapter<PlantOrderAdapter.PlantOrderViewHolder> {

    ArrayList<PlantOrder> plantOrders;
    Context context;

    public PlantOrderAdapter(Context context,ArrayList<PlantOrder> plantOrders) {
        this.plantOrders = plantOrders;
        this.context = context;
    }

    @NonNull

    @Override
    public PlantOrderViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_plant_order, parent, false);
        return new PlantOrderAdapter.PlantOrderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull  PlantOrderAdapter.PlantOrderViewHolder holder, int position) {
        PlantOrder plantOrder = plantOrders.get(position);
        holder.name.setText(plantOrder.getProductName());
        holder.plantId.setText(plantOrder.getProductId());
        holder.price.setText(plantOrder.getPrice());
        holder.quantity.setText(plantOrder.getQuantity());
    }


    @Override
    public int getItemCount() {
        return plantOrders.size();
    }

    public static class PlantOrderViewHolder extends RecyclerView.ViewHolder{

        TextView name, plantId, price, quantity;

        public PlantOrderViewHolder(@NonNull  View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name_plant_order);
            plantId = itemView.findViewById(R.id.tv_plantId_order);
            price = itemView.findViewById(R.id.tv_price_plant_order);
            quantity = itemView.findViewById(R.id.tv_quantity_plant_order);
        }
    }
}
