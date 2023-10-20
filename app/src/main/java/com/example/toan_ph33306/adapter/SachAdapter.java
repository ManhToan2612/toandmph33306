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
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toan_ph33306.DAO.SachDao;
import com.example.toan_ph33306.R;
import com.example.toan_ph33306.model.Sach;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.HashMap;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.ViewHoler> {
    private ArrayList<Sach> list;
    private Context context;
    private ArrayList<HashMap<String, Object>> listHM;

    SachDao dao;

    public SachAdapter(ArrayList<Sach> list, Context context,ArrayList<HashMap<String, Object>> listHM) {
        this.list = list;
        this.context = context;

        this.listHM = listHM;
        dao = new SachDao(context);
    }


    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_sach,parent,false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
        holder.txtmas.setText(String.valueOf(list.get(position).getMaSach()));
        holder.txttens.setText((list.get(position).getTenSach()));
        holder.txtgiat.setText(String.valueOf(list.get(position).getGiaThue()));
        holder.txtloais.setText(String.valueOf(list.get(position).getMaLoai()));
        holder.txttenloai.setText((list.get(position).getTenLoai()));
        Sach s=list.get(position);
        holder.Sach_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa Sách");
                builder.setMessage("Bạn có chắc muốn xóa sách này chứ ?");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int check = dao.delete(list.get(holder.getAdapterPosition()).getMaSach());
                        switch (check){
                            case 1:
                                list.clear();
                                list=dao.getSachAll();
                                notifyDataSetChanged();
                                Toast.makeText(context, "Xóa thành công sách", Toast.LENGTH_SHORT).show();
                                break;
                            case 0:
                                Toast.makeText(context, "Xóa không thành công sách", Toast.LENGTH_SHORT).show();
                                break;
                            case -1:
                                Toast.makeText(context, "Không xóa được sách này vì đang còn tồn tại trong phiếu mượn", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }
                    }
                });
                builder.setNegativeButton("Hủy",null);
                builder.create().show();
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                dialogupdatesach(s);
                return false;
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class ViewHoler extends RecyclerView.ViewHolder{

        TextView txtmas,txttens,txtgiat,txtloais,txttenloai;
        ImageView Sach_Delete;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            txtmas= itemView.findViewById(R.id.txtmasach1);
            txttens= itemView.findViewById(R.id.txttensach1);
            txtgiat= itemView.findViewById(R.id.txtgiathue1);
            txtloais = itemView.findViewById(R.id.txtloaisach1);
            txttenloai= itemView.findViewById(R.id.txttenloaisach1);
            Sach_Delete= itemView.findViewById(R.id.btndeletesach);

        }
    }

    public void   dialogupdatesach(Sach sach){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.up_sach,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        TextInputLayout in_TenSach = view.findViewById(R.id.in_updateTenS);
        TextInputLayout in_GiaThue = view.findViewById(R.id.in_updateGiaThue);
        TextInputEditText ed_TenSach = view.findViewById(R.id.ed_updateTenS);
        TextInputEditText ed_GiaThue = view.findViewById(R.id.ed_updateGiaThue);
        Spinner spnSach = view.findViewById(R.id.spnSach);
        Button add = view.findViewById(R.id.S_update);
        Button cancel = view.findViewById(R.id.S_Cancel);
        ed_TenSach.setText(sach.getTenSach());
        ed_GiaThue.setText(String.valueOf(sach.getGiaThue()));
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context,
                listHM,
                (android.R.layout.simple_list_item_1),
                new String[]{"tenLoai"},
                new int[]{android.R.id.text1}
        );
        spnSach.setAdapter(simpleAdapter);
        int index = 0;
        int position = -1;

        for(HashMap<String, Object> item : listHM){
            if((int) item.get("maLoai") == sach.getMaLoai()){
                position = index;
            }
            index ++;
        }
        spnSach.setSelection(position);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tensach = ed_TenSach.getText().toString();
                String checktien = ed_GiaThue.getText().toString();;
                HashMap<String, Object> hs = (HashMap<String, Object>) spnSach.getSelectedItem();
                int maloai = (int) hs.get("maLoai");


                if(tensach.isEmpty() || checktien.isEmpty()){
                    if(tensach.equals("")){
                        in_TenSach.setError("Vui lòng không để trống tên sách");
                    }else{
                        in_TenSach.setError(null);
                    }

                    if(checktien.equals("")){
                        in_GiaThue.setError("Vui lòng không để trống giá thuê");
                    }else{
                        in_GiaThue.setError(null);
                    }
                }else{
                    int tien = Integer.parseInt(checktien);
                    boolean check = dao.update(sach.getMaSach(),tensach,tien,maloai);
                    if(check){
                        list.clear();
                        list=dao.getSachAll();
                        notifyDataSetChanged();
                        Toast.makeText(context, "Cập nhật thành công sách", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }else{
                        Toast.makeText(context, "Cập nhật không thành công sách", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//              ed_TenSach.setText("");
//              ed_GiaThue.setText("");
                dialog.dismiss();
            }
        });
    }
}
