package com.example.krucils;

import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;



public class InputFragment extends Fragment implements View.OnClickListener{
    private ProgressBar progressBar;
    private EditText Paket, Harga, Berlaku;
    private Button input;
    FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_input, container, false);

        progressBar =v.findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);
        Paket =(EditText)v.findViewById(R.id.tv_paket);
        Harga = (EditText)v.findViewById((R.id.tv_harga));
        Berlaku=(EditText) v.findViewById(R.id.tv_berlaku);

        db = FirebaseFirestore.getInstance();

        input = v.findViewById(R.id.input);
        input.setOnClickListener(this);


        return v;
    }

    private boolean isEmpty(String s){
        return TextUtils.isEmpty(s);
    }

    @Override
    public void onClick(View v) {

        //String paket = Paket.getText().toString();
        String nama = Paket.getText().toString();

        String harga = Harga.getText().toString();

        String berlaku= Berlaku.getText().toString();
        uploadPaket(nama,harga,berlaku);
        Paket.setText(null);
        Harga.setText(null);
        Berlaku.setText(null);

        }

    private void uploadPaket(String nama, String harga, String berlaku) {
        String id = UUID.randomUUID().toString();

        Map<String, Object> doc = new HashMap<>();

        doc.put("id",id);
        doc.put("nama",nama);
        doc.put("harga",harga);
        doc.put("berlaku",berlaku);

        db.collection("paket")
                .document(id)
                .set(doc)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                  @Override
                  public void onSuccess(Void aVoid) {
                      Toast.makeText(getActivity(), "upload", Toast.LENGTH_LONG).show();

                  }
              })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "gagal", Toast.LENGTH_SHORT).show();
                    }
                });


    }

}