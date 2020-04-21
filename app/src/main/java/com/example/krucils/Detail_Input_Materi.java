package com.example.krucils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.errorprone.annotations.Var;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class Detail_Input_Materi extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    FirebaseFirestore db;
    private Spinner spinner;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private ImageView image;
    private TextView judul,detail,kelasMulai,harga,detail_harga;
    private Button chooseFile,uploadFile, deleteKelas, checkKelas,materiFile,materiLink, publishKelas;
    private EditText judulMateri,linkMateri;
    private final int PICK_PDF_CODE = 72;
    private Uri file;
    boolean checkGrupchat;
    private StorageTask uploadTask;
    private static final String[] paths = {"Harga Full", "Harga Biasa"};
    DownloadManager downloadManager;
    private Boolean typeFile;
    private String UIDuser,email,username,UIDkelas,hargaPick,judulPick,imageURL,detailPick,mulaiKelas, uidAkses, format, idMateri;
    private int hargaFull,hargaBiasa;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://test-f3c56.appspot.com");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__input__materi);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        image = findViewById(R.id.image);
        judul = findViewById(R.id.judul_kelas);
        detail = findViewById(R.id.detail_kelas);
        kelasMulai = findViewById(R.id.kelas_berlaku);
        harga = findViewById(R.id.harga_kelas);
        detail_harga=findViewById(R.id.detail_harga);
        judulMateri = findViewById(R.id.judul_materi);
        linkMateri = findViewById(R.id.link_file);
        typeFile = false;
        chooseFile=findViewById(R.id.btn_upload);
        uploadFile=findViewById(R.id.btn_submit);
        deleteKelas=findViewById(R.id.btn_delete);
        checkKelas=findViewById(R.id.btn_check);
        publishKelas=findViewById(R.id.btn_publish);
        materiFile=findViewById(R.id.checkbox_file);
        materiLink=findViewById(R.id.uncheckbox_file);

        progressBar = findViewById(R.id.progressKelas);
        progressBar.setVisibility(View.GONE);

        materiLink.setOnClickListener(this);
        materiFile.setOnClickListener(this);
        deleteKelas.setOnClickListener(this);
        checkKelas.setOnClickListener(this);
        chooseFile.setOnClickListener(this);
        uploadFile.setOnClickListener(this);
        publishKelas.setOnClickListener(this);


        chooseFile.setEnabled(false);
        linkMateri.setVisibility(View.VISIBLE);
        materiLink.setVisibility(View.INVISIBLE);
        materiLink.setEnabled(false);






        if(getIntent().getExtras() != null){
            //Statement Disini Akan Berjalan Jika Menggunakan Bundle
            UIDkelas = getIntent().getStringExtra("id");
            uidAkses = getIntent().getStringExtra("uidAkses");
            String title = getIntent().getStringExtra("judul");
            imageURL = getIntent().getStringExtra("imageURL");
            String detaill = getIntent().getStringExtra("detail");
            String date = getIntent().getStringExtra("mulaiKelas");
            Picasso.get().load(imageURL).into(image);
            judul.setText(title);
            detail.setText(detaill);

            // SimpleDateFormat formatter = new SimpleDateFormat("d MMM yyyy");//formating according to my need
            // String date = formatter.format(getIntent().getStringExtra("mulaiKelas"));
            kelasMulai.setText(date);
            hargaFull = getIntent().getIntExtra("hargaFull",0);
            hargaBiasa = getIntent().getIntExtra("hargaBiasa",0);

            boolean check = getIntent().getExtras().getBoolean("check");


        }else {
            //Statement Berikut ini Akan Dijalankan Jika Tidak Menggunakan Bundle
            Toast.makeText(getApplicationContext(), "Tolong kembali", Toast.LENGTH_LONG).show();

        }


        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(Detail_Input_Materi.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        getUIDMateri();
        ReadUser(currentuser);


    }

    private void ReadUser(String s) {

        DocumentReference user = db.collection("users").document(s);

        user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override

            public void onComplete(@NonNull Task< DocumentSnapshot > task) {

                if (task.isSuccessful()) {

                    DocumentSnapshot doc = task.getResult();

                    UIDuser = (String) doc.getString("UID");
                    email = (String) doc.getString("email");
                    username =(String)doc.getString("username");

                }

            }

        })

                .addOnFailureListener(new OnFailureListener() {

                    @Override

                    public void onFailure(@NonNull Exception e) {
                        // ajaib ini
                        Toast.makeText(getApplicationContext(), "tidak ada user", Toast.LENGTH_LONG).show();

                    }

                });

    }



    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_submit:
                final ProgressDialog progressDialog = new ProgressDialog(Detail_Input_Materi.this);

                progressDialog.setTitle("Uploading...");
                progressDialog.show();

                String judul = judulMateri.getText().toString();
                String urlLink = linkMateri.getText().toString();


                if (!judul.isEmpty() && file != null && urlLink.isEmpty() ) {


                    if (uploadTask != null && uploadTask.isInProgress()) {
                        Toast.makeText(Detail_Input_Materi.this , "sedang diproses", Toast.LENGTH_LONG).show();


                    } else {
                        getUIDMateri();
                        String formatFile = file.toString();
                        formatFile(formatFile);
                        final StorageReference childRef = storageRef.child("Materi/" + judul + "."+format);
                        uploadTask = childRef.putFile(file)


                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        childRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {

                                                final Uri downloadUrl = uri;
                                                String urlfile = downloadUrl.toString();
                                                uploadMateri(judul, urlfile, UIDuser, username, uidAkses,typeFile);
                                                progressDialog.dismiss();
                                                judulMateri.setText(null);
                                                file = null;
                                                linkMateri.setText(null);

                                            }
                                        });


                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle unsuccessful uploads
                                        progressDialog.dismiss();
                                        Toast.makeText(Detail_Input_Materi.this, "Kenapa gagal ppt nya", Toast.LENGTH_LONG).show();
                                        // ...
                                    }
                                })
                                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                                .getTotalByteCount());
                                        progressDialog.setMessage("Uploaded " + (int) progress + "%");
                                    }
                                });

                    }




                }
                else if(!judul.isEmpty() && file == null && !urlLink.isEmpty() ){

                    progressDialog.dismiss();
                    uploadMateri(judul, urlLink, UIDuser, username, uidAkses,typeFile);
                    getUIDMateri();
                    judulMateri.setText(null);
                    file = null;
                    linkMateri.setText(null);

                }
                else {
                    progressDialog.dismiss();
                    checkDataInput();
                }






                break;
            case R.id.btn_upload:

                getPDF();
                break;

            case R.id.checkbox_file:

                materiFile.setEnabled(false);
                materiFile.setVisibility(View.INVISIBLE);
                chooseFile.setEnabled(true);
                linkMateri.setVisibility(View.INVISIBLE);
                materiLink.setVisibility(View.VISIBLE);
                materiLink.setEnabled(true);
                typeFile = true;
                break;

            case R.id.uncheckbox_file:

                materiFile.setEnabled(true);
                materiFile.setVisibility(View.VISIBLE);
                chooseFile.setEnabled(false);
                linkMateri.setVisibility(View.VISIBLE);
                materiLink.setVisibility(View.INVISIBLE);
                materiLink.setEnabled(false);
                typeFile=false;
                break;

            case R.id.btn_delete:
               alertDelete();

                break;
            case R.id.btn_check:
                Intent intent = new Intent(Detail_Input_Materi.this, List_Materi.class);

                intent.putExtra("uidAkses",uidAkses);
                intent.putExtra("uidKelas",UIDkelas);
                startActivity(intent);


                break;
            case R.id.btn_publish :
                alertPublish();

                break;
        }
    }
    private boolean isEmpty(EditText text){
        CharSequence s=text.getText().toString();

        return TextUtils.isEmpty(s);
    }

    private void formatFile (String file){
        String [] arrFormat = file.split("[.]",0);
        format = arrFormat[(arrFormat.length)-1];
    }

    private void checkDataInput(){


        if (isEmpty(judulMateri)){
            Toast.makeText(getApplicationContext(), "Judul kosong", Toast.LENGTH_SHORT).show();

            judulMateri.setError("Judul materi kosong");
        }

        if(isEmpty(linkMateri)){

            linkMateri.setError("Link url materi kosong");

        }

        if (typeFile==false && file == null){

            Toast.makeText(getApplicationContext(), "tolong dilengkapi", Toast.LENGTH_SHORT).show();

        } if(typeFile==true && file == null) {
            Toast.makeText(getApplicationContext(), "File materi kosong", Toast.LENGTH_SHORT).show();
        }
    }
    private void delete(){
        DocumentReference kelas = db.collection("Kelas")
                .document(UIDkelas);
        kelas.update("check",false);
        kelas.update("publish",false)


                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        batalpublish(uidAkses);
                    }
                });
    }
    private void alertDelete(){
        // Create the object of
        // AlertDialog Builder class
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(Detail_Input_Materi.this);

        // Set the message show for the Alert time
        builder.setMessage("Anda yakin mau menghapus kelas ini ?");

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
    private void publishAksesKelas(){
        DocumentReference akseskelas = db.collection("AksesKelas")
                .document(uidAkses);
        akseskelas.update("check",true);
        akseskelas.update("publish",true)


                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        publishKelas(UIDkelas);

                    }
                });
    }
    private void alertPublish(){
        // Create the object of
        // AlertDialog Builder class
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(Detail_Input_Materi.this);

        // Set the message show for the Alert time
        builder.setMessage("Anda yakin ingin mempublish kelas ini ?");

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
                                publishAksesKelas();
                                dialog.cancel();
                                Intent intent = new Intent(Detail_Input_Materi.this, BerandaAdmin.class);

                                startActivity(intent);
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

    private void publishKelas (String kelas){

        DocumentReference submitkelas = db.collection("Kelas")
                .document(kelas);
        submitkelas.update("publish",true)


                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Detail_Input_Materi.this, "Berhasil Publish", Toast.LENGTH_SHORT).show();

                    }
                });

    }

    public void batalpublish(String uidAkses){

        DocumentReference batalAkses = db.collection("AksesKelas")
                .document(uidAkses);
        batalAkses.update("publish",false)


                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Detail_Input_Materi.this, "Batal Publish", Toast.LENGTH_SHORT).show();

                    }
                });

    }
    private void getUIDMateri () {

        String uid = UUID.randomUUID().toString();
        DocumentReference user = db.collection("Keranjang").document(uid);

        user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override

            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {

                    DocumentSnapshot doc = task.getResult();
                    if(doc.exists()){

                        getUIDMateri();
                    } else {
                        idMateri = uid;
                    }
                }

            }

        });

    }
    private void  uploadMateri(String judul,String file,String uidAdmin, String username, String uidAkses,boolean tipeFile){



        Map<String,Object> doc = new HashMap<>();
        doc.put("id",idMateri);
        doc.put("judul",judul);
        doc.put("urlFile",file);
        doc.put("uploadBy",username);
        doc.put("uidAdmin",uidAdmin);
        doc.put("uidAkses",uidAkses);
        doc.put("tipe",tipeFile);


        doc.put("check",true);
        doc.put("created", FieldValue.serverTimestamp());
        db.collection("MateriKelas")
                .document(idMateri)
                .set(doc)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Berhasil Upload", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "gagal", Toast.LENGTH_SHORT).show();
                    }
                })


        ;


    }
    private void getPDF() {
        //for greater than lolipop versions we need the permissions asked on runtime
        //so if the permission is not available user will go to the screen to allow storage permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Detail_Input_Materi.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PICK_PDF_CODE);

            return;
        }

        //creating an intent for file chooser
        Intent intent = new Intent();
        intent.setType("application/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_PDF_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //when the user choses the file
        if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //if a file is selected
            if (data.getData() != null) {
                //uploading the file
                file=data.getData();
            }else{
                Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void inputKeranjang(String UIDuser,
                                String username,
                                String email,
                                String UIDkelas,
                                String judul,
                                String imageURL,
                                String harga,
                                String detail,
                                String mulaiKelas,
                                boolean grupchat
    ) {

        String id = UUID.randomUUID().toString();
        String keyPembelian = "kosong";
        Map<String,Object> doc = new HashMap<>();

        doc.put("id", id);
        doc.put("uiduser", UIDuser);
        doc.put("username", username);
        doc.put("email", email);

        doc.put("uidkelas", UIDkelas);
        doc.put("judul", judul);
        doc.put("harga", harga);
        doc.put("imageURL", imageURL);
        doc.put("detail", detail);
        doc.put("tanggal", mulaiKelas);
        doc.put("grupchat", grupchat);

        doc.put("keyPembelian",keyPembelian);
        doc.put("check",false);
        doc.put("created", FieldValue.serverTimestamp());

        db.collection("Keranjang")
                .document(id)
                .set(doc)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "upload", Toast.LENGTH_LONG).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "gagal", Toast.LENGTH_SHORT).show();
                    }
                })


        ;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                checkGrupchat = true;
                harga.setText(String.valueOf(hargaFull));
                detail_harga.setText("Materi dan Akses Grup chat");
                break;
            case 1:
                checkGrupchat = false;
                harga.setText(String.valueOf(hargaBiasa));
                detail_harga.setText("Materi aja");
                break;


        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(getApplicationContext(), "Harga tolong diisi", Toast.LENGTH_LONG).show();

    }
}
