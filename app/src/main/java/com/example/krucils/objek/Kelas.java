package com.example.krucils.objek;

import java.util.Date;

public class Kelas {

    public int id;
    public String judul;
    public int harga;
    public String detail;
    public Date mulaiKelas;
    public boolean check;
    public String imageURL;

    public Kelas(){

    }

    public Kelas(int id, String judul, int harga, String detail, Date mulaiKelas, boolean check, String imageURL) {
        this.id = id;
        this.judul = judul;
        this.harga = harga;
        this.detail = detail;
        this.mulaiKelas = mulaiKelas;
        this.check = check;
        this.imageURL = imageURL;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Date getMulaiKelas() {
        return mulaiKelas;
    }

    public void setMulaiKelas(Date mulaiKelas) {
        this.mulaiKelas = mulaiKelas;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
