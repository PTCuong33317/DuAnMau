package com.goodboaiz.duanmau.model;

public class ThuThu {
    private int maTT;
    private String hoTen;
    private String matKhau;

    private String username;

    private int trangThaiHoatDong;

    public ThuThu() {
    }

    public ThuThu(int maTT, String hoTen, String matKhau, String username, int trangThaiHoatDong) {
        this.maTT = maTT;
        this.hoTen = hoTen;
        this.matKhau = matKhau;
        this.username = username;
        this.trangThaiHoatDong = trangThaiHoatDong;
    }

    public ThuThu(String hoTen, String matKhau, String username, int trangThaiHoatDong) {
        this.hoTen = hoTen;
        this.matKhau = matKhau;
        this.username = username;
        this.trangThaiHoatDong = trangThaiHoatDong;
    }

    public int getMaTT() {
        return maTT;
    }

    public void setMaTT(int maTT) {
        this.maTT = maTT;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTrangThaiHoatDong() {
        return trangThaiHoatDong;
    }

    public void setTrangThaiHoatDong(int trangThaiHoatDong) {
        this.trangThaiHoatDong = trangThaiHoatDong;
    }
}
