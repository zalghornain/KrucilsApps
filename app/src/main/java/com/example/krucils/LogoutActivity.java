package com.example.krucils;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LogoutActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView name;
    private Button btn_ya,btn_tidak;
    private boolean role;
    private String TAG = "logout";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logout);
        mAuth = FirebaseAuth.getInstance();

        btn_ya = findViewById(R.id.btn_ya);
        btn_ya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference docRef = db.collection("users").document(mAuth.getCurrentUser().getUid());
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                //Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                Log.d(TAG, "hasilsebelum: " + role);
                                role= document.getBoolean("admin");
                                Log.d(TAG, "hasilsesudah: " + role);

                                Log.d(TAG, "hasil: " + role);

                                if (role == true){
                                    BerandaAdmin berandaAdmin = new BerandaAdmin();
                                    BerandaAdmin.berandaAdmin.finish();
                                    Intent adminLogout = new Intent(LogoutActivity.this, Beranda.class);
                                    LogoutActivity.this.startActivity(adminLogout);
                                }
                                mAuth.signOut();
                                finish();
                            } else {
                                //Log.d(TAG, "No such document");
                            }
                        } else {
                            //Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
            }
        });

        btn_tidak = findViewById(R.id.btn_tidak);
        btn_tidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
