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
import android.widget.Button;
import android.widget.Toast;

import com.endterm.plantstorestaff.Model.Category;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;



import java.util.ArrayList;

public class Home extends AppCompatActivity {
    RecyclerView rvAll;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference category = database.getReference().child("Category");
    DatabaseReference plants = database.getReference().child("Plant");

    Adapter adapter;
    ArrayList<Category> list;
    FloatingActionButton btnAdd;
    String categoryId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnAdd = findViewById(R.id.btn_add);
        rvAll = findViewById(R.id.rv_all);
        rvAll.hasFixedSize();
        rvAll.setLayoutManager(new GridLayoutManager(this, 2));

        list = new ArrayList<>();
        adapter = new Adapter(this, list);
        rvAll.setAdapter(adapter);

        category.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Category category = dataSnapshot.getValue(Category.class);
                    list.add(category);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sender = new Intent(Home.this, NewCategory.class);
                startActivityForResult(sender, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
        startActivity(getIntent());
    }

    @Override
    public void onBackPressed() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case 121:
                deleteCategory(item);
                return true;
            case 122:
                updateCategory();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void deleteCategory(MenuItem item){
        int a = item.getGroupId();

        Query categoryQuery = category.orderByChild("name").equalTo(list.get(a).getName());

        categoryQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    dataSnapshot.getRef().removeValue();
                    categoryId = dataSnapshot.getRef().getKey();
                    Log.i("test",categoryId);
                    Query queryPlant = plants.orderByChild("categoryId").equalTo(categoryId);
                    queryPlant.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull  DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                dataSnapshot.getRef().removeValue();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull  DatabaseError error) {}
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        adapter = new Adapter(this, list);
        rvAll.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        finish();
        startActivity(getIntent());
    }

    private void updateCategory(){
        Toast.makeText(this,"Update category", Toast.LENGTH_SHORT).show();
    }
}