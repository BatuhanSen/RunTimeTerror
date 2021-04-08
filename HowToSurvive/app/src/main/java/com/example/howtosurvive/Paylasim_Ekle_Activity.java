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
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Paylasim_Ekle_Activity extends AppCompatActivity {

    private static final int PERMISSION_CODE=1000;
    private static final int IMAGE_CAPTURE_CODE=1001;
    private static final int PERMISSION_CODE_GALERI=1001;
    private static final int IMAGE_PICK_CODE=1000;

    String username_res,id_res;
    String mail_res,name_res,gender_res,token_res;
    ImageView foto;
    ImageButton foto_cek,foto_ekle;
    Uri foto_uri, foto_galeri_uri;
    EditText paylasim_baslik,paylasim_icerik;
    String paylasim_basliki,paylasim_iceriki;
    private RequestQueue paylasimQueue;
    Button paylas_but;
    String headerSecondPart;

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
        token_res = intent.getStringExtra("token");

        //url = "https://how-to-survive.herokuapp.com/api/post"+"/"+id_res;
        headerSecondPart="Bearer "+ token_res;

        paylasim_baslik=findViewById(R.id.paylasim_baslik);
        paylasim_icerik=findViewById(R.id.paylasim_icerik);
        paylas_but=findViewById(R.id.paylas_but);
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

        paylasimQueue = Volley.newRequestQueue(Paylasim_Ekle_Activity.this);

        paylas_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonPost_paylasim();
            }
        });

    }

    private void jsonPost_paylasim(){

        String url = "https://how-to-survive.herokuapp.com/api/post";

        paylasim_basliki = paylasim_baslik.getText().toString().trim();
        paylasim_iceriki = paylasim_icerik.getText().toString().trim();
        //System.out.println(paylasim_basliki);
        //System.out.println(paylasim_iceriki);

        JSONObject paylasim = new JSONObject();
        try {
            paylasim.put("title",paylasim_basliki);
            paylasim.put("content",paylasim_iceriki);
            paylasim.put("userId",id_res);

        }catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,paylasim,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("Response", response.toString());
                        if (response.toString().contains("successfully") ){
                            Toast.makeText(Paylasim_Ekle_Activity.this,"Paylaşımınız eklendi.",Toast.LENGTH_LONG).show();
                            anasayfaya_gec();
                        }
                        else{
                            Toast.makeText(Paylasim_Ekle_Activity.this,"Paylaşımınız eklenemedi.",Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error.Response", error.toString());
                Toast.makeText(Paylasim_Ekle_Activity.this,"Paylaşım eklenemedi..",Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type","application/json");
                headers.put("Authorization",headerSecondPart);
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        paylasimQueue.add(request);

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

    public void anasayfaya_gec(){
        Intent intent_anasayfa = new Intent(this,MainActivity.class);
        intent_anasayfa.putExtra("username",username_res);
        intent_anasayfa.putExtra("id",id_res);
        intent_anasayfa.putExtra("name",name_res);
        intent_anasayfa.putExtra("gender",gender_res);
        intent_anasayfa.putExtra("mail",mail_res);
        intent_anasayfa.putExtra("token",token_res);
        startActivity(intent_anasayfa);
    }

}