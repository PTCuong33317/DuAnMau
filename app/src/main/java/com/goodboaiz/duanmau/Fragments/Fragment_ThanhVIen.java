package com.goodboaiz.duanmau.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.goodboaiz.duanmau.DAO.ThanhVienDAO;
import com.goodboaiz.duanmau.R;
import com.goodboaiz.duanmau.adapter.ThanhVien_adapter;
import com.goodboaiz.duanmau.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.List;


public class Fragment_ThanhVIen extends Fragment {

    private ThanhVienDAO thanhVienDAO;
    private List<ThanhVien> thanhVienList;
    private ThanhVien_adapter thanhVienAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment__thanh_v_ien, container, false);
        thanhVienDAO = new ThanhVienDAO(getContext());
        thanhVienList = thanhVienDAO.selectAllThanhVien();
        thanhVienAdapter = new ThanhVien_adapter(getContext(), thanhVienList, thanhVienDAO);
        RecyclerView recyclerThanhVien = view.findViewById(R.id.rcv_thanhvien);
        FloatingActionButton floating_addThanhVien = view.findViewById(R.id.floating_thanhvien);


        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerThanhVien.setLayoutManager(manager);
        recyclerThanhVien.setAdapter(thanhVienAdapter);

        floating_addThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogAddThanhVien();
            }
        });


        return view;
    }


    private void openDialogAddThanhVien() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.item_add_thanh_vien, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText edtAddHoTenThanhVien = view.findViewById(R.id.edtAddHoTenThanhVien);
        EditText edtAddNamSinh = view.findViewById(R.id.edtAddNamSinh);

        TextView txtAddThanhVienSubmit = view.findViewById(R.id.thanhvien_add_thanhvien);
        TextView txtCancelAddThanhVien = view.findViewById(R.id.thanhvien_cancel_thanhvien);
        TextView txtCMND = view.findViewById(R.id.edtAddCMND);
        txtAddThanhVienSubmit.setOnClickListener(new View.OnClickListener() {
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
                String CMND = txtCMND.getText().toString();

                Calendar date = Calendar.getInstance();
                int year = date.get(Calendar.YEAR);
                if ((year - namSinh) < 13) {
                    Toast.makeText(getContext(), "Thành viên phải từ 13 tuổi trở lên", Toast.LENGTH_SHORT).show();
                    return;
                }

                ThanhVien thanhVien = new ThanhVien(hoTenThanhVien, namSinh, CMND);

                if (thanhVienDAO.addThanhVien(thanhVien)) {
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    thanhVienList.clear();
                    thanhVienList.addAll(thanhVienDAO.selectAllThanhVien());
                    thanhVienAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });

        txtCancelAddThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}