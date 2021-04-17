package com.example.howtosurvive;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    TextView ad;
    String username_res, id_res;
    String name_res,gender_res,mail_res,token_res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); //default action barı kaldır

        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);  // bugfix but this line after setcontentView or crash

        Intent intent = getIntent(); //kullanici girisi responsendan gelen veriler

        name_res=intent.getStringExtra("name");
        mail_res=intent.getStringExtra("mail");
        gender_res=intent.getStringExtra("gender");
        token_res=intent.getStringExtra("token");
        username_res = intent.getStringExtra("username");
        String username="Hoşgeldin " + intent.getStringExtra("username");
        id_res = intent.getStringExtra("id");

        ad = findViewById(R.id.username);
        ad.setText(username);

        Intent service_intent = new Intent(this, ArkaplanServis.class);
        service_intent.putExtra("token",token_res);
        service_intent.putExtra("id",id_res);

        startService(service_intent);


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

        Button kullanici_sayfasi = (Button) findViewById(R.id.kullanici_girisi_but);
        kullanici_sayfasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kullanici_sayfasina_gec();
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

    public void ClickKullaniciSayfasi(View view){
        kullanici_sayfasina_gec();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void ClickExit(View view){cikis(this);}

    public void dogal_afet_sayfasina_gec(){
        Intent intent_dogal = new Intent(this,Dogal_Afetler_Activity.class); //intent ile pass value
        intent_dogal.putExtra("username",username_res);
        intent_dogal.putExtra("id",id_res);
        intent_dogal.putExtra("name",name_res);
        intent_dogal.putExtra("gender",gender_res);
        intent_dogal.putExtra("mail",mail_res);
        intent_dogal.putExtra("token",token_res);
        startActivity(intent_dogal);
    }

    public void acil_durum_sayfasina_gec(){
        Intent intent_acil = new Intent(this,Acil_Durum_Activity.class);
        intent_acil.putExtra("username",username_res);
        intent_acil.putExtra("id",id_res);
        intent_acil.putExtra("name",name_res);
        intent_acil.putExtra("gender",gender_res);
        intent_acil.putExtra("mail",mail_res);
        intent_acil.putExtra("token",token_res);
        startActivity(intent_acil);
    }

    public void blog_sayfasina_gec(){
        Intent intent_blog = new Intent(this,Blog_Activity.class);
        intent_blog.putExtra("username",username_res);
        intent_blog.putExtra("id",id_res);
        intent_blog.putExtra("name",name_res);
        intent_blog.putExtra("gender",gender_res);
        intent_blog.putExtra("mail",mail_res);
        intent_blog.putExtra("token",token_res);
        startActivity(intent_blog);
    }

    public void iletisim_sayfasina_gec(){
        Intent intent_iletisim = new Intent(this,Iletisim_Activity.class);
        intent_iletisim.putExtra("username",username_res);
        intent_iletisim.putExtra("id",id_res);
        intent_iletisim.putExtra("name",name_res);
        intent_iletisim.putExtra("gender",gender_res);
        intent_iletisim.putExtra("mail",mail_res);
        intent_iletisim.putExtra("token",token_res);
        startActivity(intent_iletisim);
    }

    public void kullanici_sayfasina_gec(){
        Intent intent_kullanici = new Intent(this,Kullanici_Sayfasi_Activity.class);
        intent_kullanici.putExtra("username",username_res);
        intent_kullanici.putExtra("id",id_res);
        intent_kullanici.putExtra("name",name_res);
        intent_kullanici.putExtra("gender",gender_res);
        intent_kullanici.putExtra("mail",mail_res);
        intent_kullanici.putExtra("token",token_res);
        startActivity(intent_kullanici);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void cikis(Activity activity){
        activity.finishAffinity();
        System.exit(0);

    }

}