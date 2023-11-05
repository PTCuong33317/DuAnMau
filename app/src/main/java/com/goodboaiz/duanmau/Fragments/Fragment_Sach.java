package com.goodboaiz.duanmau.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.goodboaiz.duanmau.DAO.LoaiSachDAO;
import com.goodboaiz.duanmau.DAO.SachDAO;
import com.goodboaiz.duanmau.R;
import com.goodboaiz.duanmau.adapter.Sach_adapter;
import com.goodboaiz.duanmau.model.LoaiSach;
import com.goodboaiz.duanmau.model.Sach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;


public class Fragment_Sach extends Fragment {

    private SachDAO sachDAO;

    private ArrayList<Sach> sachArrayList;

    private Sach_adapter sachAdapter;

    private int maLoaiSach;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__sach, container, false);
        sachDAO = new SachDAO(getContext());
        sachArrayList = sachDAO.selectAllSach();
        sachAdapter = new Sach_adapter(getContext(), sachArrayList, sachDAO);
        ArrayList<Sach> sachArrayList1 = sachDAO.selectAllSach();
        RecyclerView recyclerSach = view.findViewById(R.id.rcv_sach);
        FloatingActionButton floating_add_Sach = view.findViewById(R.id.floating_sach);
        SearchView searchView = view.findViewById(R.id.search);
        Button sortButton = view.findViewById(R.id.sort_data);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerSach.setLayoutManager(manager);
        recyclerSach.setAdapter(sachAdapter);

        floating_add_Sach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogAddSach();
            }
        });

//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                sachArrayList.clear();
//                for (Sach sach : sachArrayList1) {
//                    if (sach.getTenSach().toLowerCase().contains(newText.toLowerCase())){
//                        sachArrayList.add(sach);
//                    }
//                }
//                sachAdapter.notifyDataSetChanged();
//                return false;
//            }
//        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                sachArrayList.clear();
                for (Sach sach : sachArrayList1){
                    if (String.valueOf(sach.getGiaThue()).contains(newText)){
                        sachArrayList.add(sach);
                    }
                }
                sachAdapter.notifyDataSetChanged();
                return false;
            }
        });

        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogSort();
            }
        });

        return view;
    }

    private void openDialogSort() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.sort_option_sach,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        Button sortNormal = view.findViewById(R.id.sort_normal);
        Button sortReverse = view.findViewById(R.id.sort_reverse);

        sortNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sachArrayList.sort(Comparator.comparing(Sach::getGiaThue));
                Toast.makeText(getContext(), "DONE", Toast.LENGTH_SHORT).show();
                sachAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        sortReverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sachArrayList.sort(Comparator.comparing(Sach::getGiaThue, Comparator.reverseOrder()));
                Toast.makeText(getContext(), "DONE", Toast.LENGTH_SHORT).show();
                sachAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
    }
    private void openDialogAddSach() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.item_add_sach, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        Spinner spinnerAddLoaiSach = view.findViewById(R.id.spinner_add_sach);
        EditText edtAddTenSach = view.findViewById(R.id.sach_txt_add_tensach);
        EditText edtAddGiaMuon = view.findViewById(R.id.sach_txt_add_giathue);
        TextView txt_add_sach_submit = view.findViewById(R.id.sach_btn_add_add);
        TextView txtCancelAddSach = view.findViewById(R.id.sach_btn_cancel_add);

        setDataSpinnerLoaiSach(spinnerAddLoaiSach);

        txt_add_sach_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtAddTenSach.getText().toString().isEmpty() | edtAddTenSach.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Vui lòng nhập tên sách", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtAddGiaMuon.getText().toString().isEmpty() | edtAddGiaMuon.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Vui lòng nhập giá mượn sách", Toast.LENGTH_SHORT).show();
                    return;
                }

                String tenSach = edtAddTenSach.getText().toString();
                int giaMuon = Integer.parseInt(edtAddGiaMuon.getText().toString().trim());

                Sach sach = new Sach(tenSach, giaMuon, maLoaiSach);

                if (sachDAO.addSach(sach)) {
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    sachArrayList.clear();
                    sachArrayList.addAll(sachDAO.selectAllSach());
                    sachAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });

        txtCancelAddSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void setDataSpinnerLoaiSach(Spinner spinnerLoaiSach) {
        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(getContext());
        List<LoaiSach> loaiSachList = loaiSachDAO.selectAllLoaiSach();
        List<HashMap<String, Object>> hashMapList = new ArrayList<>();
        for (LoaiSach loaiSach : loaiSachList) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("maLoaiSach", loaiSach.getMaLoaiSach());
            hashMap.put("tenLoaiSach", loaiSach.getTenLoaiSach());
            hashMapList.add(hashMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                hashMapList,
                android.R.layout.simple_list_item_1,
                new String[]{"tenLoaiSach"},
                new int[]{android.R.id.text1}
        );
        spinnerLoaiSach.setAdapter(simpleAdapter);

        spinnerLoaiSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> hashMap = hashMapList.get(position);
                maLoaiSach = (int) hashMap.get("maLoaiSach");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}