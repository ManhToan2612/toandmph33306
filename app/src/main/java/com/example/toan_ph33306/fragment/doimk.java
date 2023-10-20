package com.example.toan_ph33306.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.toan_ph33306.DAO.ThuThuDao;
import com.example.toan_ph33306.R;
import com.example.toan_ph33306.dangnhap;


public class doimk extends Fragment {


    public doimk() {
        // Required empty public constructor
    }

    EditText edtpasscu,edtpass1,edtpass2;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_doimk, container, false);
        edtpasscu = view.findViewById(R.id.edtpasscu);
        edtpass1 = view.findViewById(R.id.edtpassnew1);
        edtpass2 = view.findViewById(R.id.edtpassnew2);
        Button btnsave = view.findViewById(R.id.btnsavepass);
        Button btncance = view.findViewById(R.id.btnCancepass);

        btncance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtpasscu.setText("");
                edtpass1.setText("");
                edtpass2.setText("");
            }
        });
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Changepass();
            }
        });
        return  view;
    }

    public void Changepass(){
        String oldPass = edtpasscu.getText().toString();
        String newPass = edtpass1.getText().toString();
        String repass = edtpass2.getText().toString();
        if(newPass.equals(repass)){
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("User_File",getContext().MODE_PRIVATE);
            String hoten = sharedPreferences.getString("hoTen","");
            String mk = sharedPreferences.getString("matKhau","");
            //cập nhật
            ThuThuDao thuThuDao =  new ThuThuDao(getContext());
            boolean check = thuThuDao.updateMK(hoten,oldPass,newPass);
            if(oldPass.equals(mk)){
                if(check){
                    Toast.makeText(getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), dangnhap.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getContext(), "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                }
            }else{
                edtpasscu.setError("Mật khẩu hiện tại không đúng");
            }
        }else{
            edtpass1.setError("Mật Khẩu Không Khớp");
            edtpass2.setError("Mật Khẩu Không Khớp");
        }
    }
}