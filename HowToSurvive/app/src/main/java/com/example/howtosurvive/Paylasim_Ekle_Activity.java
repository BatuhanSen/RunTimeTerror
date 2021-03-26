package com.example.howtosurvive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;

public class Paylasim_Ekle_Activity extends AppCompatActivity {

    String username_res,id_res;
    String mail_res,name_res,gender_res;

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


    }
}