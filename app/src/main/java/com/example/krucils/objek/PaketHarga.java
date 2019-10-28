package com.example.krucils.objek;



import java.util.HashMap;
import java.util.Map;


public class PaketHarga {

    public String paket;
    public int harga;
    public String berlaku;

    public String getPaket() {
        return paket;
    }

    public void setPaket(String paket) {
        this.paket = paket;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getBerlaku() {
        return berlaku;
    }

    public void setBerlaku(String berlaku) {
        this.berlaku = berlaku;
    }

    public PaketHarga(){

    }

    public PaketHarga(String paket, int harga, String berlaku) {
        this.paket = paket;
        this.harga = harga;
        this.berlaku = berlaku;
    }


}
