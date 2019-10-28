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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InputFragment extends Fragment implements View.OnClickListener{
    private ProgressBar progressBar;
    private EditText Paket, Harga, Berlaku;
    private Button input;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_input, container, false);

        progressBar =v.findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);
        Paket =(EditText)v.findViewById(R.id.tv_paket);
        Harga = (EditText)v.findViewById((R.id.tv_harga));
        Berlaku=(EditText) v.findViewById(R.id.tv_berlaku);

        input = (Button)inflater.inflate(R.layout.fragment_input,container,false).findViewById(R.id.input);
        input.setOnClickListener(this);

        return v;
    }

    private boolean isEmpty(String s){
        return TextUtils.isEmpty(s);
    }

    @Override
    public void onClick(View v) {
        //Mendapatkan UserID dari pengguna yang Terautentikasi
        //String getUserID = auth.getCurrentUser().getUid();

        //Mendapatkan Instance dari Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference getReference;

        //Menyimpan Data yang diinputkan User kedalam Variable
        String getPaket = Paket.getText().toString();
        //ini input integer ke firebase gimana karena dalam fragment jadi sulit sepertinya
       // int Harga = Integer.parseInt();

        // sama input date masih bingung
        String getBerlaku= Berlaku.getText().toString();

        getReference = database.getReference(); // Mendapatkan Referensi dari Database

        getReference = database.getReference(); // Mendapatkan Referensi dari Database
        /*
        // Mengecek apakah ada data yang kosong
        if(isEmpty(Paket) && isEmpty(Harga) && isEmpty(Berlaku)){
            //Jika Ada, maka akan menampilkan pesan singkan seperti berikut ini.
            Toast.makeText(InputFragment.this, "Data tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show();
        }else {

        Jika Tidak, maka data dapat diproses dan meyimpannya pada Database
        Menyimpan data referensi pada Database berdasarkan User ID dari masing-masing Akun

            getReference.child("Admin").child(getUserID).child("Mahasiswa").push()
                    .setValue(new data_mahasiswa(getPaket, getHarga, getBerlaku))
                    .addOnSuccessListener(this, new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            //Peristiwa ini terjadi saat user berhasil menyimpan datanya kedalam Database
                            Paket.setText("");
                            Hargaa.setText("");
                            Berlaku.setText("");
                            Toast.makeText(MainActivity.this, "Data Tersimpan", Toast.LENGTH_SHORT).show();
                        }
                    });
            */
        }

}