package com.example.howtosurvive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Kullanici_Sayfasi_Activity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    String username_res,id_res;
    String name_res,gender_res,mail_res,token_res;
    TextView kul_ad_gelen, kul_username_gelen, kul_mail_gelen, kul_gender_gelen;
    ImageButton lokasyon_ekle, bilgi_guncelle;
    String url,headerSecondPart;
    RequestQueue requestQueue;
    Button but;
    List<LokasyonList> lokasyonList;
    RecyclerView recyclerViewLokasyon;
    AdapterLokasyon adapterLokasyon;

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
        token_res=intent.getStringExtra("token");

        url = "https://how-to-survive.herokuapp.com/api/location";
        headerSecondPart="Bearer "+ token_res;
        requestQueue = Volley.newRequestQueue(this);

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
        bilgi_guncelle=findViewById(R.id.bilgiDüzenle);

        lokasyon_ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lokasyon_ekle_sayfasina_gec();
            }
        });

        bilgi_guncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bilgi_guncelle_sayfasina_gec();
            }
        });

        lokasyonList = new ArrayList<>();
        recyclerViewLokasyon = findViewById(R.id.recyclerViewLokasyon);
        getLokasyon();

    }

    private void getLokasyon(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject data_object= response.getJSONObject("data");
                            JSONArray locationRecord = data_object.getJSONArray("locationRecords");


                            for (int i=0; i<locationRecord.length();i++){

                                JSONObject locationRec = locationRecord.getJSONObject(i);
                                LokasyonList lokasyonlar = new LokasyonList();

                                if (locationRec.getString("user").equals(id_res)){


                                    lokasyonlar.setKonum_ad(locationRec.getString("village"));
                                    lokasyonlar.setIl(locationRec.getString("city"));
                                    lokasyonlar.setIlce(locationRec.getString("town"));

                                    lokasyonList.add(lokasyonlar);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        adapterLokasyon = new AdapterLokasyon(getApplicationContext(),lokasyonList);
                        recyclerViewLokasyon.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerViewLokasyon.setAdapter(adapterLokasyon);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }

        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                //headers.put("Content-Type","application/json");
                headers.put("Authorization",headerSecondPart);
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        requestQueue.add(request);
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

    public void lokasyon_ekle_sayfasina_gec(){
        Intent intent_lokasyon = new Intent(this,Lokasyon_Ekle_Activity.class);
        intent_lokasyon.putExtra("username",username_res);
        intent_lokasyon.putExtra("id",id_res);
        intent_lokasyon.putExtra("name",name_res);
        intent_lokasyon.putExtra("gender",gender_res);
        intent_lokasyon.putExtra("mail",mail_res);
        intent_lokasyon.putExtra("token",token_res);
        startActivity(intent_lokasyon);
    }

    public void bilgi_guncelle_sayfasina_gec(){
        Intent intent_bilgi_guncelle = new Intent(this,Bilgi_Guncelle_Activity.class);
        intent_bilgi_guncelle.putExtra("username",username_res);
        intent_bilgi_guncelle.putExtra("id",id_res);
        intent_bilgi_guncelle.putExtra("name",name_res);
        intent_bilgi_guncelle.putExtra("gender",gender_res);
        intent_bilgi_guncelle.putExtra("mail",mail_res);
        intent_bilgi_guncelle.putExtra("token",token_res);
        startActivity(intent_bilgi_guncelle);
    }

    public static void cikis(Activity activity){
        activity.finishAffinity();
        System.exit(0);

    }

}
/*
{"message":"Location records fetched successfully",
        "data":
        {
            "locationRecords":[{"_id":"605e645a7f5bd646a0efb949","city":"Ankara","town":"Yenimahalle","latitude":549.68,"longitude":6164634,"__v":0},
        {"_id":"605e84952615f100043be6b3","city":"ankara","town":"cankaya","village":"park","latitude":7894,"longitude":2545,"__v":0},
        {"_id":"605e84f42615f100043be6b4","city":"ankara","town":"cayyolu","latitude":7894,"longitude":2545,"__v":0},
        {"_id":"605e859d2615f100043be6b7","city":"ankara","town":"sincan","latitude":7894,"longitude":2545,"__v":0},
        {"_id":"605f4d65af7a300004c224a7","city":"ankara","town":"cankaya","latitude":7894,"longitude":2545,"__v":0},
        {"_id":"6060372e031d2f0004b251ae","city":"Ankara","town":"Çayyolu","latitude":7894,"longitude":2545,"__v":0},
        {"_id":"606a1d2b3e9a6b00046c917c","city":"ankara","town":"cankaya","latitude":37.40622122323731,"longitude":-122.10997989401221,"__v":0},
        {"_id":"606da1efe40e6660a0ee94d9","city":"CAnkara","town":"CYenimahalle","latitude":123,"longitude":312,"user":"603d04814384fe0004d6cd31","__v":0},
        {"_id":"606db4fc148a125adc950f49","latitude":648465449,"longitude":54161,"user":"603ab58d4bcb472c58aac783","__v":0},
        {"_id":"606db533148a125adc950f4a","latitude":648465449,"longitude":54161,"user":"603ab58d4bcb472c58aac783","__v":0},
        {"_id":"606db55c326a7a505883966b","latitude":648465449,"longitude":54161,"user":"603ab58d4bcb472c58aac783","__v":0},
        {"_id":"606e0cc46b1eb00004d8b457","city":"ankara","town":"cayyolu","latitude":37.421452169748676,"longitude":-122.08394939079881,"__v":0},
        {"_id":"606e0d4f6b1eb00004d8b458","city":"ankara","town":"cayyolu","latitude":37.41819958670926,"longitude":-122.08358628675343,"user":"606036bf031d2f0004b251ad","__v":0}]
        }
}*/