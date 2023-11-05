package com.goodboaiz.duanmau.DAO;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.goodboaiz.duanmau.database.DPHelper;
import com.goodboaiz.duanmau.model.ThuThu;

import java.util.ArrayList;

public class ThuThuDAO {
    private final DPHelper dbhelper;
    private Context context;

    public ThuThuDAO( Context context) {
        dbhelper = new DPHelper(context);
        this.context = context;
    }


    public boolean checkLogin(String username, String password){
        SQLiteDatabase database = dbhelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT ThuThu.username, ThuThu.matKhau FROM ThuThu WHERE " +
                "ThuThu.username = ? AND ThuThu.matKhau = ?",new String[]{username,password});
        boolean isValiThuThu = cursor.moveToFirst();
        // Kiểm tra xem có dòng dữ liệu nào được trả về hay không
        if (cursor != null && cursor.moveToFirst()) {
            // Nếu có dòng dữ liệu, tức là username tồn tại trong bảng ThuThu
            cursor.close();
            return true;
        } else {
            // Nếu không có dòng dữ liệu, tức là username không tồn tại trong bảng ThuThu
            cursor.close();
            return false;
        }
    }


    public ArrayList<ThuThu> selectAllThuThu(){
        SQLiteDatabase database = dbhelper.getReadableDatabase();
        ArrayList<ThuThu> thuThuList = new ArrayList<>();

        try {
            Cursor cursor = database.rawQuery(
                    "SELECT ThuThu.maTT, ThuThu.hoTen, ThuThu.matKhau, ThuThu.username, ThuThu.trangThaiHoatDong FROM ThuThu", null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    thuThuList.add(new ThuThu(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getInt(4)));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e(TAG, "" + ex);
        }
        return thuThuList;
    }

    public ThuThu selectThuThu(String maTT) {
        SQLiteDatabase database = dbhelper.getReadableDatabase();
        ThuThu thuThu = null;

        String query = "SELECT * FROM ThuThu WHERE maTT = ?";
        String args[] = {maTT};

        Cursor cursor = database.rawQuery(query, args);

        if (cursor.moveToFirst()) {
            thuThu = new ThuThu(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4));
        }
        return thuThu;
    }

    public boolean updateStatusThuThu(ThuThu thuThu) {
        SQLiteDatabase database = dbhelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("trangThaiHoatDong", thuThu.getTrangThaiHoatDong());

        long row = database.update("ThuThu", values, "maTT = ?",
                new String[]{thuThu.getMaTT()+""});
        return row != -1;
    }

    public boolean addThuThu(ThuThu thuThu) {
        SQLiteDatabase database = dbhelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("hoTen", thuThu.getHoTen());
        values.put("matKhau", thuThu.getMatKhau());
        values.put("username", thuThu.getUsername());
        values.put("trangThaiHoatDong", thuThu.getTrangThaiHoatDong());

        long row = database.insert("ThuThu", null, values);
        return row != -1;
    }

}
