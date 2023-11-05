package com.goodboaiz.duanmau.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.goodboaiz.duanmau.DAO.ThuThuDAO;
import com.goodboaiz.duanmau.R;
import com.goodboaiz.duanmau.adapter.thuthu_adapter;
import com.goodboaiz.duanmau.model.ThuThu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class Fragment_ThuThu extends Fragment {

    private ThuThuDAO thuThuDAO;
    private ArrayList<ThuThu> thuThuList;
    private thuthu_adapter thuThuAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__thu_thu, container, false);
        thuThuDAO = new ThuThuDAO(getContext());
        thuThuList = new ArrayList<>();
        List<ThuThu> list = thuThuDAO.selectAllThuThu();
        for (ThuThu thuThu : list) {
            thuThuList.add(thuThu);
        }
        thuThuAdapter = new thuthu_adapter(getContext(), thuThuList, thuThuDAO);
        RecyclerView recyclerThuThu = view.findViewById(R.id.rcv_thuthu);
        FloatingActionButton floatBtnAddThuThu = view.findViewById(R.id.floating_thuthu);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerThuThu.setLayoutManager(manager);
        recyclerThuThu.setAdapter(thuThuAdapter);

        floatBtnAddThuThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogAddThuThu();
            }
        });


        return view;
    }

    private void openDialogAddThuThu() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.item_add_thuthu, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText edtAddHoThuThu = view.findViewById(R.id.edtAddHoThuThu);
        EditText edtAddTenDangNhap = view.findViewById(R.id.edtAddTenDangNhap);
        EditText edtAddMatKhau = view.findViewById(R.id.edtAddMatKhau);
        TextView txtTrangThai = view.findViewById(R.id.edtTrangThaiHoatDong);
        Button btn_submit = view.findViewById(R.id.thuthu_add_submit);
        Button btn_cancel = view.findViewById(R.id.thuthu_add_cancel);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtAddHoThuThu.getText().toString().isEmpty() | edtAddHoThuThu.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Vui lòng nhập họ thủ thư", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtAddTenDangNhap.getText().toString().isEmpty() | edtAddTenDangNhap.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Vui lòng nhập tên đăng nhập", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtAddMatKhau.getText().toString().trim().isEmpty() | edtAddMatKhau.getText().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtAddMatKhau.getText().toString().trim().length() < 8) {
                    Toast.makeText(getContext(), "Mật khẩu quá ngắn", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (txtTrangThai.getText().toString().trim().isEmpty()){
                    try {
                        if (Integer.valueOf(txtTrangThai.getText().toString()) > 1 || Integer.valueOf(txtTrangThai.getText().toString()) < 0){
                            Toast.makeText(getContext(), "trạng thái phải là số 0 hoặc 1 !", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        Toast.makeText(getContext(), "Lỗi, vui lòng kiểm tra số liệu nhập vào !", Toast.LENGTH_SHORT).show();
                    }

                }


                String hoThuThu = edtAddHoThuThu.getText().toString();
                String tenDangNhap = edtAddTenDangNhap.getText().toString();
                String matKhau = edtAddMatKhau.getText().toString().trim();
                int trangThai = Integer.valueOf(txtTrangThai.getText().toString().trim());

                ThuThu thuThu = new ThuThu( hoThuThu, matKhau, tenDangNhap, trangThai);

                if (thuThuDAO.addThuThu(thuThu)) {
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    thuThuList.clear();
                    thuThuList.addAll(thuThuDAO.selectAllThuThu());
                    thuThuAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

}