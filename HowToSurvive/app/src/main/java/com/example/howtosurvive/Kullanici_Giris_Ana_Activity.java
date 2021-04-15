package com.example.howtosurvive;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Kullanici_Giris_Ana_Activity extends AppCompatActivity {

    DatePickerDialog.OnDateSetListener setListener;
    EditText dogum_tarih;
    EditText ad;
    EditText soyad;
    EditText email;
    EditText telefon;
    EditText sifre;
    EditText sifreTekrar;
    EditText kullanici_adi;
    EditText cinsiyet;
    Button kayitOl;
    String kullanici_ad,sifresi,emaili,adi,cinsiyeti,telefonu,sifreTekrari;
    private RequestQueue kayitQueue;
    ProgressBar pbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_kullanici_giris_ana);


        kayitOl = findViewById(R.id.kayit_olma_main);
        pbar=findViewById(R.id.pbar_ana);
        ad = findViewById(R.id.kullanici_adi_main);
        soyad = findViewById(R.id.kullanici_soyad_main);
        email = findViewById(R.id.kullanici_mail_main);
        telefon = findViewById((R.id.kullanici_telefon_main));
        sifre = findViewById(R.id.kullanici_sifre_main);
        sifreTekrar = findViewById(R.id.sifre_tekrar_main);
        dogum_tarih = findViewById(R.id.kullanici_dogumTarih_main);
        kullanici_adi = findViewById(R.id.kullanici_username_main);
        cinsiyet = findViewById(R.id.kullanici_cinsiyet_main);

        kayitQueue = Volley.newRequestQueue(Kullanici_Giris_Ana_Activity.this);
        
        kayitOl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonGonderme();

            }
        });
    }

    private void jsonGonderme(){

        String url = "https://how-to-survive.herokuapp.com/api/auth/signup";

        kullanici_ad = kullanici_adi.getText().toString().trim();
        sifresi = sifre.getText().toString().trim();
        emaili = email.getText().toString().trim();
        adi = ad.getText().toString().trim();
        cinsiyeti = cinsiyet.getText().toString().trim();
        telefonu = telefon.getText().toString().trim();
        sifreTekrari= sifreTekrar.getText().toString().trim();

            if (sifreTekrari.equals(sifresi)) {

            JSONObject kullanici = new JSONObject();
            try {
                kullanici.put("username", kullanici_ad);
                kullanici.put("password", sifresi);
                kullanici.put("email", emaili);
                kullanici.put("name", adi);
                kullanici.put("gender", cinsiyeti);
                kullanici.put("phone", telefonu);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, kullanici,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.d("Response", response.toString());
                            if (response.toString().contains("successfully")) {
                                Toast.makeText(Kullanici_Giris_Ana_Activity.this, "Kayıt işlemi başarılı.", Toast.LENGTH_LONG).show();
                                kullanici_giris_gec();
                            } else {
                                Toast.makeText(Kullanici_Giris_Ana_Activity.this, "Kayıt işlemi başarısız.", Toast.LENGTH_LONG).show();
                                pbar.setVisibility(View.GONE);
                                kayitOl.setVisibility(View.VISIBLE);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Error.Response", error.toString());
                    Toast.makeText(Kullanici_Giris_Ana_Activity.this, "Kayıt işlemi başarısız.Bu mail sistemde kayıtlı.", Toast.LENGTH_LONG).show();
                    pbar.setVisibility(View.GONE);
                    kayitOl.setVisibility(View.VISIBLE);
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }

                @Override
                public String getBodyContentType() {
                    return "application/json";
                }
            };
            kayitQueue.add(request);

        }else{
                Toast.makeText(Kullanici_Giris_Ana_Activity.this, "Şifreleriniz eşleşmiyor.Tekrar deneyiniz.", Toast.LENGTH_LONG).show();
            }

    }

    public void kullanici_giris_gec(){
        Intent intent_kullanici_giris = new Intent(this,Kullanici_Girisi_Activity.class);
        startActivity(intent_kullanici_giris);
    }
}