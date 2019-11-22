package com.example.krucils;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class KelasFragment extends Fragment {

    private RecyclerView recyclerView;
    private  RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private  static  final  String TAG = "Kelas Fragment";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_kelas, container, false);


        return v;
    }
}