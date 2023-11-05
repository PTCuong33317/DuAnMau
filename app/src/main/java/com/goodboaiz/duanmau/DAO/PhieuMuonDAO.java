package com.goodboaiz.duanmau.DAO;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.goodboaiz.duanmau.database.DPHelper;
import com.goodboaiz.duanmau.model.PhieuMuon;

import java.util.ArrayList;

public class PhieuMuonDAO {

    private final DPHelper dbHelper;
    private Context context;

    public PhieuMuonDAO(Context context) {
        dbHelper = new DPHelper(context);
        this.context = context;
    }

    public ArrayList<PhieuMuon> selectAllPhieuMuon(){
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        ArrayList<PhieuMuon> phieuMuonList = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM PhieuMuon", null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    phieuMuonList.add(new PhieuMuon(cursor.getInt(0),
                            cursor.getInt(1),
                            cursor.getInt(2),
                            cursor.getString(3),
                            cursor.getInt(4),
                            cursor.getInt(5),
                            cursor.getString(6)));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e(TAG, "" + ex);
        }
        return phieuMuonList;
    }
    public boolean addPhieuMuon(PhieuMuon phieuMuon) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("trangThaiPhieuMuon", phieuMuon.getTrangThaiPhieuMuon());
        values.put("giaMuon", phieuMuon.getGiaMuon());
        values.put("ngayMuon", phieuMuon.getNgayMuon());
        values.put("maTV", phieuMuon.getMaTV());
        values.put("maSach", phieuMuon.getMaSach());
        values.put("maTT", phieuMuon.getMaTT());

        long row = database.insert("PhieuMuon", null, values);
        return row != -1;
    }

    public boolean updatePhieuMuon(PhieuMuon phieuMuon) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("trangThaiPhieuMuon", phieuMuon.getTrangThaiPhieuMuon());
        values.put("giaMuon", phieuMuon.getGiaMuon());
        values.put("ngayMuon", phieuMuon.getNgayMuon());
        values.put("maTV", phieuMuon.getMaTV());
        values.put("maSach", phieuMuon.getMaSach());
        values.put("maTT", phieuMuon.getMaTT());

        long row = database.update("PhieuMuon", values, "maPhieuMuon=?",
                new String[]{String.valueOf(phieuMuon.getMaPhieuMuon())});
        return row != -1;
    }
    public String getHoTenThanhVien(int maTV) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String hoTenThanhVien = null;

        String query = "SELECT ThanhVien.hoVaTen FROM ThanhVien INNER JOIN PhieuMuon ON " +
                "ThanhVien.maTV = PhieuMuon.maTV WHERE PhieuMuon.maTV = ?";
        String args[] = {String.valueOf(maTV)};

        Cursor cursor = database.rawQuery(query, args);
        if (cursor.moveToFirst()) {
            hoTenThanhVien = cursor.getString(0);
        }
        return hoTenThanhVien;
    }

    public String getTenSach(int maSach) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String tenSach = null;

        String query = "SELECT Sach.tenSach FROM Sach INNER JOIN PhieuMuon ON " +
                "Sach.maSach = PhieuMuon.maSach WHERE PhieuMuon.maSach = ?";
        String args[] = {String.valueOf(maSach)};

        Cursor cursor = database.rawQuery(query, args);
        if (cursor.moveToFirst()) {
            tenSach = cursor.getString(0);
        }
        return tenSach;
    }
    public String getHoThuThu(String maThuThu) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String hoThuThu = null;

        String query = "SELECT ThuThu.hoTen FROM ThuThu INNER JOIN PhieuMuon ON " +
                "ThuThu.maTT = PhieuMuon.maTT WHERE PhieuMuon.maTT = ?";
        String args[] = {String.valueOf(maThuThu)};

        Cursor cursor = database.rawQuery(query, args);
        if (cursor.moveToFirst()) {
            hoThuThu = cursor.getString(0);
        }
        return hoThuThu;
    }
}
