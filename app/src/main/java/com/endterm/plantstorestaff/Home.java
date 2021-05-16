package com.endterm.plantstorestaff;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
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
import com.google.android.material.navigation.NavigationView;
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
    private DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_gallery);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull  MenuItem item) {
                if(item.getItemId() == R.id.nav_home){
                    Toast.makeText(Home.this, "home", Toast.LENGTH_SHORT).show();
                }
                if(item.getItemId() == R.id.nav_order){
                    Toast.makeText(Home.this, "cart", Toast.LENGTH_SHORT).show();
                }
                if(item.getItemId() == R.id.nav_logout){
                    Toast.makeText(Home.this, "logout", Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });

        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );

        btnAdd = findViewById(R.id.btn_add);
        rvAll = findViewById(R.id.rv_all);
        rvAll.hasFixedSize();
        rvAll.setLayoutManager(new GridLayoutManager(this, 1));

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
    public boolean onOptionsItemSelected(MenuItem item) {

        Toast.makeText(Home.this, "test"+item.getItemId(), Toast.LENGTH_SHORT).show();
        Log.i("test","test"+item.getItemId());

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
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
                updateCategory(item);
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

    private void updateCategory(MenuItem item){
        int a = item.getGroupId();
        Query categoryQuery = category.orderByChild("name").equalTo(list.get(a).getName());
        categoryQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    categoryId = dataSnapshot.getRef().getKey();
                    Intent sender = new Intent(Home.this, UpdateCategory.class);
                    sender.putExtra("categoryId", categoryId);
                    startActivityForResult(sender, 1);
                }
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
    }
}