package com.goodboaiz.duanmau.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.goodboaiz.duanmau.database.DPHelper;
import com.goodboaiz.duanmau.model.Sach;
import com.goodboaiz.duanmau.model.Top10;

import java.util.ArrayList;
import java.util.List;

public class ThongKeDAO {
    private final DPHelper dbHelper;
    private Context context;

    public ThongKeDAO(Context context) {
        dbHelper = new DPHelper(context);
        this.context = context;
    }

    public ArrayList<Top10> getTop() {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        ArrayList<Top10> topList = new ArrayList<>();
        SachDAO sachDAO = new SachDAO(context);

        String query = "SELECT maSach, COUNT(maSach) AS soLuong FROM PhieuMuon GROUP BY maSach ORDER BY soLuong DESC LIMIT 10 ";

        Cursor cursor = database.rawQuery(query, null);
        while (cursor.moveToNext()) {
            Top10 top = new Top10();
            int maSach = cursor.getInt(0);
            Sach sach = sachDAO.selectSach(maSach);
            top.setTenSach(sach.getTenSach());
            top.setSoLuong(cursor.getInt(1));
            topList.add(top);
        }
        return topList;
    }


    public int getDoanhThu(String fromDay, String toDay) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        List<Integer> list = new ArrayList<>();

        String query = "SELECT SUM(giaMuon) AS doanhThu FROM PhieuMuon WHERE ngayMuon BETWEEN ? AND ? ";

        Cursor cursor = database.rawQuery(query, new String[]{fromDay, toDay});

        while (cursor.moveToNext()) {
            try {
                list.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("doanhThu"))));
            } catch (Exception ex) {
                list.add(0);
            }
        }
        return list.get(0);
    }
}
