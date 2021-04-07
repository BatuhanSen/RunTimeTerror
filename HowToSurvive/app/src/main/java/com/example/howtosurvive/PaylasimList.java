package com.example.howtosurvive;

public class PaylasimList {
    String baslik;
    String icerik;
    String tarih;

    public PaylasimList(String baslik,String icerik,String tarih){
        this.baslik=baslik;
        this.icerik=icerik;
        this.tarih=tarih;
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

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public void setIcerik(String icerik) {
        this.icerik = icerik;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }
}
