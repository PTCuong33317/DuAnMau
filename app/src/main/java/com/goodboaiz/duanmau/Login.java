package com.goodboaiz.duanmau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.goodboaiz.duanmau.DAO.ThuThuDAO;

public class Login extends AppCompatActivity {
ThuThuDAO ttDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText edt_user = findViewById(R.id.log_username);
        EditText edt_pass = findViewById(R.id.log_pass);
        Button btn_log = findViewById(R.id.btn_dangnhap);
        Button btn_exit = findViewById(R.id.btn_exit);
        CheckBox chk_save = findViewById(R.id.chkSave);

        ttDao = new ThuThuDAO(this);

        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edt_user.getText().toString();
                String pass = edt_pass.getText().toString();
                if(ttDao.checkLogin(user,pass)){
                    Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                    //..lưu sharereferences
                    SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("matt", user);
                    editor.commit();

                    startActivity(new Intent(Login.this, MainActivity.class));
                }
                else Toast.makeText(Login.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}