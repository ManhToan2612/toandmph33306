package com.example.toan_ph33306.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.toan_ph33306.Dbhelper.Dbhelper;
import com.example.toan_ph33306.model.Sach;

import java.util.ArrayList;

public class ThongKeDao {

    private Dbhelper dbHelper;
    public ThongKeDao(Context context){
        dbHelper = new Dbhelper(context);
    }
    public ArrayList<Sach> getTop10(){
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT PHIEUMUON.MASACH, SACH.TENSACH, COUNT(PHIEUMUON.MASACH) FROM PHIEUMUON, SACH WHERE PHIEUMUON.MASACH = SACH.MASACH GROUP BY PHIEUMUON.MASACH, SACH.TENSACH ORDER BY COUNT(PHIEUMUON.MASACH) DESC LIMIT 10",null);
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new Sach(cursor.getInt(0),cursor.getString(1),cursor.getInt(2)));
            }while (cursor.moveToNext());
        }

        return list;
    }
    public int tongDoanhThu(String ngayBatDau, String ngayKetThuc){
        ngayBatDau = ngayBatDau.replace("/","");
        ngayKetThuc = ngayKetThuc.replace("/","");

        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT SUM(TienThue) FROM PHIEUMUON WHERE substr(NGAY,7) || substr(NGAY,4,2) || substr(NGAY,1,2) BETWEEN ? AND ?", new String[]{ngayBatDau,ngayKetThuc});
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            return cursor.getInt(0);
        }else {

            return 0;
        }

    }
}
