package com.goodboaiz.duanmau.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.goodboaiz.duanmau.DAO.ThanhVienDAO;
import com.goodboaiz.duanmau.Login;
import com.goodboaiz.duanmau.MainActivity;
import com.goodboaiz.duanmau.R;
import com.goodboaiz.duanmau.database.DPHelper;
import com.goodboaiz.duanmau.model.ThanhVien;

public class Fragment_doiMatkhau extends Fragment {
private ThanhVienDAO thanhvienDao;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doi_matkhau, container, false);
        thanhvienDao = new ThanhVienDAO(getContext());

        EditText txt_CP_OldPassword = view.findViewById(R.id.txt_CP_OldPassword);
        EditText txt_CP_NewPassword = view.findViewById(R.id.txt_CP_NewPassword);
        EditText txt_CP_NewPassword2 = view.findViewById(R.id.txt_CP_NewPassword2);
        Button btn_CP_Password = view.findViewById(R.id.btn_CP_Password);
        Button btn_CP_cancel = view.findViewById(R.id.btn_CP_cancel);


        btn_CP_Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txt_CP_NewPassword.getText().toString().equals(txt_CP_NewPassword2.getText().toString())
                        && getPassword(txt_CP_OldPassword.getText().toString(),txt_CP_NewPassword.getText().toString()) != null) {
                    if (thanhvienDao.chanePassWord(getPassword(txt_CP_OldPassword.getText().toString(),txt_CP_NewPassword.getText().toString()))){
                        Toast.makeText(getContext(), "thay doi mat khau thanh cong", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), Login.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getContext(),"thay doi mat khau that bai",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        btn_CP_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);

            }
        });

        return view;
    }
    @SuppressLint("Range")
    private ThanhVien getPassword(String oldPassword, String newPassword) {
        DPHelper dpHelper = new DPHelper(getContext());
        SQLiteDatabase database = dpHelper.getReadableDatabase();
        ThanhVien thanhVien = null;

        Cursor cursor = database.rawQuery("SELECT * FROM ThanhVien WHERE password=?", new String[]{oldPassword});
        if (cursor.moveToFirst()) {
            thanhVien = new ThanhVien(cursor.getInt(cursor.getColumnIndex("maTV")),
                    cursor.getString(cursor.getColumnIndex("username")),
                    newPassword,
                    cursor.getString(cursor.getColumnIndex("hoVaTen")),
                    cursor.getInt(cursor.getColumnIndex("namSinh")),
                    cursor.getString(cursor.getColumnIndex("email")),
                    cursor.getInt(cursor.getColumnIndex("trangThai")),
                    cursor.getString(cursor.getColumnIndex("chucVu"))
            );
            @SuppressLint("Range") String log = cursor.getString(cursor.getColumnIndex("password"));
            Log.e("password", log);
            cursor.close();
            dpHelper.close();
            return thanhVien;
        }
        cursor.close();
        dpHelper.close();
        return thanhVien;
    }
}