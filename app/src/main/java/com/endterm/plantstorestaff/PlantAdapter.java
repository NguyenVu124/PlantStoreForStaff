package com.endterm.plantstorestaff;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.endterm.plantstorestaff.Model.Category;
import com.endterm.plantstorestaff.Model.PlantModel;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.PlantViewHolder> {

    ArrayList<PlantModel> listPlant;
    Context context;

    public PlantAdapter(Context context,ArrayList<PlantModel> listPlant) {
        this.listPlant = listPlant;
        this.context = context;
    }

    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new PlantViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull  PlantAdapter.PlantViewHolder holder, int position) {
        PlantModel plant = listPlant.get(position);
        holder.name.setText(plant.getName());
        Picasso.get()
                .load(plant.getImage())
                .into(holder.avt);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent sender  = new Intent(v.getContext(), Plant.class);
//                sender.putExtra("categoryId",String.valueOf(position+1));
//                v.getContext().startActivity(sender);
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return listPlant.size();
    }

    public static class PlantViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        TextView name, price;
        ImageView avt;
        LinearLayout item;

        public PlantViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            price = itemView.findViewById(R.id.tv_price);
            avt = itemView.findViewById(R.id.iv_avatar);
            item = itemView.findViewById(R.id.item);
            item.setOnCreateContextMenuListener(this);
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(this.getAdapterPosition(), 131,0,"Delete this plant");
            menu.add(this.getAdapterPosition(), 132,1,"Update this plant");
        }
    }
}
