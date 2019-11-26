package com.example.krucils;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.krucils.objek.Beli;
import com.example.krucils.objek.Kelas;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KeranjangFragment extends Fragment {

    private final String TAG = " Keranjang";
    private TextView judul,detail,kelasMulai,harga,totalHarga;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String currentuserUID;
    private int inflater;

    public static int total=1;
    //private List keranjangHarga = new ArrayList<Integer>();
    private ArrayList<Integer> keranjangHarga = new ArrayList<Integer>();

    private static FirestoreRecyclerAdapter adapter;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://test-f3c56.appspot.com");
    private FirebaseAuth mAuth;
    FirebaseFirestore db;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_keranjang,container,false);
        totalHarga = view.findViewById(R.id.tv_total);
        //getUID
        currentuserUID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Query query = FirebaseFirestore.getInstance()
                .collection("Keranjang")
                .whereEqualTo("uiduser",currentuserUID)
                .whereEqualTo("check", false)
                ;

        FirestoreRecyclerOptions<Beli> options = new FirestoreRecyclerOptions.Builder<Beli>()
                .setQuery(query,Beli.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<Beli,KeranjangHolder>(options) {

            @NonNull
            @Override
            public KeranjangHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_keranjang,parent,false);
                return new KeranjangHolder(v);
            }

            @Override
            protected void onBindViewHolder(@NonNull KeranjangHolder holder, int position, @NonNull Beli model) {

                String id = model.getId();
                String uidkelas = model.getUidkelas();
                String judul=model.getJudul();
                String detail=model.getDetail();
                String harga=model.getHarga();
                String image = model.getImageURL();
                String tanggal = model.getTanggal();
                boolean grupchat=model.isGrupchat();

                int hargaKelas = Integer.parseInt(harga);
                String uiduser=model.getUiduser();
                String email=model.getEmail();
                String username=model.getUsername();

                boolean check=model.isCheck();
                Date created=model.getCreated();


                keranjangHarga.add(position,hargaKelas);
                calculateTotal();

                holder.delete(position,id);
                holder.setText(judul,tanggal,image,harga,detail);
            }




        };

        calculateTotal();
        recyclerView = view.findViewById(R.id.RecyclerViewKeranjang);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }

    public  void calculateTotal(){
        int i=0;
        total=0;
        while(i<keranjangHarga.size()){
            total= total + keranjangHarga.get(i);
            i++;
        }

        totalHarga.setText("Rp."+total);
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
    private class KeranjangHolder extends RecyclerView.ViewHolder{

        public KeranjangHolder(@NonNull View itemView) {
            super(itemView);
        }

        void setText(final String setJudul, final String setTanggal, final String setImage, final  String setHarga, final  String setDetail) {
            imageView = itemView.findViewById(R.id.thumbnail);
            Picasso.get().load(setImage).into(imageView);

            judul= itemView.findViewById(R.id.judul_tv);
            judul.setText(setJudul);

            detail=itemView.findViewById(R.id.detail_tv);
            detail.setText(setDetail);

            kelasMulai= itemView.findViewById(R.id.mulaiKelas_tv);
            kelasMulai.setText(setTanggal);

            harga=itemView.findViewById(R.id.harga_tv);
            harga.setText(setHarga);
        }

        void delete(final int position, final String id){

            db = FirebaseFirestore.getInstance();
            Button delete = itemView.findViewById(R.id.delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    keranjangHarga.remove(position);
                    DocumentReference keranjang = db.collection("Keranjang")
                            .document(id);
                    keranjang.update("check",true)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getActivity(), "Delete", Toast.LENGTH_LONG).show();

                                }
                            });




                }




            });


        }


    }
}
