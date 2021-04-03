package com.example.howtosurvive;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class Paylasim_Ekle_Activity extends AppCompatActivity {

    private static final int PERMISSION_CODE=1000;
    private static final int IMAGE_CAPTURE_CODE=1001;
    private static final int PERMISSION_CODE_GALERI=1001;
    private static final int IMAGE_PICK_CODE=1000;

    String username_res,id_res;
    String mail_res,name_res,gender_res;
    ImageView foto;
    ImageButton foto_cek,foto_ekle;
    Uri foto_uri, foto_galeri_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); //default action barı kaldır
        setContentView(R.layout.activity_paylasim_ekle);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int genislik = displayMetrics.widthPixels;
        int yukseklik = displayMetrics.heightPixels;

        getWindow().setLayout((int)(genislik*0.8),(int)(yukseklik*0.8));
        WindowManager.LayoutParams prms = getWindow().getAttributes();
        prms.gravity = Gravity.CENTER;
        prms.x=0;
        prms.y= -20;
        getWindow().setAttributes(prms);

        Intent intent = getIntent();
        username_res = intent.getStringExtra("username");
        id_res = intent.getStringExtra("id");
        mail_res = intent.getStringExtra("mail");
        name_res = intent.getStringExtra("name");
        gender_res = intent.getStringExtra("gender");

        foto = findViewById(R.id.foto);
        foto_cek = findViewById(R.id.foto_cek);
        foto_ekle = findViewById(R.id.foto_ekle);

        foto_cek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){ //gerekli izinler kontrol
                    if (checkSelfPermission(Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission,PERMISSION_CODE);
                    }
                    else{
                        kameraAc();
                    }
                }
                else {
                    kameraAc();
                }
            }
        });

        foto_ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED){
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions,PERMISSION_CODE_GALERI);
                    }
                    else {
                        galeriAc();
                    }
                }
                else{
                    galeriAc();
                }
            }
        });

    }

    private void galeriAc(){
        Intent intent_galeri =  new Intent(Intent.ACTION_PICK);
        intent_galeri.setType("image/*");
        startActivityForResult(intent_galeri,IMAGE_PICK_CODE);
    }

    private void kameraAc(){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"Yeni foto");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Kameradan cekildi");
        foto_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        Intent intent_foto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent_foto.putExtra(MediaStore.EXTRA_OUTPUT,foto_uri);
        startActivityForResult(intent_foto,IMAGE_CAPTURE_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == IMAGE_CAPTURE_CODE){ // ekranda cekilen resmi paylasma
            foto.setImageURI(foto_uri);
        }

        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            foto_galeri_uri=data.getData();
            foto.setImageURI(foto_galeri_uri);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode== PERMISSION_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                kameraAc();
            }
        }

        if (requestCode == PERMISSION_CODE_GALERI){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                galeriAc();
            }
        }
    }
}