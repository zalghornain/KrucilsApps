package com.example.krucils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krucils.objek.BahanMateri;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class List_Materi extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = " Mantap";
    private TextView judul,detail,kelasMulai;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    DownloadManager downloadManager;
    private Button delete,publish;
    private String uidAkses,uidKelas;
    private List<String> arrayID = new ArrayList<>();
    private static FirestoreRecyclerAdapter adapter;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://test-f3c56.appspot.com");

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__materi);
        db = FirebaseFirestore.getInstance();
        delete=findViewById(R.id.btn_delete);


        delete.setOnClickListener(this);



        if(getIntent().getExtras() != null){
            //Statement Disini Akan Berjalan Jika Menggunakan Bundle

            uidAkses = getIntent().getStringExtra("uidAkses");
            uidKelas= getIntent().getStringExtra("uidKelas");



        }else {
            //Statement Berikut ini Akan Dijalankan Jika Tidak Menggunakan Bundle
            Toast.makeText(getApplicationContext(), "Tolong kembali", Toast.LENGTH_LONG).show();

        }
        Query query = FirebaseFirestore.getInstance()
                .collection("MateriKelas")
                .whereEqualTo("uidAkses",uidAkses)
                .whereEqualTo("check",true)
                ;

        FirestoreRecyclerOptions<BahanMateri> options = new FirestoreRecyclerOptions.Builder<BahanMateri>()
                .setQuery(query,BahanMateri.class)
                .build();


        adapter = new FirestoreRecyclerAdapter<BahanMateri,BahanMateriHolder>(options) {

            @NonNull
            @Override
            public BahanMateriHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_materi_admin,parent,false);
                return new BahanMateriHolder(v);
            }

            @Override
            protected void onBindViewHolder(@NonNull BahanMateriHolder holder, int position, @NonNull BahanMateri model) {
                    String id = model.getId();
                    String judul = model.getJudul();
                    String urlFile = model.getUrlFile();
                    String uidAdmin = model.getUidAdmin();
                    String uidAkses = model.getUidAkses();
                    String uploadBy = model.getUploadBy();

                    boolean check = model.isCheck();
                    boolean typeFile = model.isTipe();
                    Date created = model.getCreated();

                    holder.inputArray(id,position);
                    holder.setText(judul);
                    holder.deleteMateri(id,position);
                    holder.downloadMateri(judul,urlFile,typeFile);
            }
        };
        recyclerView = findViewById(R.id.RecyclerViewListMateri);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(List_Materi.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
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

    @Override
    public void onClick(View view) {

        switch (view.getId()) {


            case R.id.btn_delete:
                alertDelete();







                break;

        }


    }
    private void delete(){


        for (int i = 0;i<arrayID.size();i++){




            String uidmateri = arrayID.get(i);
            DocumentReference materi = db.collection("MateriKelas")
                    .document(uidmateri);
            materi.update("check", false)

                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {


                        }
                    });







        }
    }

    private void alertDelete(){
        // Create the object of
        // AlertDialog Builder class
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(List_Materi.this);

        // Set the message show for the Alert time
        builder.setMessage("Anda yakin mau menghapus semua materi ?");

        // Set Alert Title
        builder.setTitle("Perhatian");

        // Set Cancelable false
        // for when the user clicks on the outside
        // the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name
        // OnClickListener method is use of
        // DialogInterface interface.

        builder
                .setPositiveButton(
                        "Ya",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {

                                // When the user click yes button
                                // then app will close
                                delete();
                                dialog.cancel();
                            }
                        });

        // Set the Negative button with No name
        // OnClickListener method is use
        // of DialogInterface interface.
        builder
                .setNegativeButton(
                        "Tidak",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {

                                // If user click no
                                // then dialog box is canceled.
                                dialog.cancel();
                            }
                        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();
    }



    private class BahanMateriHolder extends RecyclerView.ViewHolder {
        public BahanMateriHolder(@NonNull View itemView) {
            super(itemView);
        }

        void setText(final String setJudul){

            judul = itemView.findViewById(R.id.judul_materi);
            judul.setText(setJudul);
        }

        void deleteMateri(final String id,final int position){
            db = FirebaseFirestore.getInstance();
            ImageButton delete = itemView.findViewById(R.id.btn_delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    DocumentReference materiKelas = db.collection("MateriKelas")
                            .document(id);
                    materiKelas.update("check",false)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(List_Materi.this, "Delete", Toast.LENGTH_LONG).show();
                                    arrayID.remove(position);

                                }
                            });




                }




            });

        }

        void inputArray (final String id, final int position){

            arrayID.add(position,id);
        }

        void downloadMateri(final String judul, final String urlFile, final boolean typeFile){

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(typeFile==true){
                        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                        Uri uri = Uri.parse(urlFile);
                        DownloadManager.Request request = new DownloadManager.Request(uri);
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        request.setDestinationInExternalFilesDir(List_Materi.this,DIRECTORY_DOWNLOADS,judul );
                        Long reference = downloadManager.enqueue(request);
                    } else {
                        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlFile));
                       startActivity(appIntent);

                    }
                }
            });
        }
    }
}
