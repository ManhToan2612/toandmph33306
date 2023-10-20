package com.example.toan_ph33306.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.toan_ph33306.DAO.ThongKeDao;
import com.example.toan_ph33306.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoanhThuFrg#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoanhThuFrg extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DoanhThuFrg() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoanhThuFrg.
     */
    // TODO: Rename and change types and number of parameters
    public static DoanhThuFrg newInstance(String param1, String param2) {
        DoanhThuFrg fragment = new DoanhThuFrg();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_doanh_thu_frg, container, false);
        EditText edtStart = view.findViewById(R.id.edtNgayBatDau);
        EditText edtEnd = view.findViewById(R.id.edtNgayKetThuc);
        Button btnThongKe = view.findViewById(R.id.btnThongKeDoanhThu);
        TextView txtKetQua = view.findViewById(R.id.txtKetQuaThongKe);
        Calendar calendar = Calendar.getInstance();
        edtStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                                String ngay = "";
                                String thang = "";
                                if (dayOfMonth < 10){
                                    ngay = "0" + dayOfMonth;
                                }else {
                                    ngay = String.valueOf(dayOfMonth);
                                }
                                if ((month + 1) < 10){
                                    thang = "0" + (month + 1);
                                }else {
                                    thang = String.valueOf((month + 1));
                                }
                                edtStart.setText(year + "/" + thang + "/" +ngay);
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
            }
        });


        edtEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                                String ngay = "";
                                String thang = "";
                                if (dayOfMonth < 10){
                                    ngay = "0" + dayOfMonth;
                                }else {
                                    ngay = String.valueOf(dayOfMonth);
                                }
                                if ((month + 1) < 10){
                                    thang = "0" + (month + 1);
                                }else {
                                    thang = String.valueOf((month + 1));
                                }
                                edtEnd.setText(year + "/" + thang + "/" +ngay);
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
            }
        });

        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThongKeDao thongKeDAO = new ThongKeDao(getContext());
                String ngayBD = edtStart.getText().toString();
                String ngayKT = edtEnd.getText().toString();
                int doanhThu1 = thongKeDAO.tongDoanhThu(ngayBD,ngayKT);
                txtKetQua.setText(doanhThu1 + "VND");
            }
        });
        return view;
    }
}