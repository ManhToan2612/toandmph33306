package com.example.toan_ph33306.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toan_ph33306.R;
import com.example.toan_ph33306.model.Sach;

import java.util.ArrayList;

public class Top10Adapter extends RecyclerView.Adapter<Top10Adapter.ViewHoler> {
    private final Context context;
    private final ArrayList<Sach> list;

    public Top10Adapter(Context context, ArrayList<Sach> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =((Activity)context).getLayoutInflater();
        View view= inflater.inflate(R.layout.item_rcvtop10, parent, false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
        holder.txtMaSach.setText("Mã sách: " + String.valueOf(list.get(position).getMaSach()));
        holder.txtTenSach.setText("Tên sách: " + list.get(position).getTenSach());
        holder.txtSoLuongMuon.setText("Số lượng đã mượn: " + String.valueOf(list.get(position).getSoluongdamuon()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class ViewHoler extends RecyclerView.ViewHolder {
        TextView txtMaSach, txtTenSach, txtSoLuongMuon;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            txtMaSach = itemView.findViewById(R.id.txtMaSachTop10);
            txtTenSach = itemView.findViewById(R.id.txtTenSachTop10);
            txtSoLuongMuon = itemView.findViewById(R.id.txtSoLuongMuonTop10);

        }
    }
}
