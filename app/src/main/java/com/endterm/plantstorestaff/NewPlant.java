package com.endterm.plantstorestaff;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.endterm.plantstorestaff.Model.PlantModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class NewPlant extends AppCompatActivity {

    private Button btnAdd;
    private EditText edtName, edtUrl, edtPrice;
    private DatabaseReference db;
    private PlantModel plant;
    int lastId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_plant);

        btnAdd = findViewById(R.id.btn_addNew);
        edtName = findViewById(R.id.edt_namePlant);
        edtUrl = findViewById(R.id.edt_urlImagePlant);
        edtPrice = findViewById(R.id.edt_Price);

        Intent receive = getIntent();
        String categoryId = receive.getStringExtra("categoryId");

        db = FirebaseDatabase.getInstance().getReference();
        Query lastQuery = db.child("Plant").orderByKey().limitToLast(1);
        lastId = 0;
        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child: snapshot.getChildren()) {
                    lastId =  Integer.parseInt(child.getKey());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String url = edtUrl.getText().toString();
                String price = edtPrice.getText().toString();
                plant = new PlantModel(url, name, String.valueOf(categoryId),price);
                db.child("Plant").child(String.valueOf(lastId+1)).setValue(plant);
                setResult(1);
                finish();
            }
        });
    }
}