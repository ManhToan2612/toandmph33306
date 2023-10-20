package com.example.toan_ph33306.fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.toan_ph33306.DAO.TheLoaiDao;
import com.example.toan_ph33306.R;
import com.example.toan_ph33306.adapter.TheLoaiAdapter;
import com.example.toan_ph33306.model.TheLoai;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;


public class TheLoaiFrg extends Fragment {

    FloatingActionButton fltadds;
    RecyclerView rcvtheloai;
    TheLoaiDao dao;
    TheLoaiAdapter adapter;
    public TheLoaiFrg() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_the_loai_frg, container, false);
        rcvtheloai = view.findViewById(R.id.rcvtheloai);
        FloatingActionButton fltadd = view.findViewById(R.id.fltadtheloai);
        fltadds = view.findViewById(R.id.fltadtheloai);
        dao = new TheLoaiDao(getContext());
        ArrayList<TheLoai> list = dao.getalltheloai();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvtheloai.setLayoutManager(layoutManager);
        adapter = new TheLoaiAdapter(list, getContext());
        rcvtheloai.setAdapter(adapter);
        fltadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTL();
            }
        });
        return view;
    }
    public void addTL(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.them_loaisach,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextInputLayout in_TenLS = view.findViewById(R.id.in_addTenLS);
        TextInputEditText ed_TenLS = view.findViewById(R.id.ed_addTenLS);
        Button AddLS = view.findViewById(R.id.LS_add);
        Button CancelLS = view.findViewById(R.id.LS_Cancel);

        ed_TenLS.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0){
                    in_TenLS.setError("Vui lòng nhập tên loại sách");
                }else{
                    in_TenLS.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        AddLS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenloai = ed_TenLS.getText().toString();
                if(tenloai.isEmpty()){
                    if(tenloai.equals("")){
                        in_TenLS.setError("Vui lòng nhập tên loại sách");
                    }else{
                        in_TenLS.setError(null);
                    }
                }else{
                    if(dao.insert(tenloai)){
                        loadData();
                        Toast.makeText(getContext(), "Thêm loại sách thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }else{
                        Toast.makeText(getContext(), "Thêm loại sách không thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        CancelLS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed_TenLS.setText("");
            }
        });

    }
    private void loadData(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvtheloai.setLayoutManager(layoutManager);
        ArrayList<TheLoai> list = dao.getalltheloai();
        adapter = new TheLoaiAdapter(list, getContext());
        rcvtheloai.setAdapter(adapter);
    }
}