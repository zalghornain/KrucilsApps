package com.example.krucils;


import androidx.appcompat.app.AppCompatActivity;
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
    private Button loginBtn,adminBtn;
    private TextView UsernameField, PassField;
    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_user);

        mAuth = FirebaseAuth.getInstance();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        UsernameField=findViewById(R.id.input_email);
        PassField=findViewById(R.id.input_password);

        loginBtn= findViewById(R.id.btn_login);


        adminBtn=findViewById(R.id.login_admin);
    //    FirebaseUser currentUser = mAuth.getCurrentUser();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(UsernameField.getText().toString(),PassField.getText().toString());
            }

        });


    }



    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
    //    if (!validateForm()) {
     //       return;
     //   }

   //     showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                   //         updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                     //       updateUI(null);
                        }

                        // [START_EXCLUDE]
                    //    if (!task.isSuccessful()) {
                            //        mStatusTextView.setText(R.string.auth_failed);
                        }//   hideProgressDialog();
                        // [END_EXCLUDE]
                   // }
                });
        // [END sign_in_with_email]
    }



}
