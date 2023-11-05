package com.goodboaiz.duanmau.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.goodboaiz.duanmau.DAO.ThongKeDAO;
import com.goodboaiz.duanmau.R;
import com.goodboaiz.duanmau.adapter.Top10_adapter;
import com.goodboaiz.duanmau.model.Top10;

import java.util.ArrayList;


public class Fragment_top10 extends Fragment {

    private ListView listViewTop;
    private ArrayList<Top10> top10List;
    private Top10_adapter top10Adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top10, container, false);
        listViewTop = view.findViewById(R.id.listViewTop10);
        ThongKeDAO thongKeDAO = new ThongKeDAO(getContext());
        top10List = thongKeDAO.getTop();
        top10Adapter = new Top10_adapter(getContext(), this, top10List);
        listViewTop.setAdapter((ListAdapter) top10Adapter);
        return view;
    }
}