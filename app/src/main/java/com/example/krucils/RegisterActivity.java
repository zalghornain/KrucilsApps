package com.example.krucils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends MainActivity {


    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    private TextView userRegister,passRegister,emailRegister;
    private Button signupBtn;
    private FirebaseFirestore db;
    private static String z;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        mAuth = FirebaseAuth.getInstance();
        userRegister= findViewById(R.id.inputregister_username);
        emailRegister= findViewById(R.id.inputregister_email);
        passRegister=findViewById(R.id.inputregister_password);
        signupBtn=findViewById(R.id.btn_signup);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO only register if email is not used
                //emailIsUsed(mAuth, db, emailRegister.getText().toString());
                //Log.d(TAG, z);
                //if(!emailIsUsed(mAuth, db, emailRegister.getText().toString())) {
                    createAccount(userRegister.getText().toString(), emailRegister.getText().toString(), passRegister.getText().toString());
                    //todo kalo gak diisi apa2 crash aplikasinya
                //}
            }
        });


    }

    private void createAccount(String username, String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        // if (!validateForm()) {
        //    return;
        // }
        //    showProgressDialog();
        final String usernamefinal= username;
        final String emailfinal = email;



        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(RegisterActivity.this, "Register success.",
                                    Toast.LENGTH_SHORT).show();


                            //set result buat di cek di profil fragment
                            //input data to database

                            FirebaseUser user = mAuth.getCurrentUser();


                            //buat nginput sesuatu yang mantap gan
                            Map<String, Object> data = new HashMap<>();

                            data.put("UID", user.getUid());
                            data.put("email", user.getEmail());
                            //todo coba cari cara buat manggil manual jangan di paksa gan
                            data.put("username", usernamefinal);

                            //apply ke database
                            //masukin ke dalam document dengan judul UID di koleksi users
                            db.collection("users").document(user.getUid()).set(data);


                            setResult(Activity.RESULT_OK);
                            finish();
                            //todo kalo register udah selesai kira kira mau ngapain langkah selanjutnya ?

                            //kalo email duplicate register gagal

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Register failed.",
                                    Toast.LENGTH_SHORT).show();
                            //                   updateUI(null);
                        }

                        // [START_EXCLUDE]
                        //          hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    //ini belum selesai, masih error
    private boolean emailIsUsed(FirebaseAuth auth, FirebaseFirestore db, String email){
        FirebaseUser user = auth.getCurrentUser();
        db.collection("users")
                .whereEqualTo("email", email)
                .get()
                //todo kalo ketemu pertama stop
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task ) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot users : task.getResult()) {
                                Log.d(TAG, users.getId() + " => " + users.get("email"));
                                z = users.get("email").toString();
                                Log.d(TAG, z);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        return true;
    }
}
