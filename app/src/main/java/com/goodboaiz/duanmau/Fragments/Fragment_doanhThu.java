package com.goodboaiz.duanmau.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.goodboaiz.duanmau.DAO.ThongKeDAO;
import com.goodboaiz.duanmau.R;

import java.util.Calendar;

public class Fragment_doanhThu extends Fragment {


    public Fragment_doanhThu() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doanh_thu, container, false);
        EditText edtTuNgay = view.findViewById(R.id.edtTuNgay);
        EditText edtDenNgay = view.findViewById(R.id.edtDenNgay);
        TextView txtTinh = view.findViewById(R.id.txtTinh);
        TextView txtDoanhThu = view.findViewById(R.id.txtDoanhThu);

        edtTuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar date = Calendar.getInstance();
                int year = date.get(Calendar.YEAR);
                int month = date.get(Calendar.MONTH) + 1;
                int day = date.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edtTuNgay.setText(String.format("%d-%d-%d", dayOfMonth, month, year));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        edtDenNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar date = Calendar.getInstance();
                int year = date.get(Calendar.YEAR);
                int month = date.get(Calendar.MONTH) + 1;
                int day = date.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edtDenNgay.setText(String.format("%d-%d-%d", dayOfMonth, month, year));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        txtTinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtTuNgay.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng chọn mốc thời gian đầu", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (edtDenNgay.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng chọn mốc thời gian cuối", Toast.LENGTH_SHORT).show();
                    return;
                }

                String tuNgay = edtTuNgay.getText().toString();
                String denNgay = edtDenNgay.getText().toString();
                ThongKeDAO thongKeDAO = new ThongKeDAO(getContext());
                txtDoanhThu.setText("Doanh thu: " + thongKeDAO.getDoanhThu(tuNgay, denNgay) + " VNĐ");
            }
        });


        return view;
    }
}