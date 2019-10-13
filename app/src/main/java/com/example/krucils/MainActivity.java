package com.example.krucils;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button loginBtn,adminBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        loginBtn= (Button)findViewById(R.id.login_user);
        adminBtn=(Button)findViewById(R.id.login_admin);



        loginBtn.setOnClickListener(this);
        adminBtn.setOnClickListener(this);




    }

    public void onClick (View view){

        if (view.getId()== R.id.login_user){

           // Intent playIntent = new Intent(this, Penjumlahan.class);

           // this.startActivity(playIntent);test2

        } if(view.getId()== R.id.login_admin){


        }


    }



}
