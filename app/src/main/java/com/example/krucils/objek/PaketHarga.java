package com.example.krucils.objek;



import java.util.HashMap;
import java.util.Map;


public class PaketHarga {

    public String id ;
    public String paket;
    public int harga;
    public String berlaku;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public PaketHarga(String id,String paket, int harga, String berlaku) {
        this.id = id;
        this.paket = paket;
        this.harga = harga;
        this.berlaku = berlaku;
    }

    public Map<String,Object> toMap(){
        HashMap<String ,Object> result = new HashMap<>();
        result.put("paket",this.paket);
        result.put("harga",this.harga);
        result.put("berlakut",this.berlaku);

        return result;
    }



}
