package com.example.th2.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.th2.DateComparator;
import com.example.th2.R;
import com.example.th2.adapter.RecycleViewAdapter;
import com.example.th2.dal.SQLHelper;
import com.example.th2.model.Item;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class FragmentSearch extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private Button btSearch,btStatistic;
    private SearchView searchView;
    private EditText efrom,eto;
    private Spinner spCategory, spCondition;
    private RecycleViewAdapter adapter;
    private SQLHelper db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_search,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        adapter = new RecycleViewAdapter();
        db = new SQLHelper(getContext());
        List<Item> list =  db.getAll();
        Collections.sort(list, new DateComparator());
        adapter.setList(list);
        LinearLayoutManager manager  = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Item> list = db.searchByName(s);
                Collections.sort(list, new DateComparator());
                adapter.setList(list);
                return true;
            }
        });
        efrom.setOnClickListener(this);
        eto.setOnClickListener(this);
        btSearch.setOnClickListener(this);
        btStatistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
                SQLHelper sqLHelper = new SQLHelper(getActivity());
                List<Item> statistic = sqLHelper.getStatistic();
                Collections.sort(list, new DateComparator());
                Log.v("kiemtrahihi",String.valueOf(statistic.size()));
                adapter.setList(statistic);
            }
        });
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String tt = spCategory.getItemAtPosition(position).toString();
                List<Item> list;
                if(!tt.equalsIgnoreCase("all")){
                    list = db.searchByCategory(tt);
                }else{
                    list = db.getAll();
                }
                Collections.sort(list, new DateComparator());
                adapter.setList(list);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spCondition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String conditionStr = spCondition.getItemAtPosition(position).toString();
                String category = spCategory.getSelectedItem().toString();
                List<Item> list;

                if (conditionStr.equalsIgnoreCase("all")) {
                    list = db.getAll();
                } else {
                    boolean conditionValue = conditionStr.equalsIgnoreCase("Còn hàng");
                    list = db.searchByConditionAndCategory(conditionValue, category);
                }
                Collections.sort(list, new DateComparator());
                adapter.setList(list);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void initView(View view) {
        recyclerView  = view.findViewById(R.id.recycleView);
        btSearch = view.findViewById(R.id.btSearch);
        btStatistic = view.findViewById(R.id.btStatistic);
        searchView = view.findViewById(R.id.search);
        efrom = view.findViewById(R.id.eFrom);
        eto = view.findViewById(R.id.eTo);
        spCategory = view.findViewById(R.id.spCategory);
        spCondition = view.findViewById(R.id.spCondition);

        String[] arr = getResources().getStringArray(R.array.category);
        String[] arr1 = new String[arr.length+1];
        arr1[0] = "All";
        for(int i = 0; i < arr.length; i++){
            arr1[i+1] = arr[i];
        }
        String[] carr = getResources().getStringArray(R.array.condition);
        String[] carr1 = new String[carr.length+1];
        carr1[0] = "All";
        for(int i = 0; i < carr.length; i++){
            carr1[i+1] = carr[i];
        }
        spCategory.setAdapter(new ArrayAdapter<>(getContext(),R.layout.item_spinner, arr1));
        spCondition.setAdapter(new ArrayAdapter<>(getContext(),R.layout.item_spinner, carr1));

    }

    @Override
    public void onClick(View view) {
        if(view == efrom){
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day  = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                    String date = "";
                    if(m > 8){
                        date = d + "/" + (m+1) + "/" + y;

                    }
                    else{
                        date = d + "/0" + (m+1) + "/" + y;

                    }
                    efrom.setText(date);
                }
            },year, month, day);
            dialog.show();
        }
        if(view == eto){
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day  = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                    String date = "";
                    if(m > 8){
                        date = d + "/" + (m+1) + "/" + y;

                    }
                    else{
                        date = d + "/0" + (m+1) + "/" + y;

                    }
                    eto.setText(date);
                }
            },year, month, day);
            dialog.show();
        }
        if(view == btSearch){
            String from = efrom.getText().toString();
            String to = eto.getText().toString();
            if(!from.isEmpty() && !to.isEmpty()){
                List<Item> list = db.searchByDateFromTo(from, to);
                Collections.sort(list, new DateComparator());
                adapter.setList(list);

            }
        }
    }
}

