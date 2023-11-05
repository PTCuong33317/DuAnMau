package com.goodboaiz.duanmau.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.goodboaiz.duanmau.DAO.LoaiSachDAO;
import com.goodboaiz.duanmau.R;
import com.goodboaiz.duanmau.model.LoaiSach;

import java.util.ArrayList;

public class LoaiSach_adapter extends RecyclerView.Adapter<LoaiSach_adapter.ViewHolder>{

    private final Context context;
    private ArrayList<LoaiSach> loaiSachArrayList;
    private final LoaiSachDAO loaiSachDAO;

    public LoaiSach_adapter(Context context, ArrayList<LoaiSach> loaiSachArrayList, LoaiSachDAO loaiSachDAO) {
        this.context = context;
        this.loaiSachArrayList = loaiSachArrayList;
        this.loaiSachDAO = loaiSachDAO;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.loaisach_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LoaiSach loaiSach = loaiSachArrayList.get(position);
        holder.txt_maLoai.setText("Mã loại : " + loaiSach.getMaLoaiSach());
        holder.txt_tenloai.setText("Tên loại sách : " + loaiSach.getTenLoaiSach());

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cảnh báo");
                builder.setMessage("Sau khi xóa không thể khôi phục! Tiếp tục?");

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (loaiSachDAO.deleteLoaiSach(loaiSach)) {
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            loaiSachArrayList.clear();
                            loaiSachArrayList.addAll(loaiSachDAO.selectAllLoaiSach());
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

        holder.btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(loaiSach);
            }
        });

    }

    @Override
    public int getItemCount() {
        return loaiSachArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txt_maLoai, txt_tenloai;
        TextView btn_delete,btn_update;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_maLoai = itemView.findViewById(R.id.loaisach_maloai);
            txt_tenloai = itemView.findViewById(R.id.loaisach_tenloai);
            btn_delete = itemView.findViewById(R.id.txt_delete_loaisach);
            btn_update = itemView.findViewById(R.id.txt_update_loaisach);
        }
    }

    private void openDialog(LoaiSach loaiSach){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_update_loaisach, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextView txtUpdateMaLoaiSach = view.findViewById(R.id.loaisach_txt_maLoai);
        EditText edtUpdateTenLoaiSach = view.findViewById(R.id.loaisach_txt_update_tenloaisach);
        TextView txtUpdateLoaiSachSub = view.findViewById(R.id.loaisach_btn_update);
        TextView txtCancelUpdateLoaiSach = view.findViewById(R.id.loaisach_btn_cancel);

        txtUpdateMaLoaiSach.setText("Mã loại sách: " + loaiSach.getMaLoaiSach());
        edtUpdateTenLoaiSach.setText(loaiSach.getTenLoaiSach());

        txtUpdateLoaiSachSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtUpdateTenLoaiSach.getText().toString().isEmpty() | edtUpdateTenLoaiSach.getText().toString().equals("")) {
                    Toast.makeText(context, "Vui lòng nhập tên loại sách", Toast.LENGTH_SHORT).show();
                    return;
                }
                loaiSach.setTenLoaiSach(edtUpdateTenLoaiSach.getText().toString());
                if (loaiSachDAO.updateLoaiSach(loaiSach)) {
                    Toast.makeText(context, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                    loaiSachArrayList.clear();
                    loaiSachArrayList.addAll(loaiSachDAO.selectAllLoaiSach());
                    dialog.dismiss();
                    notifyDataSetChanged();
                }
            }
        });

        txtCancelUpdateLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
