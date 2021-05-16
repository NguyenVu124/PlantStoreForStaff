package com.endterm.plantstorestaff;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.endterm.plantstorestaff.Model.Category;
import com.endterm.plantstorestaff.Model.PlantModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class UpdatePlant extends AppCompatActivity {
    private EditText edtName, edtUrl, edtPrice;
    private Button btnUpdate;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference().child("Plant");
    String categoryId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_plant);
        edtName = findViewById(R.id.edt_namePlantUpdate);
        edtUrl = findViewById(R.id.edt_urlPlantUpdate);
        edtPrice = findViewById(R.id.edt_priceUpdate);
        btnUpdate = findViewById(R.id.btn_updatePlant);

        Intent receive = getIntent();
        String plantId = receive.getStringExtra("plantId");
        Query query = reference.orderByKey().equalTo(plantId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        PlantModel plant = dataSnapshot.getValue(PlantModel.class);
                        edtName.setText(plant.getName());
                        edtUrl.setText(plant.getImage());
                        edtPrice.setText(plant.getPrice());
                        categoryId=plant.getCategoryId();
                    }
                }else {
                    Toast.makeText(UpdatePlant.this, "No data!!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameUpdate = edtName.getText().toString();
                String urlUpdate = edtUrl.getText().toString();
                String priceUpdate = edtPrice.getText().toString();
                PlantModel updatePlant = new PlantModel(urlUpdate,nameUpdate,categoryId,priceUpdate);
                reference.child(plantId).setValue(updatePlant);
                setResult(1);
                finish();
            }
        });
    }
}