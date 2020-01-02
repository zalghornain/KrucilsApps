package com.example.krucils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krucils.objek.BahanMateri;
import com.example.krucils.objek.Test;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Date;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class Test_Activity extends AppCompatActivity {

    private final String TAG = " Mantap";
    private TextView judul,harga,id;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static FirestoreRecyclerAdapter adapter;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://test-f3c56.appspot.com");

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        db = FirebaseFirestore.getInstance();







        Query query = db.collection("paket");

        FirestoreRecyclerOptions<Test> options = new FirestoreRecyclerOptions.Builder<Test>()
                .setQuery(query,Test.class)
                .build();


        adapter = new FirestoreRecyclerAdapter<Test,BahanMateriHolder>(options) {


            @NonNull
            @Override
            public BahanMateriHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test,parent,false);
                return new BahanMateriHolder(v);
            }

            @Override
            protected void onBindViewHolder(@NonNull BahanMateriHolder holder, int position, @NonNull Test model) {

                String nama = model.getNama();
                int harga = model.getHarga();


                holder.setText(nama,harga);
            }

        };
        recyclerView = findViewById(R.id.RecyclerViewTest);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(Test_Activity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }



    private class BahanMateriHolder extends RecyclerView.ViewHolder {
        public BahanMateriHolder(@NonNull View itemView) {
            super(itemView);
        }

        void setText(final String setJudul, final int setHarga){

            judul=itemView.findViewById(R.id.judul_test);
            harga=itemView.findViewById(R.id.harga_test);

            judul.setText(setJudul);


            harga.setText(setHarga);



        }


    }
}
