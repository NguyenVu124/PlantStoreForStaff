package com.endterm.plantstorestaff;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.endterm.plantstorestaff.Model.PlantModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private Button  btnSignin, btnTest;
    //    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference plants = database.getReference().child("Plant");
//    Query query = plants.orderByChild("categoryId").equalTo("5");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignin = findViewById(R.id.btn_login);
//        btnTest = findViewById(R.id.btn_test);

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn = new Intent(MainActivity.this,SignIn.class);
                startActivity(signIn);
            }
        });

//        btnTest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                query.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull  DataSnapshot snapshot) {
//                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                            dataSnapshot.getRef().removeValue();
//                        }
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull  DatabaseError error) {
//
//                    }
//                });
//            }
//        });

    }
}