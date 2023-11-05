package com.goodboaiz.duanmau;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.goodboaiz.duanmau.Fragments.Fragment_LoaiSach;
import com.goodboaiz.duanmau.Fragments.Fragment_PhieuMuon;
import com.goodboaiz.duanmau.Fragments.Fragment_Sach;
import com.goodboaiz.duanmau.Fragments.Fragment_ThanhVIen;
import com.goodboaiz.duanmau.Fragments.Fragment_ThuThu;
import com.goodboaiz.duanmau.Fragments.Fragment_doanhThu;
import com.goodboaiz.duanmau.Fragments.Fragment_doiMatkhau;
import com.goodboaiz.duanmau.Fragments.Fragment_themNguoiDung;
import com.goodboaiz.duanmau.Fragments.Fragment_top10;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    RecyclerView recyclerView;

    FloatingActionButton floatingActionButton;
    BottomNavigationView bottomNavigationView;
    private void hideMenuItem(Menu menu, int menuItemId) {
        MenuItem menuItem = menu.findItem(menuItemId);
        if (menuItem != null) {
            menuItem.setVisible(false);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        floatingActionButton = findViewById(R.id.fab_add);
        Fragment_PhieuMuon fragmentPhieuMuon = new Fragment_PhieuMuon();
        replaceFrg(fragmentPhieuMuon);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Trang chủ");
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigtionView);
        Intent intent = getIntent();

        if (intent != null){
            boolean userPermission = intent.getBooleanExtra("userPermission", false);
            boolean trangThai = intent.getBooleanExtra("trangThai",false);
            Menu navMenu = navigationView.getMenu();
            if ( userPermission && trangThai ){
                hideMenuItem(navMenu,R.id.QuanLyThuThu);
                hideMenuItem(navMenu,R.id.ThemNguoiDung);
            }else {
            }
        }
        setSupportActionBar(toolbar);
        toolbar.setTitle("Thư viện Phương Nam");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.QuanLyPhieuMuon) {
                    Fragment_PhieuMuon phieuMuonFragment = new Fragment_PhieuMuon();
                    replaceFrg(phieuMuonFragment);
                } else if (item.getItemId() == R.id.QuanLyThuThu) {
                    Fragment_ThuThu fragment_thuThu = new Fragment_ThuThu();
                    replaceFrg(fragment_thuThu);
                } else if (item.getItemId() == R.id.QuanLyLoaiSach) {
                    Fragment_LoaiSach loaiSachFragment = new Fragment_LoaiSach();
                    replaceFrg(loaiSachFragment);
                } else if (item.getItemId() == R.id.QuanLySach) {
                    Fragment_Sach sachFragment = new Fragment_Sach();
                    replaceFrg(sachFragment);
                } else if (item.getItemId() == R.id.top10) {
                    Fragment_top10 top10Fragment = new Fragment_top10();
                    replaceFrg(top10Fragment);
                } else if (item.getItemId() == R.id.doanhThu) {
                    Fragment_doanhThu doanhThuFragment = new Fragment_doanhThu();
                    replaceFrg(doanhThuFragment);
                } else if (item.getItemId() == R.id.ThemNguoiDung) {
                    Fragment_themNguoiDung addUserFragment = new Fragment_themNguoiDung();
                    replaceFrg(addUserFragment);
                } else if (item.getItemId() == R.id.doiMatKhau) {
                    Fragment_doiMatkhau changePassFragment = new Fragment_doiMatkhau();
                    replaceFrg(changePassFragment);
                } else if (item.getItemId() == R.id.QuanLyThanhVien) {
                    Fragment_ThanhVIen thanhVienFragment = new Fragment_ThanhVIen();
                    replaceFrg(thanhVienFragment);
                }  else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Cảnh báo");
                    builder.setIcon(R.drawable.canhbao);
                    builder.setMessage("Bạn có muốn đăng xuất không?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MainActivity.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, Login.class);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }
                getSupportActionBar().setTitle(item.getTitle()); //khi click vào item hiển thị tieu de lên toolbar
                drawerLayout.close(); //đóng nav
                return true;
            }
        });
    }
    public void replaceFrg(Fragment frg) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frmnav, frg).commit();
        // Ẩn FloatingActionButton
        floatingActionButton.setVisibility(View.GONE);
    }
}