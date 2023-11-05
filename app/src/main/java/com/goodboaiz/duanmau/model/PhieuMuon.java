package com.goodboaiz.duanmau.model;

public class PhieuMuon {
    private int maPhieuMuon;

    private int trangThaiPhieuMuon;

    private int giaMuon;

    private String ngayMuon;

    private int maTV;

    private int maSach;
    private String maTT;


    public PhieuMuon(int trangThaiPhieuMuon, int giaMuon, String ngayMuon, int maThanhVien, int maSach, String maThuThu) {
        this.trangThaiPhieuMuon = trangThaiPhieuMuon;
        this.giaMuon = giaMuon;
        this.ngayMuon = ngayMuon;
        this.maTV = maThanhVien;
        this.maSach = maSach;
        this.maTT = maThuThu;
    }

    public PhieuMuon() {
    }

    public PhieuMuon(int maPhieuMuon, int trangThaiPhieuMuon, int giaMuon, String ngayMuon, int maTV, int maSach, String maTT) {
        this.maPhieuMuon = maPhieuMuon;
        this.trangThaiPhieuMuon = trangThaiPhieuMuon;
        this.giaMuon = giaMuon;
        this.ngayMuon = ngayMuon;
        this.maTV = maTV;
        this.maSach = maSach;
        this.maTT = maTT;
    }

    public int getMaPhieuMuon() {
        return maPhieuMuon;
    }

    public void setMaPhieuMuon(int maPhieuMuon) {
        this.maPhieuMuon = maPhieuMuon;
    }

    public int getTrangThaiPhieuMuon() {
        return trangThaiPhieuMuon;
    }

    public void setTrangThaiPhieuMuon(int trangThaiPhieuMuon) {
        this.trangThaiPhieuMuon = trangThaiPhieuMuon;
    }

    public int getGiaMuon() {
        return giaMuon;
    }

    public void setGiaMuon(int giaMuon) {
        this.giaMuon = giaMuon;
    }

    public String getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(String ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public String getMaTT() {
        return maTT;
    }

    public void setMaTT(String maTT) {
        this.maTT = maTT;
    }
}
