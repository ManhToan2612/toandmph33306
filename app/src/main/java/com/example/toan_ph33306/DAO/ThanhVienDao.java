package com.example.toan_ph33306.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.toan_ph33306.Dbhelper.Dbhelper;
import com.example.toan_ph33306.model.ThanhVien;

import java.util.ArrayList;

public class ThanhVienDao {
    private Dbhelper dbHelper;

    public ThanhVienDao(Context context) {
        dbHelper = new Dbhelper(context);
    }

    public ArrayList<ThanhVien> getalltv() {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        ArrayList<ThanhVien> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM THANHVIEN", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new ThanhVien(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));

            } while (cursor.moveToNext());
        }
        return list;
    }

    public boolean insert(String hoten, String namsinh){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hoTen",hoten);
        values.put("namSinh",namsinh);
        long check = db.insert("THANHVIEN",null,values);
        if(check == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean update(int matv,String hoten, String namsinh){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hoTen",hoten);
        values.put("namSinh",namsinh);
        long check = db.update("THANHVIEN",values,"maTV = ?",new String[]{String.valueOf(matv)});
        if(check == -1){
            return false;
        }else{
            return true;
        }
    }

    public int delete(int matv) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from PHIEUMUON where maTV = ?", new String[]{String.valueOf(matv)});
        if (cursor.getCount() != 0) {
            return -1;
        }

        long check = db.delete("THANHVIEN", "maTv = ?", new String[]{String.valueOf(matv)});
        if (check == -1) {
            return 0;
        } else {
            return 1;
        }

    }
}
