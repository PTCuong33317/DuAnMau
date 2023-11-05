package com.goodboaiz.duanmau.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.goodboaiz.duanmau.DAO.LoaiSachDAO;
import com.goodboaiz.duanmau.DAO.SachDAO;
import com.goodboaiz.duanmau.R;
import com.goodboaiz.duanmau.model.LoaiSach;
import com.goodboaiz.duanmau.model.Sach;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Sach_adapter extends RecyclerView.Adapter<Sach_adapter.ViewHolder>{
    private final Context context;

    private ArrayList<Sach> sachArrayList;

    private final SachDAO sachDAO;
    private int maLoaiSach;


    public Sach_adapter(Context context, ArrayList<Sach> sachArrayList, SachDAO sachDAO) {
        this.context = context;
        this.sachArrayList = sachArrayList;
        this.sachDAO = sachDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.sach_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Sach sach = sachArrayList.get(position);

        holder.txt_masach.setText("Mã sách : " + sach.getMaSach());
        holder.txt_tensach.setText("Tên sách : " + sach.getTenSach());
        holder.txt_loaisach.setText("Loại sách : " + sach.getMaLoai());
        holder.txt_giathue.setText("Giá thuê : " + sach.getGiaThue());

        holder.txt_sachdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cảnh báo");
                builder.setMessage("Sau khi xóa không thể khôi phục! Tiếp tục?");

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (sachDAO.deleteSach(sach)) {
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            sachArrayList.clear();
                            sachArrayList.addAll(sachDAO.selectAllSach());
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "Lỗi xóa", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        holder.txt_sachedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogUpdateSach(sach);
            }
        });

    }

    @Override
    public int getItemCount() {
        return sachArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txt_masach, txt_tensach,txt_giathue, txt_loaisach,txt_sachedit,txt_sachdelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_masach = itemView.findViewById(R.id.sach_txt_masach);
            txt_tensach = itemView.findViewById(R.id.sach_txt_tensach);
            txt_giathue = itemView.findViewById(R.id.sach_txt_giaThue);
            txt_loaisach = itemView.findViewById(R.id.sach_txt_loaisach);
            txt_sachedit = itemView.findViewById(R.id.sach_edit);
            txt_sachdelete = itemView.findViewById(R.id.sach_delete);
        }
    }

    public void openDialogUpdateSach(Sach sach){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_update_sach, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextView txtUpdateMaSach = view.findViewById(R.id.sach_update_txt_masach);
        Spinner spinnerUpdateLoaiSach = view.findViewById(R.id.spinner_item_sach);
        EditText edtUpdateTenSach = view.findViewById(R.id.sach_update_txt_tensach);
        EditText edtUpdateGiaMuon = view.findViewById(R.id.sach_update_txt_giathue);
        Button txtUpdateSachSub = view.findViewById(R.id.sach_btn_update);
        Button txtCancelUpdateSach = view.findViewById(R.id.sach_btn_cancel_update);

        txtUpdateMaSach.setText("Mã sách: " + sach.getMaSach());
        setDataSpinnerLoaiSach(spinnerUpdateLoaiSach, sach);
        edtUpdateTenSach.setText(sach.getTenSach());
        edtUpdateGiaMuon.setText(String.valueOf(sach.getGiaThue()));

        txtUpdateSachSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtUpdateTenSach.getText().toString().isEmpty() | edtUpdateTenSach.getText().toString().equals("")) {
                    Toast.makeText(context, "Vui lòng nhập tên sách", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtUpdateGiaMuon.getText().toString().isEmpty() | edtUpdateGiaMuon.getText().toString().equals("")) {
                    Toast.makeText(context, "Vui lòng nhập giá mượn sách", Toast.LENGTH_SHORT).show();
                    return;
                }
                sach.setTenSach(edtUpdateTenSach.getText().toString());
                sach.setGiaThue(Integer.parseInt(edtUpdateGiaMuon.getText().toString().trim()));
                sach.setMaLoai(maLoaiSach);
                if (sachDAO.updateSach(sach)) {
                    Toast.makeText(context, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                    sachArrayList.clear();
                    sachArrayList.addAll(sachDAO.selectAllSach());
                    dialog.dismiss();
                    notifyDataSetChanged();
                }
            }
        });

        txtCancelUpdateSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void setDataSpinnerLoaiSach(Spinner spinnerLoaiSach, Sach sach) {
        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
        List<LoaiSach> loaiSachList = loaiSachDAO.selectAllLoaiSach();
        List<HashMap<String, Object>> hashMapList = new ArrayList<>();
        for (LoaiSach loaiSach : loaiSachList) {
            if (loaiSach.getMaLoaiSach() == sach.getMaLoai()) {
                loaiSachList.remove(loaiSach);
                loaiSachList.add(0, loaiSach);
                break;
            }
        }
        for (LoaiSach loaiSach : loaiSachList) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("maLoaiSach", loaiSach.getMaLoaiSach());
            hashMap.put("tenLoaiSach", loaiSach.getTenLoaiSach());
            hashMapList.add(hashMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context,
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
