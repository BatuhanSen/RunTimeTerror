package com.example.howtosurvive;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringReader;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.xml.validation.Validator;


public class Kayit_Ol_Activity extends AppCompatActivity {

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
    String kullanici_ad,sifresi,emaili,adi,cinsiyeti,telefonu;
    private RequestQueue kayitQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); //default action barı kaldır

        setContentView(R.layout.activity_kayit_ol);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int genislik = displayMetrics.widthPixels;
        int yukseklik = displayMetrics.heightPixels;

        getWindow().setLayout((int)(genislik*0.8),(int)(yukseklik*0.8));

        kayitOl = findViewById(R.id.kayit_olma);

        ad = findViewById(R.id.kullanici_adi);
        soyad = findViewById(R.id.kullanici_soyad);
        email = findViewById(R.id.kullanici_mail);
        telefon = findViewById((R.id.kullanici_telefon));
        sifre = findViewById(R.id.kullanici_sifre);
        sifreTekrar = findViewById(R.id.sifre_tekrar);
        dogum_tarih = findViewById(R.id.kullanici_dogumTarih);
        kullanici_adi = findViewById(R.id.kullanici_username);
        cinsiyet = findViewById(R.id.kullanici_cinsiyet);

        dogum_tarih.setFocusable(false);
        dogum_tarih.setKeyListener(null);


        Calendar takvim = Calendar.getInstance();
        final int yıl = takvim.get(Calendar.YEAR);
        final int ay = takvim.get(Calendar.MONTH);
        final int gun = takvim.get(Calendar.DAY_OF_MONTH);

        dogum_tarih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Kayit_Ol_Activity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        String DTarih = dayOfMonth + "-" + month + "-" + year;
                        dogum_tarih.setText(DTarih);
                    }
                },yıl,ay,gun);
                datePickerDialog.show();
            }
        });

        kayitQueue = Volley.newRequestQueue(Kayit_Ol_Activity.this);

            kayitOl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jsonGonderme();
                        anasayfaya_gec();
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

        JSONObject kullanici = new JSONObject();
        try {
            kullanici.put("username",kullanici_ad);
            kullanici.put("password",sifresi);
            kullanici.put("email",emaili);
            kullanici.put("name", adi);
            kullanici.put("gender",cinsiyeti);
            kullanici.put("phone",telefonu);

        }catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url,kullanici,
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

    public void anasayfaya_gec(){
        Intent intent_anasayfa = new Intent(this,MainActivity.class);
        startActivity(intent_anasayfa);
    }

}