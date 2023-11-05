package com.goodboaiz.duanmau.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DPHelper extends SQLiteOpenHelper {
    public static final String name = "PNLIB";

    public DPHelper(Context context) {
        super(context, name, null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // bảng ThanhVien
        String table_ThanhVien = "CREATE TABLE ThanhVien (\n" +
                "    maTV INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    username TEXT NOT NULL,\n" +
                "    password TEXT NOT NULL,\n" +
                "    hoVaTen TEXT NOT NULL,\n" +
                "    namSinh INTEGER NOT NULL,\n" +
                "    email TEXT NOT NULL,\n" +
                "    trangThai INTEGER NOT NULL,\n" +
                "    chucVu TEXT NOT NULL,\n" +
                " cmnd TEXT NOT NULL" +
                ");";

        db.execSQL(table_ThanhVien);
        db.execSQL("INSERT INTO ThanhVien (username, password, hoVaTen, namSinh, email, trangThai, chucVu, cmnd)\n" +
                "VALUES \n" +
                "    ('admin', 'admin', 'Pham The Cuong', 2003, 'cuongptph33317@fpt.edu.vn', 1, 'admin', '0131415134132'),\n" +
                "    ('namNH', '123', 'Pham Thanh Tuyen', 2004, 'tuyenptph33317@fpt.edu.vn', 0, 'thuThu', '0235823844134'),\n" +
                "    ('NW', '123', 'Nguyen Van Viet', 2004, 'vietnvph33317@fpt.edu.vn', 0, 'thuThu','023589427383');");


        //bảng thủ thư
        String createTableThuThu = "CREATE TABLE ThuThu (" +
                "maTT INTEGER PRIMARY KEY AUTOINCREMENT," +
                "hoTen TEXT NOT NULL," +
                "matKhau TEXT NOT NULL," +
                "username TEXT NOT NULL," +
                "trangThaiHoatDong INTEGER NOT NULL" +
                ");";
        db.execSQL(createTableThuThu);
        db.execSQL("INSERT INTO ThuThu (hoTen, matKhau, username, trangThaiHoatDong) VALUES " +
                "('Nguyen Van Viet', '123', 'thuthu1', 1), " +
                "('Pham Thanh Tuyen', '123', 'thuthu2', 0)");




        // tạo bảng sách
        String createSach = "CREATE TABLE Sach(" +
                "maSach INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenSach TEXT NOT NULL," +
                "giaMuon INTEGEER NOT NULL," +
                "maLoaiSach INTEGER," +
                "" +
                "FOREIGN KEY (maLoaiSach) REFERENCES LoaiSach (maLoaiSach) ON DELETE SET NULL);";
        db.execSQL(createSach);
        db.execSQL("INSERT INTO Sach (tenSach,giaMuon,maLoaiSach) VALUES" +
                "('MATH 8',5000,3)," +
                "('C/C++',10000,1)," +
                "('IELTS 8.0',7000,2)");

        //  tạo bảng loại sách
        String createLoaiSach = "CREATE TABLE LoaiSach(" +
                "maLoaiSach INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenLoaiSach TEXT NOT NULL );";
        db.execSQL(createLoaiSach);
        db.execSQL("INSERT INTO LoaiSach (tenLoaiSach) VALUES " +
                "('CNTT')," +
                "('ENG')," +
                "('MATH')," +
                "('HISTORY')");

        //Bảng phiếu mượn
        String createPhieuMuon = "CREATE TABLE PhieuMuon(" +
                "maPhieuMuon INTEGER PRIMARY KEY," +
                "trangThaiPhieuMuon INTEGER NOT NULL," +
                "giaMuon INTEGER," +
                "ngayMuon DATE NOT NULL," +
                "maTV INTEGER," +
                "maSach INTEGER," +
                "maTT TEXT," +
                "FOREIGN KEY (maTV) REFERENCES ThanhVien (maTV) ON DELETE SET NULL," +
                "FOREIGN KEY (maSach) REFERENCES Sach (maSach) ON DELETE SET NULL," +
                "FOREIGN KEY (maTT) REFERENCES ThuThu (maTT));";
        db.execSQL(createPhieuMuon);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            String dropTableLoaiThuThu = "drop table if exists ThuThu";
            db.execSQL(dropTableLoaiThuThu);
            String dropTableThanhVien = "drop table if exists ThanhVien";
            db.execSQL(dropTableThanhVien);
            String dropTableLoaiSach = "drop table if exists LoaiSach";
            db.execSQL(dropTableLoaiSach);
            String dropTableSach = "drop table if exists Sach";
            db.execSQL(dropTableSach);
            String dropTablePhieuMuon = "drop table if exists PhieuMuon";
            db.execSQL(dropTablePhieuMuon);

            onCreate(db);
        }
    }
}
