package com.example.krucils.objek;

public class Test {
    public String nama;
    public String id;
    public int harga;

    public Test() {}

    public Test(String nama, String id, int harga) {
        this.nama = nama;
        this.id = id;
        this.harga = harga;
    }

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

    public int getHarga() { return harga; }

    public void setHarga(int harga) { this.harga = harga; }
}

