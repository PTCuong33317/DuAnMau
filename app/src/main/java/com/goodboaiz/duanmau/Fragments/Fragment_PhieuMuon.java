package com.goodboaiz.duanmau.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.goodboaiz.duanmau.DAO.PhieuMuonDAO;
import com.goodboaiz.duanmau.DAO.SachDAO;
import com.goodboaiz.duanmau.DAO.ThanhVienDAO;
import com.goodboaiz.duanmau.DAO.ThuThuDAO;
import com.goodboaiz.duanmau.R;
import com.goodboaiz.duanmau.adapter.phieuMuon_adapter;
import com.goodboaiz.duanmau.model.PhieuMuon;
import com.goodboaiz.duanmau.model.Sach;
import com.goodboaiz.duanmau.model.ThanhVien;
import com.goodboaiz.duanmau.model.ThuThu;
import com.goodboaiz.duanmau.DAO.ThanhVienDAO;
import com.goodboaiz.duanmau.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


public class Fragment_PhieuMuon extends Fragment {

    private PhieuMuonDAO phieuMuonDAO;
    private ArrayList<PhieuMuon> phieuMuonList;
    private phieuMuon_adapter phieuMuonAdapter;
    private int maSach;
    private int maThanhVien;

    private int maTT;
    private String maThuThu;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__phieu_muon, container, false);
        phieuMuonDAO = new PhieuMuonDAO(getContext());
        phieuMuonList = phieuMuonDAO.selectAllPhieuMuon();
        phieuMuonAdapter = new phieuMuon_adapter(getContext(), phieuMuonList, phieuMuonDAO);
        RecyclerView recyclerPhieuMuon = view.findViewById(R.id.rcv_phieumuon);
        FloatingActionButton floatBtnAddPhieuMuon = view.findViewById(R.id.floating_phieumuon);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerPhieuMuon.setLayoutManager(manager);
        recyclerPhieuMuon.setAdapter(phieuMuonAdapter);

        SachDAO sachDAO = new SachDAO(getContext());
        floatBtnAddPhieuMuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogUpdatePhieuMuon(sachDAO);
            }
        });

        return view;
    }
    private void openDialogUpdatePhieuMuon(SachDAO sachDAO) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.item_add_phieumuon, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText edtAddNgayMuon = view.findViewById(R.id.edtAddNgayMuon);
        Spinner spinnerAddThanhVien = view.findViewById(R.id.spinnerAddThanhVien);
        Spinner spinnerAddSach = view.findViewById(R.id.spinnerAddSach);
        Spinner spinnerAddTT = view.findViewById(R.id.spinnerAddThuThu);
        TextView txt_addPhieuMuon = view.findViewById(R.id.phieumuon_add_phieumuon);
        TextView txtCancelAddPhieuMuon = view.findViewById(R.id.phieumuon_add_cancel);

        Calendar date = Calendar.getInstance();
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        edtAddNgayMuon.setText(String.format("%d-%d-%d", day, month, year));

        setDataSpinnerThanhVien(spinnerAddThanhVien);
        setDataSpinnerSach(spinnerAddSach);
        setDataSpinnerThuThu(spinnerAddTT);

        txt_addPhieuMuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ngayMuon = edtAddNgayMuon.getText().toString();
                int giaMuon = sachDAO.getGiaMuon(maSach);

                PhieuMuon phieuMuon = new PhieuMuon(0, giaMuon, ngayMuon, maThanhVien, maSach, maThuThu+"");
                if (phieuMuonDAO.addPhieuMuon(phieuMuon)) {
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    phieuMuonList.clear();
                    phieuMuonList.addAll(phieuMuonDAO.selectAllPhieuMuon());
                    dialog.dismiss();
                    phieuMuonAdapter.notifyDataSetChanged();
                }
            }
        });

        txtCancelAddPhieuMuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void setDataSpinnerSach(Spinner spinnerSach) {
        SachDAO sachDAO = new SachDAO(getContext());
        List<Sach> sachList = sachDAO.selectAllSach();
        List<HashMap<String, Object>> hashMapList = new ArrayList<>();
        for (Sach sach : sachList) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("maSach", sach.getMaSach());
            hashMap.put("tenSach", sach.getTenSach());
            hashMapList.add(hashMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                hashMapList,
                android.R.layout.simple_list_item_1,
                new String[]{"tenSach"},
                new int[]{android.R.id.text1}
        );
        spinnerSach.setAdapter(simpleAdapter);

        spinnerSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> hashMap = hashMapList.get(position);
                maSach = (int) hashMap.get("maSach");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setDataSpinnerThanhVien(Spinner spinnerThanhVien) {
        ThanhVienDAO thanhVienDAO = new ThanhVienDAO(getContext());
        List<ThanhVien> thanhVienList = thanhVienDAO.selectAllThanhVien();
        List<HashMap<String, Object>> hashMapList = new ArrayList<>();
        for (ThanhVien thanhVien : thanhVienList) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("maThanhVien", thanhVien.getMaTV());
            hashMap.put("hoTenThanhVien", thanhVien.getHoVaTen());
            hashMapList.add(hashMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                hashMapList,
                android.R.layout.simple_list_item_1,
                new String[]{"hoTenThanhVien"},
                new int[]{android.R.id.text1}
        );
        spinnerThanhVien.setAdapter(simpleAdapter);

        spinnerThanhVien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> hashMap = hashMapList.get(position);
                maThanhVien = (int) hashMap.get("maThanhVien");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void setDataSpinnerThuThu(Spinner spinnerThuThu) {
        ThuThuDAO thuThuDAO = new ThuThuDAO(getContext());
        ArrayList<ThuThu> thuThuArrayList = thuThuDAO.selectAllThuThu();
        ArrayList<HashMap<String, Object>> hashMapList = new ArrayList<>();
        for (ThuThu thuThu : thuThuArrayList) {
            if (thuThu.getTrangThaiHoatDong() == 1){
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("maTT", thuThu.getMaTT());
                hashMap.put("hoTen", thuThu.getHoTen());
                hashMapList.add(hashMap);
            }
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                hashMapList,
                android.R.layout.simple_list_item_1,
                new String[]{"hoTen"},
                new int[]{android.R.id.text1}
        );
        spinnerThuThu.setAdapter(simpleAdapter);

        spinnerThuThu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> hashMap = hashMapList.get(position);
                maTT = (int) hashMap.get("maTT");
                maThuThu = (String) hashMap.get("hoTen");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}