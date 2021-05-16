package com.endterm.plantstorestaff;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.endterm.plantstorestaff.Model.Category;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class NewCategory extends AppCompatActivity {

    private Button btnAdd;
    private EditText edtName, edtUrl;
    private DatabaseReference db;
    private Category category;
    int lastId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);

        btnAdd = findViewById(R.id.btn_addNewCategory);
        edtName = findViewById(R.id.edt_nameCategory);
        edtUrl = findViewById(R.id.edt_urlImage);

        db = FirebaseDatabase.getInstance().getReference();
        Query lastQuery = db.child("Category").orderByKey().limitToLast(1);
        lastId = 0;
        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child: snapshot.getChildren()) {
                    lastId =  Integer.parseInt(child.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String url = edtUrl.getText().toString();
                category = new Category(name, url);
                db.child("Category").child(String.valueOf(lastId+1)).setValue(category);
                setResult(1);
                finish();
            }
        });
    }
}