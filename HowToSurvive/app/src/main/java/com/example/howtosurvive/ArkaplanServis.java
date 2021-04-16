package com.example.howtosurvive;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.howtosurvive.Bildirim.CHANNEL_ID;
import static com.example.howtosurvive.Bildirim.CHANNEL_ID1;

public class ArkaplanServis extends Service {

    private static final String deprem_url="https://how-to-survive.herokuapp.com/api/earthquake";
    private static final String yangin_url="https://how-to-survive.herokuapp.com/api/fire";
    RequestQueue requestQueue;
    PendingIntent pendingIntent;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       // return super.onStartCommand(intent, flags, startId);

        String token_res = intent.getStringExtra("token");
        Intent notificationIntent = new Intent(this,Giris_Activity.class);
        pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);


        Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID1)
                .setContentTitle("How To Survive").setContentText("Uygulama arka planda çalışmaktadır.")
                .setContentIntent(pendingIntent).build();

        startForeground(2,notification);

        requestQueue = Volley.newRequestQueue(this);

        Timer timerDeprem = new Timer ();
        TimerTask taskDeprem = new TimerTask () {
            @Override
            public void run () {
                getDeprem(token_res);
            }
        };

        timerDeprem.scheduleAtFixedRate(taskDeprem , 0l, 1 * (60*1000)); // her 1 dakikada

        Timer timerYangin = new Timer ();
        TimerTask taskYangin = new TimerTask () {
            @Override
            public void run () {
                getYangin(token_res);
            }
        };

        timerYangin.scheduleAtFixedRate(taskYangin , 0l, 1 * (60*1000)); // her 1 dakikada

        return START_REDELIVER_INTENT;

    }

    private void getDeprem(String token_res) {
        String headerSecondPart="Bearer "+ token_res;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, deprem_url, null,
                new Response.Listener<JSONObject>() {

                    @RequiresApi(api = Build.VERSION_CODES.O)
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject data_object= response.getJSONObject("data");
                            JSONArray depremler_response = data_object.getJSONArray("earthquakeRecords");

                            for (int i=0; i<depremler_response.length();i++){
                                JSONObject deprem = depremler_response.getJSONObject(i);


                                if ((!(deprem.isNull("longitude")) && !(deprem.isNull("latitude")) )){
                                    //System.out.println(deprem.getString("longitude")+" "+deprem.getString("latitude"));
                                    double longitude = Double.parseDouble(deprem.getString("longitude"));
                                    double latitude = Double.parseDouble(deprem.getString("latitude"));
                                    LatLng templocation = new LatLng(latitude,longitude);

                                    String konum = deprem.getString("location");
                                    String gerceklesme= deprem.getString("updated_at");
                                    String deprem_tarih=gerceklesme.substring(0,gerceklesme.indexOf('Z'));
                                    String buyukluk=deprem.getString("magnitude");


                                    LocalDateTime deprem_tarihi = LocalDateTime.parse(deprem_tarih).plusHours(3);

                                    LocalDateTime simdi = LocalDateTime.now();

                                    long kacDakika =  ChronoUnit.MINUTES.between(deprem_tarihi,simdi);


                                    if (kacDakika == 0){
                                        String mesaj = konum+" konumunda "+buyukluk+" büyüklüğünde deprem gerçekleşmiştir. Artçıl depremler yaşanabilir";
                                        Notification notification = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID)
                                                .setStyle(new NotificationCompat.BigTextStyle().bigText(mesaj))
                                                .setContentTitle("Deprem Gerçekleşti.")
                                                .setContentText(mesaj)
                                                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                                                .setContentIntent(pendingIntent).build();

                                        startForeground(1,notification);

                                    }

                                }

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

    private void getYangin(String token_res){
        String headerSecondPart="Bearer "+ token_res;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, yangin_url, null,
                new Response.Listener<JSONObject>() {

                    @RequiresApi(api = Build.VERSION_CODES.O)
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        try {
                            JSONObject data_object= response.getJSONObject("data");
                            JSONArray yanginlar_response = data_object.getJSONArray("fireRecords");

                            for (int i=0; i<yanginlar_response.length();i++) {
                                JSONObject yangin = yanginlar_response.getJSONObject(i);

                                if ((!(yangin.isNull("longitude")) && !(yangin.isNull("latitude"))) ) {
                                    double longitude = Double.parseDouble(yangin.getString("longitude"));
                                    double latitude = Double.parseDouble(yangin.getString("latitude"));
                                    LatLng templocation = new LatLng(latitude, longitude);

                                    String sehir = yangin.getString("city");
                                    String ilce = yangin.getString("town");
                                    String gerceklesme = yangin.getString("occured_at");
                                    String gerceklesme_tarih = gerceklesme.substring(0, gerceklesme.indexOf('Z'));


                                    LocalDateTime yangin_tarihi = LocalDateTime.parse(gerceklesme_tarih);

                                    LocalDateTime yangin_tarih_son = yangin_tarihi.plusHours(3);

                                    LocalDateTime simdi = LocalDateTime.now();


                                    long kacDakika =  ChronoUnit.MINUTES.between(yangin_tarih_son,simdi);

                                    System.out.println(kacDakika);

                                    if (kacDakika <=1){
                                        String mesaj = sehir+ " "+ ilce +" konumunda tehlikeli statüsünde "+ kacDakika+ " dakika önce yangın başlamıştır.";

                                        Notification notification = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID)
                                                .setContentTitle("Yangın Başladı.")
                                                .setStyle(new NotificationCompat.BigTextStyle().bigText(mesaj))
                                                .setContentText(mesaj)
                                                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                                                .setContentIntent(pendingIntent).build();

                                        startForeground(1,notification);
                                    }

                                }
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
    public void onDestroy() {
        super.onDestroy();
    }
}
