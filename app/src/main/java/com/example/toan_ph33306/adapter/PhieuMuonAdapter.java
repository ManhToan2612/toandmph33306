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
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toan_ph33306.DAO.PhieuMuonDao;
import com.example.toan_ph33306.DAO.SachDao;
import com.example.toan_ph33306.DAO.ThanhVienDao;
import com.example.toan_ph33306.R;
import com.example.toan_ph33306.model.PhieuMuon;
import com.example.toan_ph33306.model.Sach;
import com.example.toan_ph33306.model.ThanhVien;

import java.util.ArrayList;
import java.util.HashMap;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.Viewholer>{
    private ArrayList<PhieuMuon> list;
    private Context context;

    PhieuMuonDao phieuMuonDao;


    public PhieuMuonAdapter(ArrayList<PhieuMuon> list, Context context) {
        this.list = list;
        this.context = context;
        phieuMuonDao = new PhieuMuonDao(context);
    }

    @NonNull
    @Override
    public Viewholer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_phieumuon,parent,false);
        return new Viewholer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholer holder, int position) {
        holder.txtmapm.setText(String.valueOf(list.get(position).getMaPM()));
        holder.txttentv.setText(list.get(position).getHoTenTV());

        holder.txttens.setText(list.get(position).getTenSach());
        holder.txtgiat.setText(String.valueOf(list.get(position).getTienThue()));
        String trangthai = "";
        PhieuMuon pm = list.get(position);
        if(list.get(position).getTrangThai() == 1){
            trangthai = "Đã trả sách";
            holder.txttrangthai.setTextColor(ContextCompat.getColor(context, R.color.TrangThai));
        }else{
            trangthai = "Chưa trả sách";
        }
        holder.txttrangthai.setText(trangthai);
        holder.txtngaythue.setText(list.get(position).getNgayThue());



        holder.btnlele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete");
                builder.setMessage("Bạn thật sự muốn xóa phiếu mượn này chứ");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        PhieuMuonDao dao = new PhieuMuonDao(context);
                        boolean check = dao.delete(pm.getMaPM());
                        if (check){
                            list.clear();
                            list = dao.getDSPhieuMuon();
                            notifyDataSetChanged();
                            Toast.makeText(context, "Xóa phiếu mượn thành công", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Xóa phiếu mượn không thành công", Toast.LENGTH_SHORT).show();
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
                dialogupdate(pm);
                return false;
            }
        });
    }
    private void dialogupdate(PhieuMuon pm){
        AlertDialog.Builder builder =new AlertDialog.Builder(context);
        LayoutInflater inflater =((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.up_phieumuon,null);
        builder.setView(view);
        Dialog dialog =builder.create();
        dialog.show();

        Spinner spnThanhVien = view.findViewById(R.id.spnThanhVien);
        Spinner spnSach = view.findViewById(R.id.spnSach);
        Button PM_add = view.findViewById(R.id.PM_update);
        Button PM_cancel = view.findViewById(R.id.PM_Cancel);


        CheckBox chk = view.findViewById(R.id.chkcheck);
        getDataThanhVien(spnThanhVien);
        getDataSach(spnSach);
        loadData();
        PM_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        PM_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int status;
                HashMap<String, Object> hsTV = (HashMap<String, Object>) spnThanhVien.getSelectedItem();
                int matv = (int) hsTV.get("maTV");
                HashMap<String, Object> hsSach = (HashMap<String, Object>) spnSach.getSelectedItem();
                int masach = (int) hsSach.get("maSach");

                if (chk.isChecked()) {
                    status = 1;
                } else {
                    status = 0;
                }
                pm.setMaTV(matv);
                pm.setMaSach(masach);
                pm.setTrangThai(status);

                boolean updated = phieuMuonDao.update(pm);
                if (updated) {
                    loadData();
                    dialog.dismiss();
                    Toast.makeText(context, "Cập nhật phiếu mượn thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Cập nhật phiếu mượn không thành công", Toast.LENGTH_SHORT).show();
                }

                dialog.dismiss();
            }
        });

    }

    private void getDataThanhVien(Spinner spnThanhVien){
        ThanhVienDao thanhVienDao = new ThanhVienDao(context);
        ArrayList<ThanhVien> list = thanhVienDao.getalltv();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for(ThanhVien tv : list){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("maTV",tv.getMaTV());
            hs.put("hoTen",tv.getHoTen());
            listHM.add(hs);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context,
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"hoTen"},
                new int[]{android.R.id.text1});
        spnThanhVien.setAdapter(simpleAdapter);
    }

    private void getDataSach(Spinner spnSach){
        SachDao sachDao = new SachDao(context);
        ArrayList<Sach> list = sachDao.getSachAll();

        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for(Sach sc : list){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("maSach",sc.getMaSach());
            hs.put("tenSach",sc.getTenSach());
            hs.put("tienThue",sc.getGiaThue());
            listHM.add(hs);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context,
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"tenSach"},
                new int[]{android.R.id.text1});
        spnSach.setAdapter(simpleAdapter);
    }

    private void loadData(){
        list.clear();
        list = phieuMuonDao.getDSPhieuMuon();
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholer extends RecyclerView.ViewHolder{
        TextView txttentv,txttens,txtgiat,txtmapm,txttrangthai,txtngaythue;
        ImageButton btnlele;
        public Viewholer(@NonNull View itemView) {
            super(itemView);
            txtmapm= itemView.findViewById(R.id.txtmaPM);
            txttentv= itemView.findViewById(R.id.txttentv);
            txttens= itemView.findViewById(R.id.txttensach);
            txtgiat= itemView.findViewById(R.id.txttienthue);
            txttrangthai= itemView.findViewById(R.id.txttrasach);
            txtngaythue= itemView.findViewById(R.id.txtngaythue);
            btnlele= itemView.findViewById(R.id.btndelete);

        }
    }

}
