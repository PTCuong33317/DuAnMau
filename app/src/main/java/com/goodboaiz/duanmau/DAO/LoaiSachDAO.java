package com.goodboaiz.duanmau.DAO;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.goodboaiz.duanmau.database.DPHelper;
import com.goodboaiz.duanmau.model.LoaiSach;

import java.util.ArrayList;

public class LoaiSachDAO {
    private final DPHelper dbHelper;
    private Context context;

    public LoaiSachDAO(Context context) {
        dbHelper = new DPHelper(context);
        this.context = context;
    }

    public ArrayList<LoaiSach> selectAllLoaiSach() {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        ArrayList<LoaiSach> loaiSachList = new ArrayList<>();

        try {
            Cursor cursor = database.rawQuery("SELECT * FROM LoaiSach", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    loaiSachList.add(new LoaiSach(cursor.getInt(0), cursor.getString(1)));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e(TAG, "" + ex);
        }
        return loaiSachList;
    }

    public boolean addLoaiSach(LoaiSach loaiSach) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("tenLoaiSach", loaiSach.getTenLoaiSach());

        long row = database.insert("LoaiSach", null, values);
        return row != -1;
    }

    public boolean updateLoaiSach(LoaiSach loaiSach) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("tenLoaiSach", loaiSach.getTenLoaiSach());

        long row = database.update("LoaiSach", values, "maLoaiSach =?",
                new String[]{String.valueOf(loaiSach.getMaLoaiSach())});
        return row != -1;
    }

    public boolean deleteLoaiSach(LoaiSach loaiSach) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        long row = database.delete("LoaiSach", "maLoaiSach =?",
                new String[]{String.valueOf(loaiSach.getMaLoaiSach())});
        return row != -1;
    }
}
