package com.haiprj.apps.fakeroom.model;

public class ThuChi {
    private int id;
    private String tenKhoan;
    private String ngayThang;
    private float soTien;
    private boolean isThu;

    public ThuChi(String tenKhoan, String ngayThang, float soTien, boolean isThu) {
        this.tenKhoan = tenKhoan;
        this.ngayThang = ngayThang;
        this.soTien = soTien;
        this.isThu = isThu;
    }

    public ThuChi() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenKhoan() {
        return tenKhoan;
    }

    public void setTenKhoan(String tenKhoan) {
        this.tenKhoan = tenKhoan;
    }

    public String getNgayThang() {
        return ngayThang;
    }

    public void setNgayThang(String ngayThang) {
        this.ngayThang = ngayThang;
    }

    public float getSoTien() {
        return soTien;
    }

    public void setSoTien(float soTien) {
        this.soTien = soTien;
    }

    public boolean isThu() {
        return isThu;
    }

    public void setThu(boolean thu) {
        isThu = thu;
    }
}
