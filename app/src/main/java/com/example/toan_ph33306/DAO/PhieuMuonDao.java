package com.example.toan_ph33306.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.toan_ph33306.Dbhelper.Dbhelper;
import com.example.toan_ph33306.model.PhieuMuon;

import java.util.ArrayList;

public class PhieuMuonDao {
    private Dbhelper dbHelper;

    public PhieuMuonDao(Context context) {
        dbHelper = new Dbhelper(context);

    }

    public ArrayList<PhieuMuon> getDSPhieuMuon(){
        ArrayList<PhieuMuon> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT PHIEUMUON.maPM, THANHVIEN.hoTen AS tenTV, SACH.tenSach, PHIEUMUON.tienThue, PHIEUMUON.traSach, PHIEUMUON.ngay\n" +
                "FROM PHIEUMUON \n" +
                "JOIN THANHVIEN ON PHIEUMUON.maTV = THANHVIEN.maTV\n" +
                "JOIN SACH ON PHIEUMUON.maSach = SACH.maSach;\n",null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new PhieuMuon(cursor.getInt(0),cursor.getString(1),cursor.getString(2), cursor.getInt(3), cursor.getInt(4),cursor.getString(5)));
            }while (cursor.moveToNext());
        }
        return list;
    }
    public boolean insert(PhieuMuon pm){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaTT",pm.getMaTT());
        values.put("MaTV", pm.getMaTV());
        values.put("MaSach",pm.getMaSach());
        values.put("TienThue",pm.getTienThue());
        values.put("TraSach",pm.getTrangThai());
        values.put("Ngay",pm.getNgayThue());

        long check = db.insert("PhieuMuon",null,values);
        if(check == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean update(PhieuMuon pm){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maTT",pm.getMaTT());
        values.put("maTV", pm.getMaTV());
        values.put("maSach",pm.getMaSach());
        values.put("tienThue",pm.getTienThue());
        values.put("traSach",pm.getTrangThai());
        values.put("ngay",pm.getNgayThue());

        long check = db.update("PHIEUMUON",values,"maPM = ?",new String[]{String.valueOf(pm.getMaPM())});
        if(check == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean delete(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long check = db.delete("PhieuMuon","MaPM = ?",new String[]{String.valueOf(id)});
        if(check == -1){
            return false;
        }else{
            return true;
        }
    }
}
