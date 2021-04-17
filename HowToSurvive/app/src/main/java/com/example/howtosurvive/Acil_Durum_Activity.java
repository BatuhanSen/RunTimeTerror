package com.example.howtosurvive;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Acil_Durum_Activity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    String username_res, id_res;
    String name_res, mail_res, gender_res,token_res;
    EditText ekstra_ihtiyac, kac_kisi;
    Button acil_but;
    double latitude,longitude;
    String adres="";
    String ekstra_ihtiyaci;
    int kisi_sayi;
    RequestQueue requestQueue;

    LocationManager locationManager;
    LocationListener locationListener;
    static boolean check=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); //default action barı kaldır
        setContentView(R.layout.activity_acil_drum);

        drawerLayout = findViewById(R.id.drawer_layout); // bugfix but this line after setcontentView or crash

        Intent intent = getIntent(); //kullanici girisi responsendan gelen veriler

        username_res = intent.getStringExtra("username");
        id_res = intent.getStringExtra("id");
        mail_res = intent.getStringExtra("mail");
        name_res = intent.getStringExtra("name");
        gender_res = intent.getStringExtra("gender");
        token_res = intent.getStringExtra("token");

        ekstra_ihtiyac = findViewById(R.id.ekstra_ihtiyac);
        kac_kisi = findViewById(R.id.kac_kisi);
        acil_but = findViewById(R.id.acil_but);

        //uygulamanın konum kullanmasina izin yoksa ekrana izin isteme cikar
        if (ContextCompat.checkSelfPermission(Acil_Durum_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Acil_Durum_Activity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }



        requestQueue = Volley.newRequestQueue(this);

        acil_but.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v)
            {
                try {
                    konumBul();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void konumBul() throws IOException {

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.NETWORK_PROVIDER;
        // I suppressed the missing-permission warning because this wouldn't be executed in my
        // case without location services being enabled
        @SuppressLint("MissingPermission")
        android.location.Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
        latitude = lastKnownLocation.getLatitude();
        longitude = lastKnownLocation.getLongitude();
        Toast.makeText(Acil_Durum_Activity.this,"Konum: "+latitude+" "+longitude,Toast.LENGTH_LONG).show();

        Geocoder geocoder = new Geocoder(Acil_Durum_Activity.this, Locale.getDefault());
        List<Address> addresses = geocoder.getFromLocation(latitude,longitude,1);
        adres = addresses.get(0).getAddressLine(0);

        if (adres.length()>0){
            String url = "https://how-to-survive.herokuapp.com/api/emergency";

            String kisi=kac_kisi.getText().toString().trim();
            if (kisi.length()!=0){
                kisi_sayi=Integer.parseInt(kisi); // personCount
            }
            ekstra_ihtiyaci=ekstra_ihtiyac.getText().toString().trim(); //message
            String headerSecondPart="Bearer "+ token_res;

            JSONObject acil = new JSONObject();
            try {
                acil.put("personCount",kisi_sayi);
                acil.put("message",ekstra_ihtiyaci);
                acil.put("user",id_res);
                acil.put("latitude",latitude);
                acil.put("longitude",longitude);
                acil.put("address",adres);

            }catch (JSONException e){
                e.printStackTrace();
            }

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,acil,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.d("Response", response.toString());
                            if (response.toString().contains("successfully") ){
                                Toast.makeText(Acil_Durum_Activity.this,"Konumunuz alındı,sisteme eklendi. Konum: "+adres,Toast.LENGTH_LONG).show();
                                anasayfaya_gec();
                            }
                            else{
                                Toast.makeText(Acil_Durum_Activity.this,"Konumunuz sisteme eklenemedi.",Toast.LENGTH_LONG).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Error.Response", error.toString());
                    Toast.makeText(Acil_Durum_Activity.this,"Konumunuz sisteme eklenemedi.",Toast.LENGTH_LONG).show();
                }
            })
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Content-Type","application/json");
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

    }


    public void jsonPost_acil() throws IOException {



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
        recreate();
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void cikis(Activity activity){
        activity.finishAffinity();
        System.exit(0);

    }
}