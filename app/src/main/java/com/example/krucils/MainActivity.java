package com.example.krucils;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.AlertDialog;

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

           // this.startActivity(playIntent);test2;test3;testmantap;

        } if(view.getId()== R.id.login_admin){


        }


    }



}
