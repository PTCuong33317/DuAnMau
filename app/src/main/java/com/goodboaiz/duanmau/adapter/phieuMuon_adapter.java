package com.goodboaiz.duanmau.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.goodboaiz.duanmau.DAO.PhieuMuonDAO;
import com.goodboaiz.duanmau.DAO.SachDAO;
import com.goodboaiz.duanmau.DAO.ThanhVienDAO;
import com.goodboaiz.duanmau.DAO.ThuThuDAO;
import com.goodboaiz.duanmau.R;
import com.goodboaiz.duanmau.model.PhieuMuon;
import com.goodboaiz.duanmau.model.Sach;
import com.goodboaiz.duanmau.model.ThanhVien;
import com.goodboaiz.duanmau.model.ThuThu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class phieuMuon_adapter extends RecyclerView.Adapter<phieuMuon_adapter.ViewHolder>{
    private final Context context;
    private ArrayList<PhieuMuon> phieuMuonList;
    private final PhieuMuonDAO phieuMuonDAO;

    private int maSach;
    private int maThanhVien;

    private int maTT;

    private String UsernameTT;

    public phieuMuon_adapter(Context context, ArrayList<PhieuMuon> phieuMuonList, PhieuMuonDAO phieuMuonDAO) {
        this.context = context;
        this.phieuMuonList = phieuMuonList;
        this.phieuMuonDAO = phieuMuonDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.phieumuon_layout, parent, false);
        return new ViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PhieuMuon phieuMuon = phieuMuonList.get(position);
        SachDAO sachDAO = new SachDAO(context);

        holder.txtMaPhieuMuon.setText("Mã phiếu mượn: " + phieuMuon.getMaPhieuMuon() );
        holder.txtTrangThaiPhieuMuon.setText(phieuMuon.getTrangThaiPhieuMuon() == 0 ? "(Chưa trả sách)" : "(Đã trả sách)");
        holder.txtTenThanhVien_PhieuMuon.setText("Người mượn: "
                + phieuMuonDAO.getHoTenThanhVien(phieuMuon.getMaTV()));
        holder.txtTenSach_PhieuMuon.setText("Tên sách: "
                + phieuMuonDAO.getTenSach(phieuMuon.getMaSach()));
        holder.txtNgayMuon.setText("Ngày mượn: " + phieuMuon.getNgayMuon());
        holder.txtGiaMuon_PhieuMuon.setText("Giá mượn: " + phieuMuon.getGiaMuon());
        //String TTTP = phieuMuon.getMaTT() == null ? String.valueOf(phieuMuon.getMaTV()) : phieuMuon.getMaTT();
        holder.txtTenThuThu_PhieuMuon.setText("Thủ thư tạo phiếu: " + phieuMuon.getMaTT());

        int giaThue = phieuMuon.getGiaMuon(),trangThai = phieuMuon.getTrangThaiPhieuMuon();
        int Color = ( giaThue > 30000 ) ? R.color.red : R.color.green;
        int colorTT = ( trangThai == 1 ) ? R.color.green : R.color.red;
        holder.txtGiaMuon_PhieuMuon.setTextColor(ContextCompat.getColor(context,Color));
        holder.txtTrangThaiPhieuMuon.setTextColor(ContextCompat.getColor(context,colorTT));

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                View view = inflater.inflate(R.layout.item_update_phieumuon, null);
                builder.setView(view);
                Dialog dialog = builder.create();
                dialog.show();

                TextView txtUpdateMaPhieuMuon = view.findViewById(R.id.txtUpdateMaPhieuMuon);
                TextView txtUpdateHoTenThuThu = view.findViewById(R.id.txtUpdateHoTenThuThu);
                EditText edtUpdateNgayMuon = view.findViewById(R.id.edtUpdateNgayMuon);
                Spinner spinnerUpdateThanhVien = view.findViewById(R.id.spinnerUpdateThanhVien);
                Spinner spinnerUpdateSach = view.findViewById(R.id.spinnerUpdateSach);
                Spinner spinnerUpdateThuThu = view.findViewById(R.id.spinnerUpdateThuThu);
                TextView txtChuaTraSach = view.findViewById(R.id.txtChuaTraSach);
                TextView txtDaTraSach = view.findViewById(R.id.txtDaTraSach);
                TextView txtUpdatePhieuMuonSub = view.findViewById(R.id.phieumuon_update_phieumuon);
                TextView txtCancelUpdatePhieuMuon = view.findViewById(R.id.phieumuon_update_cancel);

                txtUpdateMaPhieuMuon.setText("Mã phiếu: " + phieuMuon.getMaPhieuMuon());
                txtUpdateHoTenThuThu.setText("Thủ thư tạo phiếu: " + phieuMuon.getMaTT() );

                edtUpdateNgayMuon.setText(phieuMuon.getNgayMuon());
                setDataSpinnerThanhVien(spinnerUpdateThanhVien, phieuMuon);
                setDataSpinnerSach(spinnerUpdateSach, phieuMuon);
                setDataSpinnerThuThu(spinnerUpdateThuThu,phieuMuon);

                if (phieuMuon.getTrangThaiPhieuMuon() == 0) {
                    txtDaTraSach.setVisibility(View.GONE);
                } else if (phieuMuon.getTrangThaiPhieuMuon() == 1) {
                    txtChuaTraSach.setVisibility(View.GONE);
                }

                edtUpdateNgayMuon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar date = Calendar.getInstance();
                        int year = date.get(Calendar.YEAR);
                        int month = date.get(Calendar.MONTH) + 1;
                        int day = date.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                edtUpdateNgayMuon.setText(String.format("%d-%d-%d", dayOfMonth, month, year));
                            }
                        }, year, month, day);
                        datePickerDialog.show();
                    }
                });

                txtChuaTraSach.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txtChuaTraSach.setVisibility(View.GONE);
                        txtDaTraSach.setVisibility(View.VISIBLE);
                        phieuMuon.setTrangThaiPhieuMuon(1);
                    }
                });

                txtDaTraSach.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txtDaTraSach.setVisibility(View.GONE);
                        txtChuaTraSach.setVisibility(View.VISIBLE);
                        phieuMuon.setTrangThaiPhieuMuon(0);
                    }
                });


                txtUpdatePhieuMuonSub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        phieuMuon.setNgayMuon(edtUpdateNgayMuon.getText().toString());
                        phieuMuon.setMaTV(maThanhVien);
                        phieuMuon.setMaSach(maSach);
                        phieuMuon.setMaTT(String.valueOf(UsernameTT));
                        phieuMuon.setGiaMuon(sachDAO.getGiaMuon(maSach));

                        if (phieuMuonDAO.updatePhieuMuon(phieuMuon)) {
                            Toast.makeText(context, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                            phieuMuonList.clear();
                            phieuMuonList.addAll(phieuMuonDAO.selectAllPhieuMuon());
                            dialog.dismiss();
                            notifyDataSetChanged();
                        }
                    }
                });

                txtCancelUpdatePhieuMuon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                return true;
            }
        });

    }

    private void setDataSpinnerSach(Spinner spinnerSach, PhieuMuon phieuMuon) {
        SachDAO sachDAO = new SachDAO(context);
        List<Sach> sachList = sachDAO.selectAllSach();
        List<HashMap<String, Object>> hashMapList = new ArrayList<>();
        for (Sach sach : sachList) {
            if (sach.getMaSach() == phieuMuon.getMaSach()) {
                sachList.remove(sach);
                sachList.add(0, sach);
                break;
            }
        }
        for (Sach sach : sachList) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("maSach", sach.getMaSach());
            hashMap.put("tenSach", sach.getTenSach());
            hashMapList.add(hashMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context,
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
    private void setDataSpinnerThanhVien(Spinner spinnerThanhVien, PhieuMuon phieuMuon) {
        ThanhVienDAO thanhVienDAO = new ThanhVienDAO(context);
        List<ThanhVien> thanhVienList = thanhVienDAO.selectAllThanhVien();
        List<HashMap<String, Object>> hashMapList = new ArrayList<>();
        for (ThanhVien thanhVien : thanhVienList) {
            if (thanhVien.getMaTV() == phieuMuon.getMaTV()) {
                thanhVienList.remove(thanhVien);
                thanhVienList.add(0, thanhVien);
                break;
            }
        }
        for (ThanhVien thanhVien : thanhVienList) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("maThanhVien", thanhVien.getMaTV());
            hashMap.put("hoTenThanhVien", thanhVien.getHoVaTen());
            hashMapList.add(hashMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context,
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

    private void setDataSpinnerThuThu(Spinner spinnerThuThu, PhieuMuon phieuMuon) {
        ThuThuDAO thuThuDAO = new ThuThuDAO(context);
        ArrayList<ThuThu> thuThuArrayList = thuThuDAO.selectAllThuThu();
        ArrayList<HashMap<String, Object>> hashMapList = new ArrayList<>();
        for (ThuThu thuThu : thuThuArrayList) {
            if (thuThu.getTrangThaiHoatDong() ==1){
                if (thuThu.getHoTen().equals(thuThu.getMaTT())) {
                    thuThuArrayList.remove(thuThu);
                    thuThuArrayList.add(0, thuThu);
                    break;
                }
            }

        }
        for (ThuThu thuThu : thuThuArrayList) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("maTT", thuThu.getMaTT());
            hashMap.put("hoTen", thuThu.getHoTen());
            hashMapList.add(hashMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context,
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
                UsernameTT = (String) hashMap.get("hoTen");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return phieuMuonList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtMaPhieuMuon, txtTenThanhVien_PhieuMuon, txtTenSach_PhieuMuon, txtNgayMuon,
                txtGiaMuon_PhieuMuon, txtTenThuThu_PhieuMuon, txtTrangThaiPhieuMuon;
        Button btn_phieuMuon_update, btn_phieuMuon_cancel;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaPhieuMuon = itemView.findViewById(R.id.phieumuon_maphieumuon);
            txtTenThanhVien_PhieuMuon = itemView.findViewById(R.id.phieumuon_nguoimuon);
            txtTenSach_PhieuMuon = itemView.findViewById(R.id.phieumuon_tensach);
            txtNgayMuon = itemView.findViewById(R.id.phieumuon_ngaymuon);
            txtGiaMuon_PhieuMuon = itemView.findViewById(R.id.phieumuon_giamuon);
            txtTenThuThu_PhieuMuon = itemView.findViewById(R.id.phieumuon_thuthu);
            txtTrangThaiPhieuMuon = itemView.findViewById(R.id.phieumuon_trangthai);

        }
    }
}
