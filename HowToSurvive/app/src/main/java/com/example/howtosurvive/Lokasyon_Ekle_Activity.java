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

public class Lokasyon_Ekle_Activity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    String username_res,id_res;
    String mail_res,name_res,gender_res;
    EditText konum_ad,sehir_ad,ilce_ad,genis_adres;
    String konum_adi,sehir_adi,ilce_adi,genis_adresi,latitude,longitude,village;
    Button lok_ekle;
    private RequestQueue lokasyonQueue;
    private ProgressBar pbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); //default action barı kaldır
        setContentView(R.layout.activity_lokasyon_ekle);
        drawerLayout = findViewById(R.id.drawer_layout); // bugfix but this line after setcontentView or crash

        Intent intent = getIntent(); //kullanici girisi responsendan gelen veriler

        username_res = intent.getStringExtra("username");
        id_res = intent.getStringExtra("id");
        mail_res = intent.getStringExtra("mail");
        name_res = intent.getStringExtra("name");
        gender_res = intent.getStringExtra("gender");

        konum_ad=findViewById(R.id.konum_ad);
        sehir_ad=findViewById(R.id.sehir_ad);
        ilce_ad=findViewById(R.id.ilce_ad);
        genis_adres=findViewById(R.id.genis_adres);
        lok_ekle=findViewById(R.id.lok_ekle);
        pbar=findViewById(R.id.pbar_lokasyon);

        lokasyonQueue = Volley.newRequestQueue(Lokasyon_Ekle_Activity.this);

        lok_ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonPostGonder();
            }
        });
    }

    private void jsonPostGonder(){
        String url = "https://how-to-survive.herokuapp.com/api/location";

        pbar.setVisibility(View.VISIBLE);
        lok_ekle.setVisibility(View.GONE);

        sehir_adi = sehir_ad.getText().toString().trim();
        ilce_adi = ilce_ad.getText().toString().trim();
        longitude="2545";
        latitude="7894";
        village="park";


        JSONObject lokasyon = new JSONObject();
        try {
            lokasyon.put("city",sehir_adi);
            lokasyon.put("town",ilce_adi);
            //lokasyon.put("village",village);
            lokasyon.put("latitude",latitude); //latitude ve longitude olmadan post edilemiyo hata alırsın unutma
            lokasyon.put("longitude",longitude);

        }catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,lokasyon,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("Response", response.toString());
                        if (response.toString().contains("successfully") ){
                            Toast.makeText(Lokasyon_Ekle_Activity.this,"Lokasyon eklendi.",Toast.LENGTH_LONG).show();
                            anasayfaya_gec();
                        }
                        else{
                            Toast.makeText(Lokasyon_Ekle_Activity.this,"Lokasyon eklenemedi.",Toast.LENGTH_LONG).show();
                            pbar.setVisibility(View.GONE);
                            lok_ekle.setVisibility(View.VISIBLE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error.Response", error.toString());
                Toast.makeText(Lokasyon_Ekle_Activity.this,"Lokasyon eklenemedi.",Toast.LENGTH_LONG).show();
                pbar.setVisibility(View.GONE);
                lok_ekle.setVisibility(View.VISIBLE);
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
        lokasyonQueue.add(request);
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
        kullanici_sayfasina_gec();
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

    public static void cikis(Activity activity){
        activity.finishAffinity();
        System.exit(0);

    }
}