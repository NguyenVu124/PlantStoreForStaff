package com.endterm.plantstorestaff;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.endterm.plantstorestaff.Model.Category;
import com.endterm.plantstorestaff.Model.PlantModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Plant extends AppCompatActivity {

    private TextView tvTest;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Plant");
    ArrayList<String> test = new ArrayList<>();
    RecyclerView rvPlant;
    PlantAdapter plantAdapter;
    ArrayList<PlantModel> list;
    FloatingActionButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant);

        btnAdd = findViewById(R.id.btn_addNewPlant);
        rvPlant = findViewById(R.id.rv_all);
        rvPlant.hasFixedSize();
        rvPlant.setLayoutManager(new GridLayoutManager(this, 2));

        list = new ArrayList<>();
        plantAdapter = new PlantAdapter(this, list);
        rvPlant.setAdapter(plantAdapter);

        Intent receive = getIntent();
        String categoryId =  receive.getStringExtra("categoryId");
//        Toast.makeText(this, ""+categoryId,Toast.LENGTH_SHORT).show();
        Query check = reference.orderByChild("categoryId").equalTo(categoryId);
        check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                        PlantModel plant = dataSnapshot.getValue(PlantModel.class);
                        list.add(plant);
                    }
//                    Toast.makeText(Plant.this, ""+test.size(),Toast.LENGTH_SHORT).show();
                    plantAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sender = new Intent(Plant.this, NewPlant.class);
                sender.putExtra("categoryId", categoryId);
                startActivityForResult(sender, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
        startActivity(getIntent());
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case 131:
                deletePlant(item);
//                Toast.makeText(Plant.this, "delete",Toast.LENGTH_SHORT).show();
                return true;
            case 132:
//                updateCategory();
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

    private void deletePlant(MenuItem item){
        int a = item.getGroupId();
        Toast.makeText(this,"Delete plant "+a, Toast.LENGTH_SHORT).show();
//        reference.child(String.valueOf(a+1)).removeValue();
//        plantAdapter = new plantAdapter(this, list);
//        rvAll.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
        finish();
        startActivity(getIntent());
    }
}