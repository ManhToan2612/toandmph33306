package com.example.toan_ph33306.adapter;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toan_ph33306.DAO.TheLoaiDao;
import com.example.toan_ph33306.R;
import com.example.toan_ph33306.model.TheLoai;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class TheLoaiAdapter extends RecyclerView.Adapter<TheLoaiAdapter.ViewHoler>{

    private ArrayList<TheLoai> list;
    private Context context;
    TheLoaiDao dao;

    public TheLoaiAdapter(ArrayList<TheLoai> list, Context context) {
        this.list = list;
        this.context = context;
        dao=new TheLoaiDao(context);
    }
    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_loaisach,parent,false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
        holder.edtmaloai.setText(String.valueOf(list.get(position).getMaLoai()));
        holder.edttenloai.setText(list.get(position).getTenloai());
        TheLoai tl = list.get(position);
        holder.imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa loại sách");
                builder.setMessage("Bạn có muốn xóa loại sách này không ?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TheLoaiDao dao = new TheLoaiDao(context);
                        int check = dao.deleteLS(list.get(holder.getAdapterPosition()).getMaLoai());
                        switch (check){
                            case 1:
                                list.clear();
                                list = dao.getalltheloai();
                                notifyDataSetChanged();
                                Toast.makeText(context, "Xóa loại sách thành công", Toast.LENGTH_SHORT).show();
                                break;
                            case -1:
                                Toast.makeText(context, "Không thể xóa vì đang có sách thuộc thể loại", Toast.LENGTH_SHORT).show();
                                break;
                            case 0:
                                Toast.makeText(context, "Xóa loại sách không thành công", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }
                    }
                });
                builder.setNegativeButton("Cancel",null);
                builder.create().show();
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                dialogUpdateTL(tl);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder{
        TextView edtmaloai,edttenloai;
        ImageButton imgbtn;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            edtmaloai= itemView.findViewById(R.id.txtmaloai1);
            edttenloai= itemView.findViewById(R.id.txttenloai1);
            imgbtn= itemView.findViewById(R.id.imgdletetl);
        }
    }

    public void dialogUpdateTL(TheLoai tl){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.up_loaisach,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        TextInputLayout in_TenLS = view.findViewById(R.id.in_updateTenLS);
        TextInputEditText ed_TenLS = view.findViewById(R.id.ed_updateTenLS);
        TextInputLayout in_MaLS = view.findViewById(R.id.in_updateMaLS);
        TextInputEditText ed_MaLS = view.findViewById(R.id.ed_updateMaLS);
        Button UpdateLS = view.findViewById(R.id.LS_update);
        Button CancelLS = view.findViewById(R.id.LS_Cancelupdtae);

        ed_MaLS.setText(String.valueOf(tl.getMaLoai()));
        ed_TenLS.setText(tl.getTenloai());
        UpdateLS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tl.setTenloai(ed_TenLS.getText().toString());

                String TenLS = ed_TenLS.getText().toString();

                if(TenLS.isEmpty()){
                    if(TenLS.equals("")){
                        in_TenLS.setError("Vui Lòng Nhập Tên Loại Sách");
                    }else{
                        in_TenLS.setError(null);
                    }
                }else{
                    if(dao.update(tl)){
                        list.clear();
                        list.addAll(dao.getalltheloai());
//                        list=dao.getalltheloai();
                        notifyDataSetChanged();
                        dialog.dismiss();
                        Toast.makeText(context, "Update thành công", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, "Update không thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        CancelLS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ed_TenLS.setText("");
                dialog.dismiss();
            }
        });
    }

}
