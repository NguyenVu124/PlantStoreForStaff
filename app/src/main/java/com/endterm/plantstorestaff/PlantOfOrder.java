package com.endterm.plantstorestaff;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.endterm.plantstorestaff.Model.PlantOrder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class PlantOfOrder extends AppCompatActivity {


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("request");
    RecyclerView rvPlantOrder;
    PlantOrderAdapter adapter;
    ArrayList<PlantOrder> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_of_order);

        rvPlantOrder = findViewById(R.id.rv_plant_of_order);
        rvPlantOrder.hasFixedSize();
        rvPlantOrder.setLayoutManager(new GridLayoutManager(this, 1));
        list = new ArrayList<>();
        Intent receive = getIntent();
        ArrayList<HashMap<String,PlantOrder>> plants = (ArrayList<HashMap<String,PlantOrder>>)getIntent().getSerializableExtra("BUNDLE");
        for (HashMap<String,PlantOrder> hashMap:plants){
            PlantOrder plantOrder = new PlantOrder(hashMap.values().toArray()[1].toString(),hashMap.values().toArray()[3].toString(),hashMap.values().toArray()[2].toString(),hashMap.values().toArray()[0].toString());
            list.add(plantOrder);
        }
        adapter = new PlantOrderAdapter(this, list);
        rvPlantOrder.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}