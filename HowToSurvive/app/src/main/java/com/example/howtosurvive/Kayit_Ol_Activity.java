package com.example.howtosurvive;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import javax.xml.validation.Validator;


public class Kayit_Ol_Activity extends AppCompatActivity {

    DatePickerDialog.OnDateSetListener setListener;
    EditText dogum_tarih, ad, soyad, email, telefon, sifre, sifreTekrar;
    Button kayitOl;

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

        ad = findViewById(R.id.kullanici_adi);
        soyad = findViewById(R.id.kullanici_soyad);
        email = findViewById(R.id.kullanici_mail);
        telefon = findViewById((R.id.kullanici_telefon));
        sifre = findViewById(R.id.kullanici_sifre);
        sifreTekrar = findViewById(R.id.sifre_tekrar);
        dogum_tarih = findViewById(R.id.kullanici_dogumTarih);

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

    }
}