package com.example.krucils;


import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.krucils.objek.PaketHarga;

import java.util.ArrayList;

public class PaketViewHolder extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<PaketHarga>listpaket;
    private Context context;

    public RecyclerViewAdapter(ArrayList<PaketHarga>listpaket, Context context){
        this.listpaket = listpaket;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
