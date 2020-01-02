package com.example.krucils.objek;

public class Test {



        public String nama;
        public int harga;

        public Test(){}

    public Test(String nama, int harga) {
        this.nama = nama;
        this.harga = harga;
    }

    public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public int getHarga() {
            return harga;
        }

        public void setHarga(int harga) {
            this.harga = harga;
        }
}
