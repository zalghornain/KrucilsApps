package com.example.krucils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krucils.objek.Keranjang;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DetailKeranjang extends AppCompatActivity implements View.OnClickListener {

    private ImageView buktiPembayaran;
    private EditText atasnama,bank;
    private TextView hargaTotal;
    private Button  uploadImage, bayar,back;
    private int hargaAwal,hargaAkhir;
    //private ArrayList<Keranjang> keranjangList = new ArrayList<Keranjang>();
    private String currentuserUID,email,username,koderef,idPembelian;
    private Uri  imgUri;
    private  ArrayList<Keranjang> listKerangjang;
    private StorageTask uploadTask;
    private boolean CheckImage = false;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://test-f3c56.appspot.com");

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_detail_keranjang);
        atasnama=findViewById(R.id.atas_nama);
        bank = findViewById(R.id.bank);
        hargaTotal=findViewById(R.id.tv_total);
        uploadImage=findViewById(R.id.btn_image);
        bayar=findViewById(R.id.btn_bayar);
        buktiPembayaran=findViewById(R.id.thumbnail);
        getUIDPembelian();


        uploadImage.setOnClickListener(this);
        bayar.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            currentuserUID = bundle.getString("uidUser");
            email=bundle.getString("usermail");
            username=bundle.getString("username");
            koderef = bundle.getString("koderef");
            listKerangjang =(ArrayList<Keranjang>) bundle.getSerializable("keranjangList");

            hargaAwal = bundle.getInt("totalHarga");
            hargaAkhir= bundle.getInt("hargaAkhir");
           /* Keranjang array = new Keranjang();
            array = listKerangjang.get(1);
            String udin = array.getUidKeranjang();
            kodeRef.setText(udin);

            */
            formatRP(hargaAwal);
        }



        //kodeRef.setText(String.valueOf(listKerangjang.size()));


    }

    @Override
    public void onClick(View view) {

            switch (view.getId()){

                case R.id.btn_check:

                    // nanti di pake, kita tambahin dulu field poin dan kodeRef di collection User
                    break;

                case R.id.btn_image:
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(intent.ACTION_PICK);
                    //startActivityForResult(intent.createChooser(intent,"Pilih gambar"), PICK_IMAGE_REQUEST);
                    startActivityForResult(intent,1);
                    break;

                case R.id.btn_bayar:
                    checkDataInput();
                    final ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());

                    /*progressDialog.setTitle("Uploading...");
                    progressDialog.show();

                     */


                    final String checkAtasNama= atasnama.getText().toString();
                    final String checkBank = bank.getText().toString();

                    if (imgUri !=null && checkAtasNama !=null && checkBank !=null) {
                        //if (imgUri !=null) {


                        if (uploadTask != null && uploadTask.isInProgress()) {
                            Toast.makeText(getApplicationContext(), "sedang diproses", Toast.LENGTH_LONG).show();


                        } else {

                            final StorageReference childRef = storageRef.child("BuktiPembayaran/" + System.currentTimeMillis() + "." + getExtension(imgUri));
                            uploadTask = childRef.putFile(imgUri)


                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            childRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {

                                                    final Uri downloadUrl = uri;
                                                    String image = downloadUrl.toString();

                                                    //Disini Tambahin Method input ke database
                                                    //uploadKelas(judul, detail, harga,harga2, tanggal, image);
                                                    uploadKeranjang(currentuserUID,email,username,image,listKerangjang,hargaAkhir,hargaAwal,koderef,checkAtasNama,checkBank);
                                                    progressDialog.dismiss();


                                                    buktiPembayaran.setImageURI(null);
                                                    imgUri= null;


                                                }
                                            });

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            // Handle unsuccessful uploads
                                            progressDialog.dismiss();
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
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Silahkan Upload foto", Toast.LENGTH_LONG).show();
                    }



                    break;
            }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1 && resultCode == RESULT_OK && data != null && data.getData()
                !=null)
        {
            imgUri =data.getData();
            buktiPembayaran.setImageURI(imgUri);

        }
    }
    public void formatRP(int rp){
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        hargaTotal.setText(kursIndonesia.format(rp));
    }

    private boolean isEmpty(EditText text){
        CharSequence s=text.getText().toString();

        return TextUtils.isEmpty(s);
    }


    private void checkDataInput(){

        if (isEmpty(atasnama)){
            Toast.makeText(DetailKeranjang.this, "atasnama tolong diisi", Toast.LENGTH_LONG).show();

            atasnama.setError("tolong dilengkapi");
        }

        if(isEmpty(bank)){
            Toast.makeText(DetailKeranjang.this, "nama bank tolong diisi", Toast.LENGTH_LONG).show();
            bank.setError("tolong dilengkapi");

        }

        if (imgUri==null){

            Toast.makeText(DetailKeranjang.this, "Silahkan Upload foto", Toast.LENGTH_LONG).show();


        }

    }

    private String getExtension (Uri uri){

        ContentResolver cr = getApplicationContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));

    }

    private void getUIDPembelian () {

        String uid = UUID.randomUUID().toString();
        DocumentReference user = db.collection("NewKeranjang").document(uid);

        user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override

            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {

                    DocumentSnapshot doc = task.getResult();
                    if(doc.exists()){

                        getUIDPembelian();
                    } else {
                        idPembelian = uid;
                    }
                }

            }

        });

    }
    private void uploadKeranjang(String UIDuser, String email, String username , String imageURL, ArrayList keranjangList,int hargaAkhir,int hargaAwal, String kodeRef, String checkNama, String checkBank) {
        final ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
        //progressDialog.setTitle("Uploading...");
        //progressDialog.show();





        Map<String, Object> doc = new HashMap<>();

        doc.put("keyPembelian", idPembelian);
        doc.put("uidUser", currentuserUID);
        doc.put("username", username);
        doc.put("atasnama", checkNama);
        doc.put("bank", checkBank);
        doc.put("email", email);
        doc.put("hargaAwal", hargaAwal);
        doc.put("hargaAkhir", hargaAkhir);
        doc.put("kodeRef", kodeRef);
        doc.put("keranjangList", keranjangList);
        doc.put("imageURL", imageURL);
        doc.put("checkAdmin",false);
        doc.put("pembayaran",false);
        doc.put("timestamp", FieldValue.serverTimestamp());


        db.collection("NewPembelian")
                .document(idPembelian)
                .set(doc)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        updateKeypembelian(keranjangList,idPembelian);
                        Intent intents = new Intent(DetailKeranjang.this,Beranda.class);
                        startActivity(intents);


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                })


        ;

    }

    private void updateKeypembelian(ArrayList listKerangjang,String keyPembelian){
        db = FirebaseFirestore.getInstance();

        ArrayList<Keranjang> list ;
        list = listKerangjang;

            for (int i = 0;i<list.size();i++){


                Keranjang input = new Keranjang();
                input = list.get(i);
                String uidKelas = input.getUidKeranjang();
                DocumentReference keranjangg = db.collection("NewKeranjang")
                        .document(uidKelas);
                keranjangg.update("keyPembelian", keyPembelian)

                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(getApplicationContext(), "Berhasil mengirim", Toast.LENGTH_LONG).show();
                            }
                        });







            }




    }
}
