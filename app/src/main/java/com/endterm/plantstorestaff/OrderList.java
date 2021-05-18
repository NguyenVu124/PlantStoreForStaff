package com.endterm.plantstorestaff;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.endterm.plantstorestaff.Model.Category;
import com.endterm.plantstorestaff.Model.Order;
import com.endterm.plantstorestaff.Model.PlantOrder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderList extends AppCompatActivity {
    RecyclerView rvOrder;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference order = database.getReference().child("request");

    OrderAdapter orderAdapter ;
    ArrayList<Order> listOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        rvOrder = findViewById(R.id.rv_order_list);
        rvOrder.hasFixedSize();
        rvOrder.setLayoutManager(new GridLayoutManager(this,1));
        listOrder = new ArrayList<>();
        orderAdapter = new OrderAdapter(this, listOrder);
        rvOrder.setAdapter(orderAdapter);
        order.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Order order = new Order();
                    order.setKey(dataSnapshot.getRef().getKey());
                    order.setAddress(dataSnapshot.child("address").getValue().toString());
                    order.setName(dataSnapshot.child("name").getValue().toString());
                    order.setPhone(dataSnapshot.child("phone").getValue().toString());
                    order.setStatus(dataSnapshot.child("status").getValue().toString());
                    order.setTotal(dataSnapshot.child("total").getValue().toString());
                    order.setPlantOrders((ArrayList<HashMap<String, PlantOrder>>) dataSnapshot.child("plantOrder").getValue());
                    listOrder.add(order);
                }
                orderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
    }
}