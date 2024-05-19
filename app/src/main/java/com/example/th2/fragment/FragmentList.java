package com.example.th2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.th2.DateComparator;
import com.example.th2.R;
import com.example.th2.UpdateDeleteActivity;
import com.example.th2.adapter.RecycleViewAdapter;
import com.example.th2.dal.SQLHelper;
import com.example.th2.model.Item;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class FragmentList extends Fragment implements RecycleViewAdapter.ItemListener {
    private RecyclerView recyclerView;
    RecycleViewAdapter adapter;
    private SQLHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_list,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleView);
        adapter = new RecycleViewAdapter();
        db = new SQLHelper(getContext());

        List<Item> list = db.getAll();
        Collections.sort(list, new DateComparator());
        adapter.setList(list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);
    }


    @Override
    public void onItemClick(View view, int position) {
        Item item = adapter.getItem(position);
        Intent intent = new Intent(getActivity(), UpdateDeleteActivity.class);
        intent.putExtra("item", item);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Item> list = db.getAll();
        Collections.sort(list, new DateComparator());
        adapter.setList(list);
    }
}