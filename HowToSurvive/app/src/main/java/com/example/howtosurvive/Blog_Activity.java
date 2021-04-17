package com.example.howtosurvive;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

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

public class Blog_Activity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    String username_res,id_res;
    String mail_res,name_res,gender_res,token_res;
    ImageButton paylasim_ekle;
    private static final String url = "https://how-to-survive.herokuapp.com/api/post";
    String headerSecondPart;
    List<PaylasimList> paylasimList;
    RecyclerView recyclerViewPaylasim;
    AdapterPaylasim adapterPaylasim;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); //default action barı kaldır
        setContentView(R.layout.activity_blog);
        drawerLayout = findViewById(R.id.drawer_layout); // bugfix but this line after setcontentView or crash

        Intent intent = getIntent(); //kullanici girisi responsendan gelen veriler

        username_res = intent.getStringExtra("username");
        id_res = intent.getStringExtra("id");
        mail_res = intent.getStringExtra("mail");
        name_res = intent.getStringExtra("name");
        gender_res = intent.getStringExtra("gender");
        token_res = intent.getStringExtra("token");

        //url = "https://how-to-survive.herokuapp.com/api/post"+"/"+id_res;
        headerSecondPart="Bearer "+ token_res;

        paylasim_ekle=findViewById(R.id.paylasimEkle_but);

        paylasim_ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(url);
                System.out.println(headerSecondPart);
                paylasim_ekle_sayfasina_gec();
            }
        });

        requestQueue = Volley.newRequestQueue(this);
        paylasimList = new ArrayList<>();
        recyclerViewPaylasim = findViewById(R.id.recyclerViewBlog);
        getPaylasimlar();

    }

    private void getPaylasimlar(){

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        try {
                            JSONObject data_object= response.getJSONObject("data");
                            JSONArray posts = data_object.getJSONArray("posts");
                            //System.out.println(response.toString());

                            for (int i=posts.length()-1; i>=0;i--){
                                JSONObject paylasim = posts.getJSONObject(i);
                                PaylasimList paylasimlar = new PaylasimList();

                                paylasimlar.setBaslik(paylasim.getString("title"));
                                paylasimlar.setIcerik(paylasim.getString("content"));
                                paylasimlar.setUsername(paylasim.getString("author"));

                                if (!(paylasim.isNull("imageUrl"))){ //image olabilir olmayabilir
                                    paylasimlar.setImage(paylasim.getString("imageUrl"));
                                }


                                String Fulltarih =paylasim.getString("createdAt");
                                String tarih = Fulltarih.substring(0,Fulltarih.indexOf('T'));
                                String yıl = tarih.substring(0,tarih.indexOf('-'));
                                String ay = tarih.substring(tarih.indexOf('-')+1,tarih.lastIndexOf('-'));
                                String gun = tarih.substring(tarih.lastIndexOf('-')+1);
                                String ayHarfle="";

                                switch (ay){
                                    case "01":
                                        ayHarfle="Ocak";
                                        break;
                                    case "02":
                                        ayHarfle="Şubat";
                                        break;
                                    case "03":
                                        ayHarfle="Mart";
                                        break;
                                    case "04":
                                        ayHarfle="Nisan";
                                        break;
                                    case "05":
                                        ayHarfle="Mayıs";
                                        break;
                                    case "06":
                                        ayHarfle="Haziran";
                                        break;
                                    case "07":
                                        ayHarfle="Temmuz";
                                        break;
                                    case "08":
                                        ayHarfle="Ağustos";
                                        break;
                                    case "09":
                                        ayHarfle="Eylül";
                                        break;
                                    case "10":
                                        ayHarfle="Ekim";
                                        break;
                                    case "11":
                                        ayHarfle="Kasim";
                                        break;
                                    case "12":
                                        ayHarfle="Aralık";
                                        break;
                                }

                                String sonTarih="| "+gun+" "+ayHarfle+ " "+ yıl;

                                paylasimlar.setTarih(sonTarih);

                                paylasimList.add(paylasimlar);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        adapterPaylasim = new AdapterPaylasim(getApplicationContext(),paylasimList);
                        recyclerViewPaylasim.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerViewPaylasim.setAdapter(adapterPaylasim);
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
        recreate();
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

    public void paylasim_ekle_sayfasina_gec(){
        Intent intent_paylasim_ekle = new Intent(this,Paylasim_Ekle_Activity.class);
        intent_paylasim_ekle.putExtra("username",username_res);
        intent_paylasim_ekle.putExtra("id",id_res);
        intent_paylasim_ekle.putExtra("name",name_res);
        intent_paylasim_ekle.putExtra("gender",gender_res);
        intent_paylasim_ekle.putExtra("mail",mail_res);
        intent_paylasim_ekle.putExtra("token",token_res);
        startActivity(intent_paylasim_ekle);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void cikis(Activity activity){
        activity.finishAffinity();
        System.exit(0);

    }
}
/*
 {"message":"Post created successfully",
         "data":
         {
           "post":
           {
               "metadata":{"likes":0,"dislikes":0},
               "comments":[],
               "_id":"606e06ab6b1eb00004d8b454",
               "title":"Baslik",
               "content":"Icerik",
               "author":"606036bf031d2f0004b251ad",
               "createdAt":"2021-04-07T19:23:23.035Z",
               "updatedAt":"2021-04-07T19:23:23.035Z","__v":0
            },
           "author":{"_id":"606036bf031d2f0004b251ad","username":"ipek97"}
         }
}*/