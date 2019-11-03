package com.example.krucils;

import android.os.Bundle;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InputKelasFragment extends Fragment implements View.OnClickListener {

    private ProgressBar progressBar;
    private EditText judulKelas, detailKelas, hargaKelas,mulaiKelas;
    private Button upload;
    static  final SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
    FirebaseFirestore db;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_input_kelas, container, false);
        progressBar = v.findViewById(R.id.progressKelas);
        progressBar.setVisibility(View.GONE);

        judulKelas = v.findViewById(R.id.kelas_judul);
        detailKelas = v.findViewById(R.id.kelas_detail);
        hargaKelas = v.findViewById(R.id.kelas_harga);
        mulaiKelas = v.findViewById(R.id.kelas_mulai);
        upload = v.findViewById(R.id.upload);
        upload.setOnClickListener(this);

        db = FirebaseFirestore.getInstance();

        return v;
    }

    @Override
    public void onClick(View view) {

            String judul = judulKelas.getText().toString();
            String detail = detailKelas.getText().toString();
            String harga = hargaKelas.getText().toString();
            String tanggal = mulaiKelas.getText().toString();

            uploadKelas(judul,detail,harga,tanggal);
            judulKelas.setText(null);
            detailKelas.setText(null);
            hargaKelas.setText(null);
            mulaiKelas.setText(null);

    }

    private void uploadKelas(String judul, String detail, String harga, String tanggal) {

        String id = UUID.randomUUID().toString();

        Map<String, Object> doc = new HashMap<>();

        doc.put("id", id);
        doc.put("Judul", judul);
        doc.put("Harga", harga);
        doc.put("Detail", detail);
        doc.put("MulaiKelas", getDateFromString(tanggal));
        doc.put("timestamp", FieldValue.serverTimestamp());

        db.collection("Kelas")
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

    public Date getDateFromString(String datetoSave) {
        try {
            Date date = format.parse(datetoSave);
            return date;
        } catch (ParseException e) {
            return null;
        }
    }
}
