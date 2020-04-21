package com.example.krucils.objek;

import java.util.Date;

public class BahanMateri {

    public String id;
    public String judul;
    public String urlFile;
    public String uidAdmin;
    public String uidAkses;
    public String uploadBy;

    public boolean check;
    public boolean tipe;
    public Date created;

    public BahanMateri(){}

    public BahanMateri(String id, String judul, String urlFile, String uidAdmin, String uidAkses, String uploadBy, boolean check, boolean tipe, Date created) {
        this.id = id;
        this.judul = judul;
        this.urlFile = urlFile;
        this.uidAdmin = uidAdmin;
        this.uidAkses = uidAkses;
        this.uploadBy = uploadBy;
        this.check = check;
        this.tipe = tipe;
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getUrlFile() {
        return urlFile;
    }

    public void setUrlFile(String urlFile) {
        this.urlFile = urlFile;
    }

    public String getUidAdmin() {
        return uidAdmin;
    }

    public void setUidAdmin(String uidAdmin) {
        this.uidAdmin = uidAdmin;
    }

    public String getUidAkses() {
        return uidAkses;
    }

    public void setUidAkses(String uidAkses) {
        this.uidAkses = uidAkses;
    }

    public String getUploadBy() {
        return uploadBy;
    }

    public void setUploadBy(String uploadBy) {
        this.uploadBy = uploadBy;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean isTipe() {
        return tipe;
    }

    public void setTipe(boolean tipe) {
        this.tipe = tipe;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
