package com.example.howtosurvive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Giris_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_giris);

        Button kullanici_girisi = (Button) findViewById(R.id.kul_giris_but);
        kullanici_girisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kullanici_girisi_sayfasina_gec();
            }
        });

        Button kayit_ol = (Button) findViewById(R.id.kayit_olma_butt);
        kayit_ol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kayit_ol_sayfasina_gec();
            }
        });
    }

    public void kullanici_girisi_sayfasina_gec(){
        Intent intent_kullanici = new Intent(this,Kullanici_Girisi_Activity.class); //intent ile pass value
        startActivity(intent_kullanici);
    }

    public void kayit_ol_sayfasina_gec(){
        Intent intent_kayit = new Intent(this,Kullanici_Giris_Ana_Activity.class); //intent ile pass value
        startActivity(intent_kayit);
    }
}