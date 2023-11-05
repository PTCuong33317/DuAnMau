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

import com.goodboaiz.duanmau.DAO.ThanhVienDAO;
import com.goodboaiz.duanmau.R;
import com.goodboaiz.duanmau.model.ThanhVien;

import java.util.List;

public class ThanhVien_adapter extends RecyclerView.Adapter<ThanhVien_adapter.ViewHolder>{

    private final Context context;
    private List<ThanhVien> thanhVienList;
    private final ThanhVienDAO thanhVienDAO;

    public ThanhVien_adapter(Context context, List<ThanhVien> thanhVienList, ThanhVienDAO thanhVienDAO) {
        this.context = context;
        this.thanhVienList = thanhVienList;
        this.thanhVienDAO = thanhVienDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_thanhvien, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThanhVien thanhVien = thanhVienList.get(position);

        holder.txtMaThanhVien.setText("Mã thành viên: " + thanhVien.getMaTV());
        holder.txtHoTenThanhVien.setText("Họ tên: " + thanhVien.getHoVaTen());
        holder.txtNamSinh.setText("Năm sinh: " + thanhVien.getNamSinh());
        holder.txt_cmnd.setText("CMND: " + thanhVien.getCmnd());

        holder.txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cảnh báo");
                builder.setMessage("Sau khi xóa không thể khôi phục! Tiếp tục?");

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {
                            if (thanhVienDAO.deleteThanhVien(thanhVien)) {
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                thanhVienList.clear();
                                thanhVienList.addAll(thanhVienDAO.selectAllThanhVien());
                                notifyDataSetChanged();
                            } else {
                                Toast.makeText(context, "Lỗi xóa", Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
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

        holder.txtUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogUpdateThanhVien(thanhVien);
            }
        });
    }

    @Override
    public int getItemCount() {
        return thanhVienList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaThanhVien, txtHoTenThanhVien, txtNamSinh, txtUpdate, txt_cancel, txt_cmnd;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaThanhVien = itemView.findViewById(R.id.thanhvien_matv);
            txtHoTenThanhVien = itemView.findViewById(R.id.thanhvien_ten_thanhvien);

            txtNamSinh = itemView.findViewById(R.id.thanhvien_namsinh_thanhvien);
            txtUpdate = itemView.findViewById(R.id.thanhvien_update_thanhvien);
            txt_cancel = itemView.findViewById(R.id.thanhvien_delete_thanhvien);
            txt_cmnd = itemView.findViewById(R.id.thanhvien_cmnd);
        }
    }

    private void openDialogUpdateThanhVien(ThanhVien thanhVien) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_update_thanhvien, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextView txtUpdateMaThanhVien = view.findViewById(R.id.txtUpdateMaThanhVien);
        EditText edtUpdateHoTenThanhVien = view.findViewById(R.id.edtUpdateHoTenThanhVien);
        EditText edtUpdateNamSinh = view.findViewById(R.id.edtUpdateNamSinh);
        EditText edtUdCmnd = view.findViewById(R.id.edtUpdateCMND);
        TextView txtUpdateThanhVienSub = view.findViewById(R.id.thanhvien_update_submit);
        TextView txtCancelUpdateThanhVien = view.findViewById(R.id.thanhvien_cancel_submit);

        txtUpdateMaThanhVien.setText("Mã thành viên: " + thanhVien.getMaTV());
        edtUpdateHoTenThanhVien.setText(thanhVien.getHoVaTen());
        edtUpdateNamSinh.setText(String.valueOf(thanhVien.getNamSinh()));
        edtUdCmnd.setText(thanhVien.getCmnd());


        txtUpdateThanhVienSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtUpdateHoTenThanhVien.getText().toString().isEmpty() | edtUpdateHoTenThanhVien.getText().toString().equals("")) {
                    Toast.makeText(context, "Vui lòng nhập họ tên thành viên", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtUpdateNamSinh.getText().toString().isEmpty() | edtUpdateNamSinh.getText().toString().equals("")) {
                    Toast.makeText(context, "Vui lòng nhập năm sinh", Toast.LENGTH_SHORT).show();
                    return;
                }if (edtUdCmnd.getText().toString().isEmpty() | edtUdCmnd.getText().toString().equals("")){}
                thanhVien.setHoVaTen(edtUpdateHoTenThanhVien.getText().toString());
                thanhVien.setNamSinh(Integer.parseInt(edtUpdateNamSinh.getText().toString()));
                thanhVien.setCmnd(edtUdCmnd.getText().toString());

                if (thanhVienDAO.updateThanhVien(thanhVien)) {
                    Toast.makeText(context, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                    thanhVienList.clear();
                    thanhVienList.addAll(thanhVienDAO.selectAllThanhVien());
                    dialog.dismiss();
                    notifyDataSetChanged();
                }
            }
        });

        txtCancelUpdateThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
