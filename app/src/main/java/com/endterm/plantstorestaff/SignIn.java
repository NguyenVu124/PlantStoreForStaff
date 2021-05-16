package com.endterm.plantstorestaff;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.endterm.plantstorestaff.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {

    EditText edtPhone, edtPassword;
    Button btnSignIn;

    FirebaseDatabase database;
    DatabaseReference users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtPassword = findViewById(R.id.edt_password);
        edtPhone = findViewById(R.id.edt_phone_number);
        btnSignIn = findViewById(R.id.btn_signIn);

        //Init Firebase
        database = FirebaseDatabase.getInstance();
        users  = database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(edtPhone.getText().toString(), edtPassword.getText().toString());
            }
        });
    }

    private void signIn(String phone, String password) {
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                if(snapshot.child(phone).exists()){
                    if(!phone.isEmpty()){
                        User login = snapshot.child(phone).getValue(User.class);
                        if(login.getPassword().equals(password) && login.getIsStaff().equals("true")){
//
                            Toast.makeText(SignIn.this, "Login Staff Success !!", Toast.LENGTH_SHORT).show();
                            Intent home = new Intent(SignIn.this, Home.class);
                            startActivity(home);
                        }else{
                            Toast.makeText(SignIn.this, "Password is wrong or you are not staff!!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(SignIn.this, "Phone is not exist !!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
    }
}