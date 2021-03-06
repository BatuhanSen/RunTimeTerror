package com.example.howtosurvive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class Kullanici_Girisi_Activity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    EditText kullanici_ad;
    EditText sifre;
    Button giris_but;
    private RequestQueue kayitQueue;
    String kullanici_adi,sifresi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); //default action barı kaldır
        setContentView(R.layout.activity_kullanici_girisi);

        drawerLayout = findViewById(R.id.drawer_layout);

        Button sifre_unutma_but = (Button) findViewById(R.id.sifre_unuttum);

        Button kayit_ol_but = (Button) findViewById(R.id.kayit_ol_but);
        kayit_ol_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Kullanici_Girisi_Activity.this,Kayit_Ol_Activity.class));
            }
        });

        kullanici_ad = findViewById(R.id.kullanici_ad);
        sifre = findViewById(R.id.sifre);
        giris_but = (Button) findViewById(R.id.giris_but);

        kayitQueue = Volley.newRequestQueue(Kullanici_Girisi_Activity.this);

        giris_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonPost_giris();
                anasayfaya_gec();
            }
        });


    }

    private void jsonPost_giris(){
        String url = "https://how-to-survive.herokuapp.com/api/auth/login";

        kullanici_adi = kullanici_ad.getText().toString().trim();
        sifresi = sifre.getText().toString().trim();

        JSONObject kullanici = new JSONObject();
        try {
            kullanici.put("username",kullanici_adi);
            kullanici.put("password",sifresi);

        }catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,kullanici,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error.Response", error.toString());
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
        kayitQueue.add(request);
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

    public void ClickKullaniciGiris(View view){
        recreate();
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

    public void anasayfaya_gec(){
        Intent intent_anasayfa = new Intent(this,MainActivity.class);
        startActivity(intent_anasayfa);
    }

}