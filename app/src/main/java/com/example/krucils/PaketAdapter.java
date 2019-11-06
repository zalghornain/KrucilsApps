package com.example.krucils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.krucils.objek.PaketHarga;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class PaketAdapter extends FirestoreRecyclerAdapter<PaketHarga, PaketAdapter.PaketHolder> {


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public PaketAdapter(@NonNull FirestoreRecyclerOptions<PaketHarga> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PaketHolder holder, int position, @NonNull PaketHarga model) {
       // holder.textPaket.setText(model.getPaket());
        holder.textHarga.setText(String.valueOf(model.getHarga()));
        holder.textBerlaku.setText(model.getBerlaku());
    }

    @NonNull
    @Override
    public PaketHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_paket, parent,false);

        return new PaketHolder(v);
    }

    public class PaketHolder extends RecyclerView.ViewHolder{
        TextView textPaket, textHarga, textBerlaku;


        public PaketHolder(@NonNull View itemView) {
            super(itemView);
            textPaket = itemView.findViewById(R.id.tv_paket);
            textHarga = itemView.findViewById(R.id.tv_harga);
            textBerlaku = itemView.findViewById(R.id.tv_berlaku);



        }
    }


}
