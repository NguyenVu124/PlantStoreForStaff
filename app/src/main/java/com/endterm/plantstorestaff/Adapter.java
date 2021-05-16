package com.endterm.plantstorestaff;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.endterm.plantstorestaff.Model.Category;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    ArrayList<Category> listCategory;
    Context context;

    public Adapter(Context context, ArrayList<Category> listCategory){
        this.listCategory = listCategory;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull  Adapter.ViewHolder holder, int position) {
        Category category = listCategory.get(position);
        holder.name.setText(category.getName());
        Picasso.get()
                .load(category.getImage())
                .into(holder.avt);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sender  = new Intent(v.getContext(), Plant.class);
                sender.putExtra("categoryId",String.valueOf(position+1));
                v.getContext().startActivity(sender);

            }
        });


    }

    @Override
    public int getItemCount() {
        return listCategory.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        TextView name;
        ImageView avt;
        LinearLayout item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            avt = itemView.findViewById(R.id.iv_avatar);
            item = itemView.findViewById(R.id.item);
            item.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(this.getAdapterPosition(), 121,0,"Delete this category");
            menu.add(this.getAdapterPosition(), 122,1,"Update this category");
        }
    }
}

