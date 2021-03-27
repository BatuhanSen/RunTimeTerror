package com.example.howtosurvive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

import java.util.HashMap;
import java.util.Map;

public class Iletisim_Activity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    String username_res,id_res;
    String mail_res,name_res,gender_res;
    private RequestQueue iletisimQueue;
    EditText geribildirim_ad,geribildirim_soyad,geribildirim_mail,geribildirim_telefon,geribildirim_mesaj;
    String geribildirim_adi,geribildirim_soyadi,geribildirim_maili,geribildirim_telefonu,geribildirim_mesaji;
    Button gonder_but;
    private ProgressBar pbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); //default action barı kaldır
        setContentView(R.layout.activity_iletisim);
        drawerLayout = findViewById(R.id.drawer_layout);// bugfix but this line after setcontentView or crash

        Intent intent = getIntent(); //kullanici girisi responsendan gelen veriler

        username_res = intent.getStringExtra("username");
        id_res = intent.getStringExtra("id");
        mail_res = intent.getStringExtra("mail");
        name_res = intent.getStringExtra("name");
        gender_res = intent.getStringExtra("gender");

        geribildirim_ad=findViewById(R.id.geribildirim_ad);
        geribildirim_soyad=findViewById(R.id.geribildirim_soyad);
        geribildirim_mail=findViewById(R.id.geribildirim_mail);
        geribildirim_telefon=findViewById(R.id.geribildirim_telefon);
        geribildirim_mesaj=findViewById(R.id.geribildirim_mesaj);
        gonder_but=findViewById(R.id.gonder_but);
        pbar=findViewById(R.id.pbarr);

        iletisimQueue = Volley.newRequestQueue(Iletisim_Activity.this);

        gonder_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonPutGonderme();
            }
        });
    }

    private void jsonPutGonderme(){

        String url = "https://how-to-survive.herokuapp.com/api/contact";

        geribildirim_adi = geribildirim_ad.getText().toString().trim();
        geribildirim_soyadi = geribildirim_soyad.getText().toString().trim();
        geribildirim_telefonu = geribildirim_telefon.getText().toString().trim();
        geribildirim_maili = geribildirim_mail.getText().toString().trim();
        geribildirim_mesaji = geribildirim_mesaj.getText().toString().trim();

        JSONObject geriDonus = new JSONObject();
        try {
            geriDonus.put("name",geribildirim_adi);
            geriDonus.put("surname",geribildirim_soyadi);
            geriDonus.put("phoneNumber",geribildirim_telefonu);
            geriDonus.put("email", geribildirim_maili);
            geriDonus.put("message",geribildirim_mesaji);

        }catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url,geriDonus,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("Response", response.toString());
                        if (response.toString().contains("successfully") ){
                            Toast.makeText(Iletisim_Activity.this,"Mesajınız gönderildi.",Toast.LENGTH_LONG).show();
                            anasayfaya_gec();
                        }
                        else{
                            Toast.makeText(Iletisim_Activity.this,"Mesajınız gönderilemedi.",Toast.LENGTH_LONG).show();
                            pbar.setVisibility(View.GONE);
                            gonder_but.setVisibility(View.VISIBLE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error.Response", error.toString());
                Toast.makeText(Iletisim_Activity.this,"Mesajınız gönderilemedi.",Toast.LENGTH_LONG).show();
                pbar.setVisibility(View.GONE);
                gonder_but.setVisibility(View.VISIBLE);
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type","application/json");
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        iletisimQueue.add(request);
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
        recreate();
    }

    public void ClickKullaniciSayfasi(View view){
        kullanici_sayfasina_gec();
    }

    public void ClickExit(View view){cikis(this);}

    public void dogal_afet_sayfasina_gec(){
        Intent intent_dogal = new Intent(this,Dogal_Afetler_Activity.class); //intent ile pass value
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

    public static void cikis(Activity activity){
        activity.finishAffinity();
        System.exit(0);

    }
}