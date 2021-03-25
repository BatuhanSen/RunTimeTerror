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

public class Kullanici_Girisi_Activity extends AppCompatActivity {

    //DrawerLayout drawerLayout;
    EditText kullanici_ad;
    EditText sifre;
    Button giris_but;
    private RequestQueue kayitQueue;
    String kullanici_adi,sifresi;
    private ProgressBar pbar;
    String username_response,id_response;
    String name_response,mail_response,gender_response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); //default action barı kaldır
        setContentView(R.layout.activity_kullanici_girisi);

        //drawerLayout = findViewById(R.id.drawer_layout);
        //Button sifre_unutma_but = (Button) findViewById(R.id.sifre_unuttum);
        /*Button kayit_ol_but = (Button) findViewById(R.id.kayit_ol_but);
        kayit_ol_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Kullanici_Girisi_Activity.this,Kayit_Ol_Activity.class));
            }
        });*/

        pbar=findViewById(R.id.pbar);
        kullanici_ad = findViewById(R.id.kullanici_ad);
        sifre = findViewById(R.id.sifre);
        giris_but = (Button) findViewById(R.id.giris_but);

        kayitQueue = Volley.newRequestQueue(Kullanici_Girisi_Activity.this);

        giris_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonPost_giris();
            }
        });
    }

    private void jsonPost_giris(){
        String url = "https://how-to-survive.herokuapp.com/api/auth/login";

        pbar.setVisibility(View.VISIBLE);
        giris_but.setVisibility(View.GONE);

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

                        try {
                            JSONObject data_object= response.getJSONObject("data");
                            JSONObject user_object = data_object.getJSONObject("user");
                             username_response = user_object.getString("username");
                             id_response = user_object.getString("_id");
                             name_response = user_object.getString("name");
                             mail_response = user_object.getString("email");
                             gender_response = user_object.getString("gender");

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Kullanici_Girisi_Activity.this,"Giriş yapılamadı.Bilgilerinizi kontrol ediniz.",Toast.LENGTH_LONG).show();
                        }

                        Log.d("Response", response.toString());
                        if (response.toString().contains("successfully") ){
                            Toast.makeText(Kullanici_Girisi_Activity.this,"Giriş Başarılı",Toast.LENGTH_LONG).show();
                            anasayfaya_gec();
                        }
                        else{
                            Toast.makeText(Kullanici_Girisi_Activity.this,"Giriş yapılamadı.Bilgilerinizi kontrol ediniz.",Toast.LENGTH_LONG).show();
                                pbar.setVisibility(View.GONE);
                                giris_but.setVisibility(View.VISIBLE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error.Response", error.toString());
                Toast.makeText(Kullanici_Girisi_Activity.this,"Giriş yapılamadı.Bilgilerinizi kontrol ediniz.",Toast.LENGTH_LONG).show();
                pbar.setVisibility(View.GONE);
                giris_but.setVisibility(View.VISIBLE);
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

    public void dogal_afet_sayfasina_gec(){
        Intent intent_dogal = new Intent(this,Dogal_Afetler_Activity.class); //intent ile pass value
        intent_dogal.putExtra("username",username_response);
        intent_dogal.putExtra("id",id_response);
        startActivity(intent_dogal);
    }

    public void acil_durum_sayfasina_gec(){
        Intent intent_acil = new Intent(this,Acil_Durum_Activity.class);
        intent_acil.putExtra("username",username_response);
        intent_acil.putExtra("id",id_response);
        intent_acil.putExtra("name",name_response);
        intent_acil.putExtra("gender",gender_response);
        intent_acil.putExtra("mail",mail_response);
        startActivity(intent_acil);
    }

    public void blog_sayfasina_gec(){
        Intent intent_blog = new Intent(this,Blog_Activity.class);
        intent_blog.putExtra("username",username_response);
        intent_blog.putExtra("id",id_response);
        intent_blog.putExtra("name",name_response);
        intent_blog.putExtra("gender",gender_response);
        intent_blog.putExtra("mail",mail_response);
        startActivity(intent_blog);
    }

    public void iletisim_sayfasina_gec(){
        Intent intent_iletisim = new Intent(this,Iletisim_Activity.class);
        intent_iletisim.putExtra("username",username_response);
        intent_iletisim.putExtra("id",id_response);
        intent_iletisim.putExtra("name",name_response);
        intent_iletisim.putExtra("gender",gender_response);
        intent_iletisim.putExtra("mail",mail_response);
        startActivity(intent_iletisim);
    }

    public void kullanici_girisi_sayfasina_gec(){
        Intent intent_kullanici = new Intent(this,Kullanici_Girisi_Activity.class);
        intent_kullanici.putExtra("username",username_response);
        intent_kullanici.putExtra("id",id_response);
        intent_kullanici.putExtra("name",name_response);
        intent_kullanici.putExtra("gender",gender_response);
        intent_kullanici.putExtra("mail",mail_response);
        startActivity(intent_kullanici);
    }

    public void anasayfaya_gec(){
        Intent intent_anasayfa = new Intent(this,MainActivity.class);
        intent_anasayfa.putExtra("username",username_response);
        intent_anasayfa.putExtra("id",id_response);
        intent_anasayfa.putExtra("name",name_response);
        intent_anasayfa.putExtra("gender",gender_response);
        intent_anasayfa.putExtra("mail",mail_response);
        startActivity(intent_anasayfa);

    }

}