package com.example.howtosurvive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Kullanici_Sayfasi_Activity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    String username_res,id_res;
    String name_res,gender_res,mail_res;
    TextView kul_ad_gelen, kul_username_gelen, kul_mail_gelen, kul_gender_gelen;
    ImageButton lokasyon_ekle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_kullanici_sayfasi);
        drawerLayout = findViewById(R.id.drawer_layout); // bugfix but this line after setcontentView or crash

        Intent intent = getIntent(); //kullanici girisi responsendan gelen veriler

        username_res = intent.getStringExtra("username");
        id_res = intent.getStringExtra("id");
        name_res=intent.getStringExtra("name");
        mail_res=intent.getStringExtra("mail");
        gender_res=intent.getStringExtra("gender");

        kul_ad_gelen=findViewById(R.id.kul_ad_gelen);
        kul_username_gelen= findViewById(R.id.kul_username_gelen);
        kul_gender_gelen = findViewById(R.id.kul_gender_gelen);
        kul_mail_gelen = findViewById(R.id.kul_mail_gelen);

        if (gender_res.equals("K"))
            gender_res="Kadın";
        if (gender_res.equals("E"))
            gender_res="Erkek";

        kul_ad_gelen.setText(name_res);
        kul_username_gelen.setText(username_res);
        kul_mail_gelen.setText(mail_res);
        kul_gender_gelen.setText(gender_res);

        lokasyon_ekle=findViewById(R.id.lokasyon_ekle);

        lokasyon_ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lokasyon_ekle_sayfasina_gec();
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
        anasayfaya_gec();
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
        recreate();
    }

    public void ClickExit(View view){cikis(this);}

    public void dogal_afet_sayfasina_gec(){
        Intent intent_dogal = new Intent(this,Dogal_Afetler_Activity.class); //intent ile pass value
        intent_dogal.putExtra("username",username_res);
        intent_dogal.putExtra("id",id_res);
        intent_dogal.putExtra("name",name_res);
        intent_dogal.putExtra("gender",gender_res);
        intent_dogal.putExtra("mail",mail_res);
        startActivity(intent_dogal);
    }

    public void acil_durum_sayfasina_gec(){
        Intent intent_acil = new Intent(this,Acil_Durum_Activity.class);
        intent_acil.putExtra("username",username_res);
        intent_acil.putExtra("id",id_res);
        intent_acil.putExtra("name",name_res);
        intent_acil.putExtra("gender",gender_res);
        intent_acil.putExtra("mail",mail_res);
        startActivity(intent_acil);
    }

    public void blog_sayfasina_gec(){
        Intent intent_blog = new Intent(this,Blog_Activity.class);
        intent_blog.putExtra("username",username_res);
        intent_blog.putExtra("id",id_res);
        intent_blog.putExtra("name",name_res);
        intent_blog.putExtra("gender",gender_res);
        intent_blog.putExtra("mail",mail_res);
        startActivity(intent_blog);
    }

    public void iletisim_sayfasina_gec(){
        Intent intent_iletisim = new Intent(this,Iletisim_Activity.class);
        intent_iletisim.putExtra("username",username_res);
        intent_iletisim.putExtra("id",id_res);
        intent_iletisim.putExtra("name",name_res);
        intent_iletisim.putExtra("gender",gender_res);
        intent_iletisim.putExtra("mail",mail_res);
        startActivity(intent_iletisim);
    }

    public void kullanici_sayfasina_gec(){
        Intent intent_kullanici = new Intent(this,Kullanici_Sayfasi_Activity.class);
        intent_kullanici.putExtra("username",username_res);
        intent_kullanici.putExtra("id",id_res);
        intent_kullanici.putExtra("name",name_res);
        intent_kullanici.putExtra("gender",gender_res);
        intent_kullanici.putExtra("mail",mail_res);
        startActivity(intent_kullanici);
    }

    public void anasayfaya_gec(){
        Intent intent_anasayfa = new Intent(this,MainActivity.class);
        intent_anasayfa.putExtra("username",username_res);
        intent_anasayfa.putExtra("id",id_res);
        intent_anasayfa.putExtra("name",name_res);
        intent_anasayfa.putExtra("gender",gender_res);
        intent_anasayfa.putExtra("mail",mail_res);
        startActivity(intent_anasayfa);
    }

    public void lokasyon_ekle_sayfasina_gec(){
        Intent intent_lokasyon = new Intent(this,Lokasyon_Ekle_Activity.class);
        intent_lokasyon.putExtra("username",username_res);
        intent_lokasyon.putExtra("id",id_res);
        intent_lokasyon.putExtra("name",name_res);
        intent_lokasyon.putExtra("gender",gender_res);
        intent_lokasyon.putExtra("mail",mail_res);
        startActivity(intent_lokasyon);
    }

    public static void cikis(Activity activity){
        activity.finishAffinity();
        System.exit(0);

    }

}