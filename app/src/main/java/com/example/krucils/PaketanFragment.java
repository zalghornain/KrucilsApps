package com.example.krucils;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.krucils.GroupChat;
import com.example.krucils.PaketAdapter;
import com.example.krucils.Pembelian;
import com.example.krucils.R;
import com.example.krucils.objek.PaketHarga;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class PaketanFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    private  static  final  String TAG = "PaketanFragment";
    private static FirestoreRecyclerAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.fragment_paket,container, false);
        Query query = FirebaseFirestore.getInstance()
                .collection("paket");
       FirestoreRecyclerOptions<PaketHarga> options = new FirestoreRecyclerOptions.Builder<PaketHarga>()
               .setQuery(query, PaketHarga.class)
               .build();

       adapter = new FirestoreRecyclerAdapter<PaketHarga, PaketanHolder>(options) {
           @Override
           protected void onBindViewHolder(@NonNull PaketanHolder holder, int position, @NonNull PaketHarga model) {

               holder.setText(model.getNama(),model.getHarga(),model.getBerlaku(),model.getId() );
           }

           @NonNull
           @Override
           public PaketanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
              View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_paket,parent,false);
               return new PaketanHolder(v);
           }
       };

       recyclerView = v.findViewById(R.id.RecyclerViewPaket);
       recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);



        return v;
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

    private class PaketanHolder extends RecyclerView.ViewHolder {
        private View view;
        public PaketanHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }

        void setText (final String setNama, final String setHarga, final String setBerlaku, final String id){
            TextView nama = view.findViewById(R.id.tv_paket);
            TextView harga = view.findViewById(R.id.tv_harga);
            TextView berlaku = view.findViewById(R.id.tv_berlaku);
            nama.setText(setNama);
            harga.setText(setHarga);
            berlaku.setText(setBerlaku);

           nama.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Bundle bundle = new Bundle();
                   bundle.putString("id", id);
                   bundle.putString("Paket", setNama);
                   bundle.putString("Harga",setHarga);
                   bundle.putString("Berlaku",setBerlaku);
                   Intent intent = new Intent(getContext(), Pembelian.class);
                   intent.putExtras(bundle);
                   startActivity(intent);

               }
           });




        }


    }
}
