package com.goodboaiz.duanmau.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.goodboaiz.duanmau.DAO.ThuThuDAO;
import com.goodboaiz.duanmau.R;
import com.goodboaiz.duanmau.model.ThuThu;

import java.util.List;

public class thuthu_adapter extends RecyclerView.Adapter<thuthu_adapter.ViewHolder>{

    private final Context context;
    private List<ThuThu> thuThuList;
    private final ThuThuDAO thuThuDAO;

    public thuthu_adapter(Context context, List<ThuThu> thuThuList, ThuThuDAO thuThuDAO) {
        this.context = context;
        this.thuThuList = thuThuList;
        this.thuThuDAO = thuThuDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.thuthu_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThuThu thuThu = thuThuList.get(position);

        holder.txtMaThuThu.setText("Mã thủ thư: " + thuThu.getMaTT());
        holder.txtHoTen.setText("Họ và tên: " + thuThu.getHoTen() );
        holder.txtusername.setText("username : " + thuThu.getUsername());
        holder.txtTrangThai.setText(thuThu.getTrangThaiHoatDong() == 1 ? "Kích hoạt" : "Vô hiệu hóa");

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận");
                builder.setMessage("Thay đổi trạng thái thủ thư! Tiếp tục?");

                builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int status = thuThu.getTrangThaiHoatDong() == 0 ? 1 : 0;
                        thuThu.setTrangThaiHoatDong(status);
                        if (thuThuDAO.updateStatusThuThu(thuThu)) {
                            Toast.makeText(context, "Đã thay đổi", Toast.LENGTH_SHORT).show();
                            thuThuList.clear();
                            thuThuList.addAll(thuThuDAO.selectAllThuThu());
                            dialog.dismiss();
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "Lỗi", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return thuThuList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtMaThuThu, txtusername, txtHoTen,txtTrangThai;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaThuThu = itemView.findViewById(R.id.thuthu_maTT);
            txtusername = itemView.findViewById(R.id.thuthu_username_thuthu);
            txtHoTen = itemView.findViewById(R.id.thuthu_hoten_thuthu);
            txtTrangThai = itemView.findViewById(R.id.thuthu_trangthai_thuthu);
        }
    }
}
