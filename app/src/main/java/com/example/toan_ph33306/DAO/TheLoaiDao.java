package com.example.toan_ph33306.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.toan_ph33306.Dbhelper.Dbhelper;
import com.example.toan_ph33306.model.TheLoai;

import java.util.ArrayList;

public class TheLoaiDao {
    private Dbhelper dbHelper;
    public TheLoaiDao(Context context){
        dbHelper = new Dbhelper(context);
    }
    public ArrayList<TheLoai> getalltheloai(){
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        ArrayList<TheLoai> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT *FROM LOAISACH",null);
        if (cursor.getCount()!=0) {
            cursor.moveToFirst();
            do {
                list.add(new TheLoai(cursor.getInt(0), cursor.getString(1)));
            }while (cursor.moveToNext());
        }
        return list;
    }
    public int deleteLS(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from SACH where maLoai = ?", new String[]{String.valueOf(id)});
        if(cursor.getCount() != 0){
            return -1;
        }
        long check = db.delete("LOAISACH","maLoai = ?", new String[]{String.valueOf(id)});
        if(check == -1){
            return 0;
        }else{
            return 1;
        }
    }
    public boolean insert(String tenloai){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenLoai", tenloai);
        long check = db.insert("LOAISACH",null,values);
        if(check == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean update(TheLoai loaiSach){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenLoai", loaiSach.getTenloai());
        long check = db.update("LOAISACH",values,"maLoai = ?", new String[]{String.valueOf(loaiSach.getMaLoai())});
        if(check == -1){
            return false;
        }else{
            return true;
        }
    }
}
