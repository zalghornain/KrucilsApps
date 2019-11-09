package com.example.krucils;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class InputKelasFragment extends Fragment implements View.OnClickListener {

    private ProgressBar progressBar;
    private EditText judulKelas, detailKelas, hargaKelas,mulaiKelas;
    private Button upload, uImage;
    static  final SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
    private ImageView imageView;
    private Uri filepath, imgUri;
    private final int PICK_IMAGE_REQUEST = 71;
    private StorageTask uploadTask;
    private boolean CheckImage = false;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://test-f3c56.appspot.com");

    FirebaseFirestore db;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_input_kelas, container, false);
        progressBar = v.findViewById(R.id.progressKelas);
        progressBar.setVisibility(View.GONE);

        judulKelas = v.findViewById(R.id.kelas_judul);
        detailKelas = v.findViewById(R.id.kelas_detail);
        hargaKelas = v.findViewById(R.id.kelas_harga);
        mulaiKelas = v.findViewById(R.id.kelas_mulai);
        upload = v.findViewById(R.id.upload);
        upload.setOnClickListener(this);
        uImage = v.findViewById(R.id.uImage);
        uImage.setOnClickListener(this);
        imageView = v.findViewById(R.id.imgView);


        db = FirebaseFirestore.getInstance();



        return v;
    }

    @Override
    public void onClick(View view) {



        if (view.getId()==R.id.upload) {

            final ProgressDialog progressDialog = new ProgressDialog(getActivity());

            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final String judul = judulKelas.getText().toString();
            final String detail = detailKelas.getText().toString();
            final String harga = hargaKelas.getText().toString();
            final String tanggal = mulaiKelas.getText().toString();
            //String file = uImage.getText().toString();
            boolean kosong_bastard = false;
            //if(judul.length() == 0 && detail.length() == 0 && harga.length() == 0 && tanggal.length() == 0 ){
            if(judul.equals("")&& detail.equals("") && harga.equals("") && tanggal.equals("") ){
                kosong_bastard = true;
            }
            //if (isEmpty(judul) && isEmpty(detail) && isEmpty(harga) && isEmpty(tanggal) && imageView.getDrawable() == null) {
            // lol logika eror nanti benerin


            if (kosong_bastard==false && imgUri !=null) {
            //if (imgUri !=null) {


                if (uploadTask != null && uploadTask.isInProgress()) {
                    Toast.makeText(getActivity(), "sedang diproses", Toast.LENGTH_LONG).show();


                } else {

                    final StorageReference childRef = storageRef.child("Kelas/" + System.currentTimeMillis() + "." + getExtension(imgUri));
                    uploadTask = childRef.putFile(imgUri)


                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    childRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {

                                            final Uri downloadUrl = uri;
                                            String image = downloadUrl.toString();
                                            uploadKelas(judul, detail, harga, tanggal, image);
                                            progressDialog.dismiss();
                                            Toast.makeText(getActivity(), "upload", Toast.LENGTH_LONG).show();
                                            judulKelas.setText(null);
                                            detailKelas.setText(null);
                                            hargaKelas.setText(null);
                                            mulaiKelas.setText(null);
                                            imageView.setImageURI(null);
                                            imgUri= null;


                                        }
                                    });


                                        /*
                                        uploadKelas(judul, detail, harga, tanggal, image);
                                        progressDialog.dismiss();
                                        Toast.makeText(getActivity(), "upload", Toast.LENGTH_LONG).show();

                                         */
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
                Toast.makeText(getActivity(), "Tolong dilengkapi kembali", Toast.LENGTH_LONG).show();
            }



        }



        /*
            if (view.getId()==R.id.upload) {
                String judul = judulKelas.getText().toString();
                String detail = detailKelas.getText().toString();
                String harga = hargaKelas.getText().toString();
                String tanggal = mulaiKelas.getText().toString();
                //String file = uImage.getText().toString();
                //Todo logika
                if(isEmpty(judul)){

                }


                if(uploadTask !=null&&uploadTask.isInProgress()){
                    Toast.makeText(getActivity(), "sedang diproses", Toast.LENGTH_LONG).show();
                    // lol logika eror nanti benerin

                } else {
                    StorageReference childRef = storageRef.child("Kelas/" + System.currentTimeMillis() + "." + getExtension(imgUri));
                    uploadTask = childRef.putFile(imgUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    // Get a URL to the uploaded content
                                    //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                    Toast.makeText(getActivity(), "upload", Toast.LENGTH_LONG).show();

                                    imageView.setImageURI(null);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle unsuccessful uploads
                                    // ...
                                }
                            });

                }
            }

         */
        if (view.getId()==R.id.uImage){

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(intent.ACTION_PICK);
            //startActivityForResult(intent.createChooser(intent,"Pilih gambar"), PICK_IMAGE_REQUEST);
            startActivityForResult(intent,1);

        }

    }



    private String getExtension (Uri uri){

        ContentResolver cr = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData()
                !=null)
        {
            imgUri =data.getData();
            imageView.setImageURI(imgUri);
            CheckImage = true;
        }
    }

    /*
        @Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData()
            !=null)
            try {

                    if (Build.VERSION.SDK_INT<29){

                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),filepath);
                            imageView.setImageBitmap(bitmap);

                    }else {
                        Bitmap bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(getActivity().getContentResolver(),filepath));
                        imageView.setImageBitmap(bitmap);
                    }

            }catch (IOException e){
                    e.printStackTrace();
            }
            else {
                Intent intent = new Intent(getContext(), Pembelian.class);
                startActivity(intent);
            }

        }


         */
    private boolean isEmpty(String s){
        return TextUtils.isEmpty(s);
    }

    private void uploadKelas(String judul, String detail, String harga, String tanggal, String imageURL) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        //progressDialog.setTitle("Uploading...");
        //progressDialog.show();

        String id = UUID.randomUUID().toString();
        String image = UUID.randomUUID().toString();


        Map<String, Object> doc = new HashMap<>();

        doc.put("id", id);
        doc.put("Judul", judul);
        doc.put("Harga", harga);
        doc.put("Detail", detail);
        doc.put("MulaiKelas", getDateFromString(tanggal));
        doc.put("ImageURL", imageURL);
        doc.put("Check",false);
        doc.put("timestamp", FieldValue.serverTimestamp());

        // StorageReference childRef = storageRef.child("Kelas/"+image);
        //  childRef.putFile(filepath);
        /*
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();

                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                .getTotalByteCount());
                        progressDialog.setMessage("Uploaded "+(int)progress+"%");
                    }
                });

         */

        db.collection("Kelas")
                .document(id)
                .set(doc)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "upload", Toast.LENGTH_LONG).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "gagal", Toast.LENGTH_SHORT).show();
                    }
                })


        ;

    }

    public Date getDateFromString(String datetoSave) {
        try {
            Date date = format.parse(datetoSave);
            return date;
        } catch (ParseException e) {
            return null;
        }
    }
}
