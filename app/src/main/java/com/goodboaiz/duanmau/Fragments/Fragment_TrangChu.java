package com.goodboaiz.duanmau.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.goodboaiz.duanmau.R;


public class Fragment_TrangChu extends Fragment {


    public Fragment_TrangChu() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__trang_chu, container, false);
    }
}