package com.example.krucils;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class InputKelasFragment extends Fragment implements View.OnClickListener {

    private ProgressBar progressBar;
    private EditText judulKelas, detailKelas, hargaKelas,mulaiKelas;
    private Button upload, uImage;
    static  final SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
    private ImageView imageView;
    private Uri filepath;
    private final int PICK_IMAGE_REQUEST = 71;
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
        uImage = v.findViewById(R.id.uImage);
        uImage.setOnClickListener(this);

        db = FirebaseFirestore.getInstance();

        return v;
    }

    @Override
    public void onClick(View view) {



            if (view.getId()==R.id.upload) {
                String judul = judulKelas.getText().toString();
                String detail = detailKelas.getText().toString();
                String harga = hargaKelas.getText().toString();
                String tanggal = mulaiKelas.getText().toString();
                if (isEmpty(judul) && isEmpty(detail) && isEmpty(harga) && isEmpty(tanggal)) {
                    Toast.makeText(getActivity(), "Tolong dilengkapi kembali", Toast.LENGTH_LONG).show();
                } else {

                    uploadKelas(judul, detail, harga, tanggal);
                }
                judulKelas.setText(null);
                detailKelas.setText(null);
                hargaKelas.setText(null);
                mulaiKelas.setText(null);
            }

            if (view.getId()==R.id.uImage){

                Intent intent = new Intent();
                intent.setType("image/");
                intent.setAction(intent.ACTION_GET_CONTENT);
                startActivityForResult(intent.createChooser(intent,"Pilih gambar"), PICK_IMAGE_REQUEST);

            }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && requestCode == RESULT_OK && data != null && data.getData()
        !=null)
        {

        }

    }

    private boolean isEmpty(String s){
        return TextUtils.isEmpty(s);
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
