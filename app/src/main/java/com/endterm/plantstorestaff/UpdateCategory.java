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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class UpdateCategory extends AppCompatActivity {

    private EditText edtName, edtUrl;
    private Button btnUpdate;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference().child("Category");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_category);

        edtName = findViewById(R.id.edt_nameUpdate);
        edtUrl = findViewById(R.id.edt_urlUpdate);
        btnUpdate = findViewById(R.id.btn_update);

        Intent receive = getIntent();
        String categoryId = receive.getStringExtra("categoryId");
        Query query = reference.orderByKey().equalTo(categoryId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        Category category = dataSnapshot.getValue(Category.class);
                        edtName.setText(category.getName());
                        edtUrl.setText(category.getImage());
                    }
                }else {
                    Toast.makeText(UpdateCategory.this, "No data!!!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull  DatabaseError error) {}
        });



        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameUpdate = edtName.getText().toString();
                String urlUpdate = edtUrl.getText().toString();
                Category updateCategory = new Category(nameUpdate,urlUpdate);
                reference.child(categoryId).setValue(updateCategory);
                setResult(1);
                finish();
            }
        });
    }
}