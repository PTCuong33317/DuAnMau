package com.goodboaiz.duanmau.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.goodboaiz.duanmau.Fragments.Fragment_top10;
import com.goodboaiz.duanmau.R;
import com.goodboaiz.duanmau.model.Top10;

import java.util.ArrayList;

public class Top10_adapter extends ArrayAdapter<Top10> {
    private Context context;
    private Fragment_top10 fragmentTop10;
    private ArrayList<Top10> topList;

    public Top10_adapter(@NonNull Context context,Fragment_top10 fragmentTop10,ArrayList<Top10> list){
        super(context,0,list);
        this.context = context;
        this.topList = list;
        this.fragmentTop10 = fragmentTop10;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_top_10, null);
        }
        final Top10 item = topList.get(position);
        if (item != null) {
            TextView txtTopTenSach = view.findViewById(R.id.txtTopTenSach);
            TextView txtTopSoLuong = view.findViewById(R.id.txtTopSoLuong);
            txtTopTenSach.setText("Sách: " + item.getTenSach());
            txtTopSoLuong.setText("Số lượng: "+ item.getSoLuong());
        }
        return view;
    }
}
