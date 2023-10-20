package com.example.toan_ph33306.Dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Dbhelper extends SQLiteOpenHelper {

   public Dbhelper(@Nullable Context context){
       super(context, "PNLIB", null, 3);
   }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

       // Bang thu thu
        String tb_thuthu= "create table THUTHU(maTT text primary key," +
                "hoTen text, " +
                "matKhau text)";
        sqLiteDatabase.execSQL(tb_thuthu);

        // Bang thanh vien
        String tb_thanhvien= "create table THANHVIEN(maTV integer primary key autoincrement," +
                "hoTen text," +
                "namSinh text)";
        sqLiteDatabase.execSQL(tb_thanhvien);

        //Bang loai sach
        String tb_loaisach= "create table LOAISACH(maLoai integer primary key autoincrement," +
                "tenLoai text)";
        sqLiteDatabase.execSQL(tb_loaisach);

        // Bang sach
        String tb_sach= "create table SACH(maSach integer primary key autoincrement," +
                "tenSach text," +
                "tienThue integer," +
                "maLoai integer references LOAISACH(maLoai))";
        sqLiteDatabase.execSQL(tb_sach);

        // Bang phieu muon
        String tb_phieumuon= "create table PHIEUMUON(maPM integer primary key autoincrement," +
                "maTT references THUTHU(maTT)," +
                "maTV integer references THANHVIEN(maTV)," +
                "maSach integer references SACH(maSach)," +
                "ngay text," +
                "traSach integer," +
                "tienThue integer)";
        sqLiteDatabase.execSQL(tb_phieumuon);

        sqLiteDatabase.execSQL("insert into LOAISACH values (1, 'Naruto'), (2, 'Tinh cam')");
        sqLiteDatabase.execSQL("insert into SACH values(1, 'Bon con cuu', 20000, 1), (2, 'Em va anh', 45555, 2), (3, 'Lap trinh', 40000, 3), (4, 'Sach giai van', 5000, 4)");
        sqLiteDatabase.execSQL("insert into THUTHU values(1, 'admin', 'admin'), (2, 'toan', '222'), (3, 'as', 'df')");
        sqLiteDatabase.execSQL("insert into THANHVIEN values(1, 'Do Manh Toan', '2005'), (2, 'Do Van Linh', '2004')");
        sqLiteDatabase.execSQL("insert into PHIEUMUON values(1,1,1,1, '19/03/2022', 1, 2500), (2,2,2,3,'19/03/2022',0,2000), (3,3,3,1,'19/03/2022',1,2000)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if(i != i1){
            sqLiteDatabase.execSQL("drop table if exists NGUOIDUNG");
            sqLiteDatabase.execSQL("drop table if exists THUTHU");
            sqLiteDatabase.execSQL("drop table if exists THANHVIEN");
            sqLiteDatabase.execSQL("drop table if exists LOAISACH");
            sqLiteDatabase.execSQL("drop table if exists SACH");
            sqLiteDatabase.execSQL("drop table if exists PHIEUMUON");
            onCreate(sqLiteDatabase);
        }
    }
}
