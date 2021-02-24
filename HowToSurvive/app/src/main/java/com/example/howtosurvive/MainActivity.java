package com.example.howtosurvive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); //default action barı kaldır

        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);

        Button dogal_afet_sayfasi = (Button) findViewById(R.id.dogal_afet_but);
        dogal_afet_sayfasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dogal_afet_sayfasina_gec(); // dogal afet butonuna tıklayınca bu metodu cagır
            }
        });

        Button acil_durum_sayfasi = (Button) findViewById(R.id.acil_durum_but);
        acil_durum_sayfasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acil_durum_sayfasina_gec(); // acil durum butonuna tıklayınca bu metodu cagır
            }
        });

        Button blog_sayfasi = (Button) findViewById(R.id.blog_but);
        blog_sayfasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blog_sayfasina_gec(); // blog butonuna tıklayınca bu metodu cagır
            }
        });

        Button iletisim_sayfasi = (Button) findViewById(R.id.iletisim_but);
        iletisim_sayfasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iletisim_sayfasina_gec(); // iletişim butonuna tıklayınca bu metodu cagır
            }
        });

        Button kullanici_girisi_sayfasi = (Button) findViewById(R.id.kullanici_girisi_but);
        kullanici_girisi_sayfasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kullanici_girisi_sayfasina_gec();
            }
        });

    }

    public void ClickMenu(View view){
        openDrawer(drawerLayout);
    }

    private static void openDrawer(DrawerLayout drawerLayout){ // nav_drawer kapamayı ekle
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickAnasayfa(View view){
        recreate();
    }

    public void ClickDogalAfet(View view){
        dogal_afet_sayfasina_gec();
    }

    public void ClickAcilDurum(View view){
        acil_durum_sayfasina_gec();
    }

    public void ClickBlog(View view){
        blog_sayfasina_gec();
    }

    public void ClickIletisim(View view){
        iletisim_sayfasina_gec();
    }

    public void ClickKullaniciGiris(View view){
        kullanici_girisi_sayfasina_gec();
    }

    public void dogal_afet_sayfasina_gec(){
        Intent intent_dogal = new Intent(this,Dogal_Afetler_Activity.class); //intent ile pass value
        startActivity(intent_dogal);
    }

    public void acil_durum_sayfasina_gec(){
        Intent intent_acil = new Intent(this,Acil_Durum_Activity.class);
        startActivity(intent_acil);
    }

    public void blog_sayfasina_gec(){
        Intent intent_blog = new Intent(this,Blog_Activity.class);
        startActivity(intent_blog);
    }

    public void iletisim_sayfasina_gec(){
        Intent intent_iletisim = new Intent(this,Iletisim_Activity.class);
        startActivity(intent_iletisim);
    }

    public void kullanici_girisi_sayfasina_gec(){
        Intent intent_kullanici = new Intent(this,Kullanici_Girisi_Activity.class);
        startActivity(intent_kullanici);
    }

}