package com.goodboaiz.duanmau.model;

public class Top10 {
    private String tenSach;
    private int soLuong;

    public Top10() {
    }

    public Top10(String tenSach, int soLuong) {
        this.tenSach = tenSach;
        this.soLuong = soLuong;
    }

    public String getTenSach() {
        return tenSach;
    }

    public Top10 setTenSach(String tenSach) {
        this.tenSach = tenSach;
        return this;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public Top10 setSoLuong(int soLuong) {
        this.soLuong = soLuong;
        return this;
    }
}
