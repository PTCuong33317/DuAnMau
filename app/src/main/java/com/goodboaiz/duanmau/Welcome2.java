package com.goodboaiz.duanmau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Welcome2 extends AppCompatActivity {
    EditText edtchao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome2);

        edtchao = findViewById(R.id.msv2);
//        String masv = edtchao.getText().toString();
        Button xacnhan = findViewById(R.id.btn_check2);
        xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtchao.getText().toString().equals("ph33317")){
                    Intent intent = new Intent(Welcome2.this, Login.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(Welcome2.this, "Sai msv nhap dung msv de dang nhap", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}