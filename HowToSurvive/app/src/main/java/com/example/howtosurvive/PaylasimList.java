package com.example.howtosurvive;

public class PaylasimList {
    String baslik;
    String icerik;
    String tarih;
    String username;

    public PaylasimList(String baslik,String icerik,String tarih,String username){
        this.baslik=baslik;
        this.icerik=icerik;
        this.tarih=tarih;
        this.username=username;
    }

    public PaylasimList() {

    }

    public String getBaslik() {
        return baslik;
    }

    public String getIcerik() {
        return icerik;
    }

    public String getTarih() {
        return tarih;
    }

    public String getUsername() {
        return username;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public void setIcerik(String icerik) {
        this.icerik = icerik;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
