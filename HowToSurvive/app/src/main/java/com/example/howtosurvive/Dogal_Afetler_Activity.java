package com.example.howtosurvive;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Dogal_Afetler_Activity extends AppCompatActivity implements OnMapReadyCallback {

    DrawerLayout drawerLayout;
    String username_res,id_res;
    String mail_res,name_res,gender_res,token_res;
    String headerSecondPart;
    CheckBox son_bir_ay,son_uc_ay,son_bir_yil,deprem_checkBox,yangin_checkBox;
    RequestQueue requestQueue;
    private static final String deprem_url="https://how-to-survive.herokuapp.com/api/earthquake";
    private static final String yangin_url="https://how-to-survive.herokuapp.com/api/fire";
    private GoogleMap map;
    int sonbirAyCheck=-1;
    int sonUcAyCheck=-1;
    int sonbirYilCheck=-1;
    int depremCheck=-1;
    int yanginCheck=-1;
    //ArrayList<LatLng> arrTum = new ArrayList<>();

    LatLng ankara = new LatLng(39.91987,32.85427);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); //default action barı kaldır
        setContentView(R.layout.activity_dogal_afetler);
        drawerLayout = findViewById(R.id.drawer_layout);// bugfix but this line after setcontentView or crash

        Intent intent = getIntent(); //kullanici girisi responsendan gelen veriler

        username_res = intent.getStringExtra("username");
        id_res = intent.getStringExtra("id");
        mail_res = intent.getStringExtra("mail");
        name_res = intent.getStringExtra("name");
        gender_res = intent.getStringExtra("gender");
        token_res = intent.getStringExtra("token");
        headerSecondPart="Bearer "+ token_res;

        son_bir_ay=findViewById(R.id.checkBox_birAy);
        son_uc_ay=findViewById(R.id.checkBox_ucAy);
        son_bir_yil=findViewById(R.id.checkBox_birYil);
        deprem_checkBox=findViewById(R.id.checkBox_deprem);
        yangin_checkBox=findViewById(R.id.checkBox_yangin);


        requestQueue = Volley.newRequestQueue(this);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync((OnMapReadyCallback) this);

        son_bir_ay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (son_bir_ay.isChecked()){
                    sonbirAyCheck=1;
                    getDepremler();
                    getYanginlar();
                }
                else{
                    sonbirAyCheck=0;
                    getDepremler();
                    getYanginlar();
                }

            }
        });

        son_uc_ay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (son_uc_ay.isChecked()){
                    sonUcAyCheck=1;
                    getDepremler();
                    getYanginlar();
                }
                else{
                    sonUcAyCheck=0;
                    getDepremler();
                    getYanginlar();

                }
            }
        });

        son_bir_yil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (son_bir_yil.isChecked()){

                    sonbirYilCheck=1;
                    getDepremler();
                    getYanginlar();

                }
                else{

                    sonbirYilCheck=0;
                    getDepremler();
                    getYanginlar();
                }
            }
        });

        deprem_checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deprem_checkBox.isChecked()){
                    depremCheck=1;
                    getDepremler();
                }
                else{
                    depremCheck=0;
                    getDepremler();

                }
            }
        });

        yangin_checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yangin_checkBox.isChecked()){
                    yanginCheck=1;
                    getYanginlar();
                }
                else{
                    yanginCheck=0;
                    getYanginlar();

                }
            }
        });



    }

    private void getDepremler(){

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, deprem_url, null,
                new Response.Listener<JSONObject>() {
                    ArrayList<LatLng> arr = new ArrayList<>();
                    final ArrayList<LatLng> sonBirAyArrList = new ArrayList<>();
                    final ArrayList<LatLng> sonUcAyArrList = new ArrayList<>();
                    final ArrayList<LatLng> sonBirYilArrList = new ArrayList<>();

                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject data_object= response.getJSONObject("data");
                            JSONArray depremler_response = data_object.getJSONArray("earthquakeRecords");

                            for (int i=0; i<depremler_response.length();i++){
                                JSONObject deprem = depremler_response.getJSONObject(i);


                                if ((!(deprem.isNull("longitude")) && !(deprem.isNull("latitude")) ) && Double.parseDouble(deprem.getString("magnitude")) >=4  ){
                                    //System.out.println(deprem.getString("longitude")+" "+deprem.getString("latitude"));
                                    double longitude = Double.parseDouble(deprem.getString("longitude"));
                                    double latitude = Double.parseDouble(deprem.getString("latitude"));
                                    LatLng templocation = new LatLng(latitude,longitude);

                                    String gerceklesme= deprem.getString("occured_at");
                                    String gerceklesme_tarih = gerceklesme.substring(0,gerceklesme.indexOf('T'));


                                    LocalDate deprem_tarih = LocalDate.parse(gerceklesme_tarih);
                                    LocalDate simdi = LocalDate.now();
                                    long kacGun =  ChronoUnit.DAYS.between(deprem_tarih,simdi);
                                    long kacAy = ChronoUnit.MONTHS.between(deprem_tarih,simdi);
                                    long kacYıl = ChronoUnit.YEARS.between(deprem_tarih,simdi);

                                    if (kacGun<=30){
                                        sonBirAyArrList.add(templocation);
                                    }
                                    if (kacAy<=3){
                                        sonUcAyArrList.add(templocation);
                                    }
                                    if (kacYıl<=1){
                                        sonBirYilArrList.add(templocation);
                                    }

                                }

                            }


                            //ArrayList<MarkerOptions> markers = new ArrayList<>();
                            //ArrayList<Marker> markers = new ArrayList<>();
                            if (depremCheck == 1) {

                                if (sonbirAyCheck == 1) {
                                    sonbirYilCheck = -1;
                                    sonbirAyCheck = -1;
                                    sonUcAyCheck = -1;

                                    for (int i = 0; i < sonBirAyArrList.size(); i++) {

                                        MarkerOptions markerOptions = new MarkerOptions().position(sonBirAyArrList.get(i)).title("Deprem");
                                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                                        map.addMarker(markerOptions);

                                    }

                                }
                                if (sonbirAyCheck == 0) {

                                    sonbirYilCheck = -1;
                                    sonbirAyCheck = -1;
                                    sonUcAyCheck = -1;
                                    map.clear();



                                }

                                if (sonUcAyCheck == 1) {
                                    sonbirYilCheck = -1;
                                    sonbirAyCheck = -1;
                                    sonUcAyCheck = -1;
                                    for (int i = 0; i < sonUcAyArrList.size(); i++) {

                                        MarkerOptions markerOptions = new MarkerOptions().position(sonUcAyArrList.get(i)).title("Deprem");
                                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                                        map.addMarker(markerOptions);

                                    }
                                }
                                if (sonUcAyCheck == 0) {
                                    sonbirYilCheck = -1;
                                    sonbirAyCheck = -1;
                                    sonUcAyCheck = -1;
                                    map.clear();
                                }

                                if (sonbirYilCheck == 1) {
                                    sonbirYilCheck = -1;
                                    sonbirAyCheck = -1;
                                    sonUcAyCheck = -1;
                                    for (int i = 0; i < sonBirYilArrList.size(); i++) {

                                        MarkerOptions markerOptions = new MarkerOptions().position(sonBirYilArrList.get(i)).title("Deprem");
                                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                                        map.addMarker(markerOptions);

                                    }
                                }
                                if (sonbirYilCheck == 0) {
                                    sonbirYilCheck = -1;
                                    sonbirAyCheck = -1;
                                    sonUcAyCheck = -1;
                                    map.clear();
                                }
                            }
                            else if (depremCheck ==0){
                                depremCheck=-1;
                                yanginCheck=-1;
                                map.clear();
                            }


                        }catch (JSONException e){
                            e.printStackTrace();
                        }
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
    public void getYanginlar(){

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, yangin_url, null,
                new Response.Listener<JSONObject>() {

                    final ArrayList<LatLng> sonBirAyArrList = new ArrayList<>();
                    final ArrayList<LatLng> sonUcAyArrList = new ArrayList<>();
                    final ArrayList<LatLng> sonBirYilArrList = new ArrayList<>();

                    @RequiresApi(api = Build.VERSION_CODES.O)
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject data_object= response.getJSONObject("data");
                            JSONArray yanginlar_response = data_object.getJSONArray("fireRecords");

                            for (int i=0; i<yanginlar_response.length();i++){
                                JSONObject yangin = yanginlar_response.getJSONObject(i);


                                if (!(yangin.isNull("longitude")) && !(yangin.isNull("latitude")) ){
                                    double longitude = Double.parseDouble(yangin.getString("longitude"));
                                    double latitude = Double.parseDouble(yangin.getString("latitude"));
                                    LatLng templocation = new LatLng(latitude,longitude);

                                    String gerceklesme= yangin.getString("occured_at");
                                    String gerceklesme_tarih = gerceklesme.substring(0,gerceklesme.indexOf('T'));



                                    LocalDate yangin_tarih = LocalDate.parse(gerceklesme_tarih);
                                    LocalDate simdi = LocalDate.now();
                                    long kacGun =  ChronoUnit.DAYS.between(yangin_tarih,simdi);
                                    long kacAy = ChronoUnit.MONTHS.between(yangin_tarih,simdi);
                                    long kacYıl = ChronoUnit.YEARS.between(yangin_tarih,simdi);

                                    if (kacGun<=30){
                                        sonBirAyArrList.add(templocation);
                                    }
                                    if (kacAy<=3){
                                        sonUcAyArrList.add(templocation);
                                    }
                                    if (kacYıl<=1){
                                        sonBirYilArrList.add(templocation);
                                    }

                                }

                            }

                            if (yanginCheck == 1 ) {

                                if (sonbirAyCheck == 1) {
                                    sonbirYilCheck = -1;
                                    sonbirAyCheck = -1;
                                    sonUcAyCheck = -1;
                                    for (int i = 0; i < sonBirAyArrList.size(); i++) {

                                        MarkerOptions markerOptions = new MarkerOptions().position(sonBirAyArrList.get(i)).title("Yangın");
                                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                                        map.addMarker(markerOptions);

                                    }
                                }
                                if (sonbirAyCheck == 0) {
                                    sonbirYilCheck = -1;
                                    sonbirAyCheck = -1;
                                    sonUcAyCheck = -1;
                                    map.clear();

                                }

                                if (sonUcAyCheck == 1) {
                                    sonbirYilCheck = -1;
                                    sonbirAyCheck = -1;
                                    sonUcAyCheck = -1;
                                    for (int i = 0; i < sonUcAyArrList.size(); i++) {

                                        MarkerOptions markerOptions = new MarkerOptions().position(sonUcAyArrList.get(i)).title("Yangın");
                                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                                        map.addMarker(markerOptions);

                                    }
                                }
                                if (sonUcAyCheck == 0) {
                                    sonbirYilCheck = -1;
                                    sonbirAyCheck = -1;
                                    sonUcAyCheck = -1;
                                    map.clear();
                                }

                                if (sonbirYilCheck == 1) {
                                    sonbirYilCheck = -1;
                                    sonbirAyCheck = -1;
                                    sonUcAyCheck = -1;
                                    for (int i = 0; i < sonBirYilArrList.size(); i++) {

                                        MarkerOptions markerOptions = new MarkerOptions().position(sonBirYilArrList.get(i)).title("Yangın");
                                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                                        map.addMarker(markerOptions);

                                    }
                                }
                                if (sonbirYilCheck == 0) {
                                    sonbirYilCheck = -1;
                                    sonbirAyCheck = -1;
                                    sonUcAyCheck = -1;
                                    map.clear();
                                }
                            }
                            else if (yanginCheck == 0){
                                depremCheck=-1;
                                yanginCheck=-1;
                                map.clear();
                            }



                        }catch (JSONException e){
                            e.printStackTrace();
                        }
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

    @Override
    public void onMapReady(GoogleMap googleMap){
        map=googleMap;
        map.moveCamera(CameraUpdateFactory.newLatLng(ankara)); //ilk acilista turkiye gosterme
        map.animateCamera(CameraUpdateFactory.zoomTo(5f));

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
        recreate();
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

    public static void cikis(Activity activity){
        activity.finishAffinity();
        System.exit(0);

    }
}

/*

{"message":"Earthquake records fetched successfully",
"data":{
    "earthquakeRecords":[{"_id":"6075ad741982e65e2070ed4b",
        "occured_at":"2021-04-11T11:10:46.000Z",
        "magnitude":2.8,
        "latitude":38.7121,
        "longitude":30.7623,"depth":1,
        "location":"Merkez-Afyonkarahisar",
        "updated_at":"2021-04-11T11:15:49.000Z"}
 */

/*

    {"message":"Fire records fetched successfully",
        "data":{
            "fireRecords":[
                {"city":"Osmaniye",
                "town":"Merkez",
                "village":"karcalar",
                "_id":"6075ad961982e65e2070f273",
                "occured_at":"2021-04-09T14:30:00.000Z",
                "riskStatus":"Tehlikeli",
                "latitude":36.3088875,
                "longitude":37.0391655},

 */