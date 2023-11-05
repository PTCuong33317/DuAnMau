package com.goodboaiz.duanmau.model;

public class ThanhVien {
    private int maTV;
    private String username, password, hoVaTen;
    private int namSinh, trangThai;
    private String email, chucVu, Cmnd;


    public ThanhVien() {
    }

    public ThanhVien(int maTV, String username, String password, String hoVaTen, int namSinh, int trangThai, String email, String chucVu, String cmnd) {
        this.maTV = maTV;
        this.username = username;
        this.password = password;
        this.hoVaTen = hoVaTen;
        this.namSinh = namSinh;
        this.trangThai = trangThai;
        this.email = email;
        this.chucVu = chucVu;
        this.Cmnd = cmnd;
    }

    public ThanhVien(String hoVaTen, int namSinh, String Cmnd) {
        this.username = "";
        this.password = "";
        this.hoVaTen = hoVaTen;
        this.namSinh = namSinh;
        this.Cmnd = Cmnd;
        this.trangThai = 0;
        this.email = "";
        this.chucVu = "";
    }

    public ThanhVien(int maTV, String hoVaTen, int namSinh, String Cmnd) {
        this.maTV = maTV;
        this.hoVaTen = hoVaTen;
        this.namSinh = namSinh;
        this.username = "";
        this.password = "";
        this.trangThai = 0;
        this.email = "";
        this.chucVu = "";
        this.Cmnd = Cmnd;
    }

    public ThanhVien(int maTV, String username, String password, String hoVaTen, int namSinh, String email, int trangThai, String chucVu) {
        this.maTV = maTV;
        this.username = username;
        this.password = password;
        this.hoVaTen = hoVaTen;
        this.namSinh = namSinh;
        this.email = email;
        this.trangThai = trangThai;
        this.chucVu = chucVu;
    }

    public String getCmnd() {
        return Cmnd;
    }

    public void setCmnd(String cmnd) {
        Cmnd = cmnd;
    }

    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHoVaTen() {
        return hoVaTen;
    }

    public void setHoVaTen(String hoVaTen) {
        this.hoVaTen = hoVaTen;
    }

    public int getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(int namSinh) {
        this.namSinh = namSinh;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }
}
