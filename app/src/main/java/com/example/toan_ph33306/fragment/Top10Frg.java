package com.example.toan_ph33306.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.toan_ph33306.DAO.ThongKeDao;
import com.example.toan_ph33306.R;
import com.example.toan_ph33306.adapter.Top10Adapter;
import com.example.toan_ph33306.model.Sach;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Top10Frg#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Top10Frg extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Top10Frg() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Top10Frg.
     */
    // TODO: Rename and change types and number of parameters
    public static Top10Frg newInstance(String param1, String param2) {
        Top10Frg fragment = new Top10Frg();
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
        View view= inflater.inflate(R.layout.fragment_top10_frg, container, false);
        RecyclerView rcvTop10 = view.findViewById(R.id.rcvTop10);

        ThongKeDao thongKeDAO = new ThongKeDao(getContext());
        ArrayList<Sach> list = thongKeDAO.getTop10();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvTop10.setLayoutManager(layoutManager);
        Top10Adapter adapter = new Top10Adapter(getContext(),list);
        rcvTop10.setAdapter(adapter);
        return view;
    }
}