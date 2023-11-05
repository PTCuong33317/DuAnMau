package com.goodboaiz.duanmau.DAO;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.goodboaiz.duanmau.database.DPHelper;
import com.goodboaiz.duanmau.model.ThanhVien;

import java.util.ArrayList;

public class ThanhVienDAO { // Đổi tên lớp thành ThanhVienDAO để tránh xung đột với class ThanhVien
    private final DPHelper dpHelper;

    private boolean checkTT = false, checkTV = false;

    Context context1;

    public ThanhVienDAO(Context context) {
        dpHelper = new DPHelper(context);
        context1 = context;
    }

    @SuppressLint("Range")
    public ArrayList<ThanhVien> selectAll() { // Thay đổi kiểu dữ liệu của ArrayList thành ThanhVien
        ArrayList<ThanhVien> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dpHelper.getReadableDatabase();
        try {
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM ThanhVien", null); // Thay đổi tên bảng thành ThanhVien
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    ThanhVien user = new ThanhVien(); // Sử dụng class ThanhVien thay vì account
                    user.setUsername(cursor.getString(cursor.getColumnIndex("maTV")));
                    user.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                    user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                    user.setUsername(cursor.getString(cursor.getColumnIndex("hoVaTen")));
                    user.setUsername(cursor.getString(cursor.getColumnIndex("namSinh")));
                    user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                    user.setEmail(cursor.getString(cursor.getColumnIndex("trangThai")));
                    user.setEmail(cursor.getString(cursor.getColumnIndex("chucVu")));
                    user.setCmnd(cursor.getString(cursor.getColumnIndex("cmnd")));
                    list.add(user);
                    cursor.moveToNext();
                }
                cursor.close(); // Đóng cursor sau khi sử dụng
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqLiteDatabase.close();
        }
        return list;
    }

    public ArrayList<ThanhVien> selectAllThanhVien() {
        SQLiteDatabase database = dpHelper.getReadableDatabase();
        ArrayList<ThanhVien> thanhVienList = new ArrayList<>();

        try {
            Cursor cursor = database.rawQuery("SELECT * FROM ThanhVien", null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    thanhVienList.add(new ThanhVien(cursor.getInt(0),
                            cursor.getString(3),
                            cursor.getInt(4),
                            cursor.getString(8)));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e(TAG, "" + ex);
        }
        return thanhVienList;
    }

    public boolean addThanhVien(ThanhVien user) { // Thay đổi tên phương thức và kiểu dữ liệu của tham số
        SQLiteDatabase sqLiteDatabase = dpHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        values.put("hoVaTen", user.getHoVaTen());
        values.put("namSinh", user.getNamSinh());
        values.put("email", user.getEmail());
        values.put("trangThai", user.getTrangThai());
        values.put("chucVu", user.getChucVu());
        values.put("cmnd", user.getCmnd());
        long row = sqLiteDatabase.insert("ThanhVien", null, values); // Thay đổi tên bảng thành ThanhVien
        sqLiteDatabase.close(); // Đóng database sau khi sử dụng
        return (row > 0);
    }

    public boolean checkLogin(String username, String password) {
        SQLiteDatabase sqLiteDatabase = dpHelper.getReadableDatabase();
        try {
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM ThanhVien WHERE username = ? AND password = ?", new String[]{username, password});

            boolean isValidUser = cursor.moveToFirst();
            cursor.close();
            if (isValidUser ){
                return true;
            }else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqLiteDatabase.close();
        }
        return false;
    }

    public String forgotPassword(String email) {
        SQLiteDatabase sqLiteDatabase = dpHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT password FROM ThanhVien WHERE email=?", new String[]{email});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            String password = cursor.getString(0);
            cursor.close();
            return password;
        } else {
            cursor.close();
            return "";
        }
    }

    public boolean deleteThanhVien(ThanhVien thanhVien) {
        SQLiteDatabase database = dpHelper.getWritableDatabase();

        long row = database.delete("ThanhVien", "maTV =?",
                new String[]{String.valueOf(thanhVien.getMaTV())});
        return row != -1;
    }
    public boolean updateThanhVien(ThanhVien thanhVien) {
        SQLiteDatabase database = dpHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("hoVaTen", thanhVien.getHoVaTen());
        values.put("namSinh", thanhVien.getNamSinh());
        values.put("cmnd", thanhVien.getCmnd());

        long row = database.update("ThanhVien", values, "maTV =?",
                new String[]{String.valueOf(thanhVien.getMaTV())});
        return row != -1;
    }

    public boolean chanePassWord(ThanhVien thanhVien){
        SQLiteDatabase database = dpHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("password",thanhVien.getPassword());

        long row = database.update("ThanhVien",values,"maTV=?",new String[]{thanhVien.getMaTV()+""});
        Log.e("new Password",thanhVien.getPassword());
        return row != -1;
    }

}
