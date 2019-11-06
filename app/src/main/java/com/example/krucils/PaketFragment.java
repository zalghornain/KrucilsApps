package com.example.krucils;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.krucils.objek.PaketHarga;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;



public class PaketFragment extends Fragment {
    private static final String TAG = "PaketFragment";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference paketRef = db.collection("paket");
    private PaketAdapter adapter;

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_kelas, container, false);
        recyclerView = v.findViewById(R.id.list_item);
        //setUpRecylerView();

        Query query = paketRef;
        FirestoreRecyclerOptions<PaketHarga> options = new FirestoreRecyclerOptions.Builder<PaketHarga>()
                .setQuery(query, PaketHarga.class)
                .build();


        adapter = new PaketAdapter(options);
        //recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        return v;


    }

    private void setUpRecylerView() {

        Query query = paketRef;
        FirestoreRecyclerOptions<PaketHarga> options = new FirestoreRecyclerOptions.Builder<PaketHarga>()
                .setQuery(query, PaketHarga.class)
                .build();


        adapter = new PaketAdapter(options);
        //recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        if(adapter !=null) {
            adapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter !=null) {
            adapter.stopListening();
        }
    }
}