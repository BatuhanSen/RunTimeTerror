package com.example.howtosurvive;

public class LokasyonList {
    String konum_ad;
    String il;
    String ilce;
    String adres;

    public LokasyonList(String konum_ad,String il, String ilce,String adres){
        this.konum_ad=konum_ad;
        this.il=il;
        this.ilce=ilce;
        this.adres=adres;
    }

    public LokasyonList(){

    }

    public String getIl() {
        return il;
    }

    public String getIlce() {
        return ilce;
    }

    public String getKonum_ad() {
        return konum_ad;
    }

    public String getAdres() {
        return adres;
    }

    public void setIl(String il) {
        this.il = il;
    }

    public void setIlce(String ilce) {
        this.ilce = ilce;
    }

    public void setKonum_ad(String konum_ad) {
        this.konum_ad = konum_ad;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }
}
