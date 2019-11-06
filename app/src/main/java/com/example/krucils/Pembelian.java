package com.example.krucils;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Pembelian extends AppCompatActivity {

    private TextView idPaket, beliPaket, beliHarga, beliBerlaku;
    private Button bayar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pembelian);
        //Todo tambah Auth buat ambil data user
        idPaket = findViewById(R.id.id_paket);
        beliPaket= findViewById(R.id.beli_paket);
        beliHarga = findViewById(R.id.beli_harga);
        beliBerlaku = findViewById(R.id.beli_berlaku);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            idPaket.setText(bundle.getString("id"));
            beliPaket.setText(bundle.getString("Paket"));
            beliHarga.setText(bundle.getString("Harga"));
            beliBerlaku.setText(bundle.getString("Berlaku"));
        } else {
            idPaket.setText("Kosong");
            beliPaket.setText("kosong");
            beliHarga.setText("kosong");
            beliBerlaku.setText("kosong");

        }
    }
}
