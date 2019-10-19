package com.example.krucils;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.AlertDialog;
import android.widget.TextView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity{
    private FirebaseAuth mAuth;
    private Button registerhomeBtn, loginBtn, logoutBtn;
    private TextView logintitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        registerhomeBtn=findViewById(R.id.register);
        loginBtn=findViewById(R.id.login);
        logintitle=findViewById(R.id.main_title);
        logoutBtn=findViewById(R.id.logout);

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String name = user.getEmail();
            logintitle.setText("Selamat datang \n" + name);
            loginBtn.setVisibility(View.GONE);
            registerhomeBtn.setVisibility(View.GONE);
            logoutBtn.setVisibility(View.VISIBLE);
        }else{
            String name = "Guest";
            logintitle.setText("Selamat datang \n" + name);
            loginBtn.setVisibility(View.VISIBLE);
            registerhomeBtn.setVisibility(View.VISIBLE);
            logoutBtn.setVisibility(View.GONE);
        }


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                MainActivity.this.startActivity(loginIntent);
                finish();
            }
        });


        //buat pindah ke register dari home
        registerhomeBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
              MainActivity.this.startActivity(registerIntent);
           }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });


    }







}
