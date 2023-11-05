package com.goodboaiz.duanmau.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.goodboaiz.duanmau.DAO.ThanhVienDAO;
import com.goodboaiz.duanmau.R;
import com.goodboaiz.duanmau.adapter.ThanhVien_adapter;
import com.goodboaiz.duanmau.model.ThanhVien;

import java.util.ArrayList;
import java.util.Calendar;


public class Fragment_themNguoiDung extends Fragment {

    private ThanhVienDAO thanhVienDAO;
    private ArrayList<ThanhVien> thanhVienList;
    private ThanhVien_adapter thanhVienAdapter;



    // Trong Fragment_themNguoiDung
    public void openNewFragment() {
        // Tạo một Fragment mới (đối với ví dụ này, chúng ta gọi nó là Fragment_khac)
        Fragment_ThanhVIen fragmentThanhVIen = new Fragment_ThanhVIen();

        // Lấy FragmentManager
        FragmentManager fragmentManager = getFragmentManager();

        // Bắt đầu một FragmentTransaction
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Thêm Fragment mới vào (hoặc có thể sử dụng replace() để thay thế Fragment hiện tại)
        transaction.add(R.id.frg_addTV, fragmentThanhVIen);

        // Commit để hiển thị Fragment mới
        transaction.commit();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        thanhVienDAO = new ThanhVienDAO(getContext());
        thanhVienList = thanhVienDAO.selectAllThanhVien();
        thanhVienAdapter = new ThanhVien_adapter(getContext(), thanhVienList, thanhVienDAO);

        View view = inflater.inflate(R.layout.fragment_them_nguoi_dung, container, false);
        EditText edtAddHoTenThanhVien = view.findViewById(R.id.TTV_edtAddHoTenThanhVien);
        EditText edtAddNamSinh = view.findViewById(R.id.TTV_edtAddNamSinh);
        EditText edtaddCmnd = view.findViewById(R.id.TTV_edtAddCMND);
        Button btn_add_ThanhVien_Submit = view.findViewById(R.id.themthanhvien_add_thanhvien);
        Button txt_cancel_thanhVien = view.findViewById(R.id.themthanhvien_cancel_thanhvien);


        btn_add_ThanhVien_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtAddHoTenThanhVien.getText().toString().isEmpty() | edtAddHoTenThanhVien.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Vui lòng nhập họ và tên thành viên", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtAddNamSinh.getText().toString().trim().isEmpty() | edtAddNamSinh.getText().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "Vui lòng nhập năm sinh", Toast.LENGTH_SHORT).show();
                    return;
                }

                String hoTenThanhVien = edtAddHoTenThanhVien.getText().toString();
                int namSinh = Integer.parseInt(edtAddNamSinh.getText().toString());
                String Cmnd = edtaddCmnd.getText().toString();

                Calendar date = Calendar.getInstance();
                int year = date.get(Calendar.YEAR);
                if ((year - namSinh) < 13) {
                    Toast.makeText(getContext(), "Thành viên phải từ 13 tuổi trở lên", Toast.LENGTH_SHORT).show();
                    return;
                }

                ThanhVien thanhVien = new ThanhVien(hoTenThanhVien, namSinh, Cmnd);

                if (thanhVienDAO.addThanhVien(thanhVien)) {
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    thanhVienList.clear();
                    thanhVienList.addAll(thanhVienDAO.selectAllThanhVien());
                    thanhVienAdapter.notifyDataSetChanged();
                    openNewFragment();
                }
            }
        });

        txt_cancel_thanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtAddHoTenThanhVien.setText("");
                edtAddNamSinh.setText("");
            }
        });

        return view;
    }
}