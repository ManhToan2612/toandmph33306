package com.example.toan_ph33306;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.toan_ph33306.fragment.DoanhThuFrg;
import com.example.toan_ph33306.fragment.PhieuMuonFrg;
import com.example.toan_ph33306.fragment.SachFrg;
import com.example.toan_ph33306.fragment.ThanhVienFrg;
import com.example.toan_ph33306.fragment.TheLoaiFrg;
import com.example.toan_ph33306.fragment.Top10Frg;
import com.example.toan_ph33306.fragment.doimk;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DrawerLayout dralayout = findViewById(R.id.dralayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        NavigationView nav = findViewById(R.id.menunav);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                dralayout, toolbar, R.string.open, R.string.close);
        dralayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        //xử lý sự kiện khi kích chọn item
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.qlphieumuon) {
                    toolbar.setTitle("Quản lý phiếu mượn");
                    PhieuMuonFrg qlphieumuon = new PhieuMuonFrg();
                    replay(qlphieumuon);
                } else if (item.getItemId() == R.id.qlloaisach) {
                    toolbar.setTitle("Quản lý loại sách");
                    TheLoaiFrg loaisach = new TheLoaiFrg();
                    replay(loaisach);
                } else if (item.getItemId() == R.id.qlsach) {
                    toolbar.setTitle("Quản lý sách");
                    SachFrg sach = new SachFrg();
                    replay(sach);
                } else if (item.getItemId() == R.id.qlthanhvien) {
                    toolbar.setTitle("Quản lý thành viên");
                    ThanhVienFrg thanhvien = new ThanhVienFrg();
                    replay(thanhvien);
                } else if (item.getItemId() == R.id.topsach) {
                    toolbar.setTitle("Top 10 sách mượn nhiều nhất");
                    Top10Frg topsach = new Top10Frg();
                    replay(topsach);
                } else if (item.getItemId() == R.id.doanhthu) {
                    toolbar.setTitle("Doanh thu");
                    DoanhThuFrg doanhthu = new DoanhThuFrg();
                    replay(doanhthu);
                } else if (item.getItemId() == R.id.doimk) {
                    toolbar.setTitle("Đổi mật khẩu");
                    doimk doimk = new doimk();
                    replay(doimk);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Đăng Xuất");
                    builder.setMessage("Bạn chắc chăn muướn đăng xuất chứ!");
                    builder.setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(MainActivity.this, dangnhap.class));
                            finish();
                        }
                    });
                    builder.setNegativeButton("Hủy", null);
                    builder.create().show();
                }

                return true;
            }
        });
    }
    public void replay(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frglayout, fragment).commit();

    }
}