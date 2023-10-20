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

import com.example.toan_ph33306.DAO.ThanhVienDao;
import com.example.toan_ph33306.R;
import com.example.toan_ph33306.model.ThanhVien;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ViewHoler>{

    private ArrayList<ThanhVien> list;
    private Context context;
    ThanhVienDao dao;
    public ThanhVienAdapter(ArrayList<ThanhVien> list,Context context){
        this.list = list;
        this.context = context;
        dao = new ThanhVienDao(context);

    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_thanhvien,parent,false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
        holder.txtmatv.setText(String.valueOf(list.get(position).getMaTV()));
        holder.txttetv.setText(list.get(position).getHoTen());
        holder.txtns.setText(list.get(position).getNamSinh());

        ThanhVien tv=list.get(position);
        holder.btnlele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete");
                builder.setMessage("Bạn thật sự muốn xóa phiếu mượn này chứ");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ThanhVienDao dao = new ThanhVienDao(context);
                        int check = dao.delete(tv.getMaTV());
                        switch (check){
                            case 1:
//                                loadData();
                                list.clear();
                                list = dao.getalltv();
                                Toast.makeText(context, "Xóa thành viên thành công", Toast.LENGTH_SHORT).show();
                                break;
                            case 0:
                                Toast.makeText(context, "Xóa thành viên thất bại", Toast.LENGTH_SHORT).show();
                                break;
                            case -1:
                                Toast.makeText(context, "Thành viên đang tồn tại phiếu mượn, hiện tại không thể xóa", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }
                    }
                });
                notifyDataSetChanged();
                builder.setNegativeButton("Hủy",null);
                builder.create().show();
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                dialogUpdate(tv);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHoler extends RecyclerView.ViewHolder{
        TextView txtmatv,txttetv,txtns;
        ImageButton btnlele;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            txtmatv= itemView.findViewById(R.id.txtmatv1);
            txttetv= itemView.findViewById(R.id.hoten1);
            txtns= itemView.findViewById(R.id.txtnamsinh1);
            btnlele= itemView.findViewById(R.id.btndeletetv);
        }
    }

    private void dialogUpdate(ThanhVien tv){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.up_thanhvien,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextInputEditText ed_txtTenTV = view.findViewById(R.id.ed_updateTenTV);
        TextInputEditText ed_txtNamSinh = view.findViewById(R.id.ed_updateNamSinh);
        TextInputEditText ed_txtMaSV = view.findViewById(R.id.ed_updateMaTV);
        TextInputLayout in_txtTenTV = view.findViewById(R.id.in_updateTenTV);
        TextInputLayout in_txtMaTV = view.findViewById(R.id.in_updateMaTV);
        TextInputLayout in_txtNamSinh = view.findViewById(R.id.in_updateNamSinh);
        Button btn_add = view.findViewById(R.id.TV_Update);
        Button btn_cancel = view.findViewById(R.id.TV_Cancel);

        ed_txtMaSV.setText(String.valueOf(tv.getMaTV()));
        ed_txtTenTV.setText(tv.getHoTen());
        ed_txtNamSinh.setText(tv.getNamSinh());

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoten = ed_txtTenTV.getText().toString();
                String namsinh = ed_txtNamSinh.getText().toString();
                int id = tv.getMaTV();

                boolean check = dao.update(id,hoten,namsinh);
                if(hoten.isEmpty() || namsinh.isEmpty()){
                    if(hoten.equals("")){
                        in_txtTenTV.setError("Vui lòng không để trống Họ Tên");
                    }else{
                        in_txtTenTV.setError(null);
                    }

                    if(namsinh.equals("")){
                        in_txtNamSinh.setError("Vui lòng không để trống năm sinh");
                    }else{
                        in_txtNamSinh.setError(null);
                    }
                }else{
                    if(check){
                        list.clear();
                        list=dao.getalltv();
                        notifyDataSetChanged();
                        Toast.makeText(context, "Cập nhật nhân viên thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }else{
                        Toast.makeText(context, "Cập nhật nhân viên thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed_txtTenTV.setText("");
                ed_txtNamSinh.setText("");
            }
        });
    }
}
