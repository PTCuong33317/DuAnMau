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

import com.goodboaiz.duanmau.DAO.LoaiSachDAO;
import com.goodboaiz.duanmau.R;
import com.goodboaiz.duanmau.adapter.LoaiSach_adapter;
import com.goodboaiz.duanmau.model.LoaiSach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class Fragment_LoaiSach extends Fragment {
    private LoaiSachDAO loaiSachDAO;
    private ArrayList<LoaiSach> loaiSachArrayList;

    private LoaiSach_adapter loaiSachAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__loai_sach, container, false);
        loaiSachDAO = new LoaiSachDAO(getContext());
        loaiSachArrayList = loaiSachDAO.selectAllLoaiSach();
        loaiSachAdapter = new LoaiSach_adapter(getContext(), loaiSachArrayList, loaiSachDAO);
        RecyclerView recyclerLoaiSach = view.findViewById(R.id.rcv_loaisach);
        FloatingActionButton floating_add_loaiSach = view.findViewById(R.id.floating_loaisach);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerLoaiSach.setLayoutManager(manager);
        recyclerLoaiSach.setAdapter(loaiSachAdapter);

        floating_add_loaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        return view;
    }

    private void openDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.item_add_loaisach, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText edtAddTenLoaiSach = view.findViewById(R.id.loaisach_add_txt_tenloaisach);
        TextView txt_add_loaiSach_submit = view.findViewById(R.id.loaisach_btn_add_add);
        TextView txt_cancel_add_loaiSach = view.findViewById(R.id.loaisach_btn_add_cancel);

        txt_add_loaiSach_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtAddTenLoaiSach.getText().toString().isEmpty() | edtAddTenLoaiSach.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Vui lòng nhập tên loại sách", Toast.LENGTH_SHORT).show();
                    return;
                }
                String tenLoaiSach = edtAddTenLoaiSach.getText().toString();
                LoaiSach loaiSach = new LoaiSach(tenLoaiSach);

                if (loaiSachDAO.addLoaiSach(loaiSach)) {
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    loaiSachArrayList.clear();
                    loaiSachArrayList.addAll(loaiSachDAO.selectAllLoaiSach());
                    dialog.dismiss();
                    loaiSachAdapter.notifyDataSetChanged();
                }
            }
        });

        txt_cancel_add_loaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}