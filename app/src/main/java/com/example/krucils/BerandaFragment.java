package com.example.krucils;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.krucils.objek.Kelas;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Date;

public class BerandaFragment extends Fragment {
    private final String TAG = " Mantap";
    private TextView judul,detail,harga,berlaku;
    private ImageView imageView;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://test-f3c56.appspot.com");

    FirebaseFirestore db;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_beranda, container, false);
        db = FirebaseFirestore.getInstance();
       /*
        judul = v.findViewById(R.id.kelas_judul);

        detail = v.findViewById(R.id.kelas_detail);
        harga = v.findViewById(R.id.kelas_harga);
        berlaku = v.findViewById(R.id.kelas_mulai);
        imageView= v.findViewById(R.id.imgView);

        DocumentReference docRef = db.collection("Kelas").document("193f745c-7965-4bf5-9374-aa420eab4560");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        judul.setText(document.getString("Judul"));
                        detail.setText(document.getString("Detail"));
                        harga.setText(document.getString("Harga"));
                        Date mulai = document.getDate("MulaiKelas");
                        berlaku.setText(mulai.toString());

                        Picasso.get().load(document.getString("ImageURL")).into(imageView);


                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }

            }
        });


        */

        return v;
    }
}