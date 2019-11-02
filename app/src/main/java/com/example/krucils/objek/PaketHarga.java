package com.example.krucils.objek;



import java.util.HashMap;
import java.util.Map;


public class PaketHarga {

    public String id ;
    public String nama;

    public String harga;
    public String berlaku;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
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

    public PaketHarga(String id,String nama, String harga, String berlaku) {
        this.id = id;
        this.nama= nama;
        this.harga = harga;
        this.berlaku = berlaku;
    }

    public Map<String,Object> toMap(){
        HashMap<String ,Object> result = new HashMap<>();
        result.put("id",this.id);
        result.put("paket",this.nama);
        result.put("harga",this.harga);
        result.put("berlakut",this.berlaku);

        return result;
    }



}
