package com.example.th2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.th2.dal.SQLHelper;
import com.example.th2.model.Item;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity implements View.OnClickListener{
    private Spinner spCategory, spCondition;
    private EditText tvName, tvDesc, tvDate;
    private Button btUpdate, btCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
        btCancel.setOnClickListener(this);
        btUpdate.setOnClickListener(this);
        tvDate.setOnClickListener(this);
    }
    private void initView(){
        spCategory = findViewById(R.id.spCategory);
        spCondition = findViewById(R.id.spCondition);
        tvName = findViewById(R.id.tvName);
        tvDesc = findViewById(R.id.tvDesc);
        tvDate = findViewById(R.id.tvDate);
        btUpdate = findViewById(R.id.btUpdate);
        btCancel = findViewById(R.id.btCancel);
        spCategory.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner, getResources().getStringArray(R.array.category)));
        spCondition.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner, getResources().getStringArray(R.array.condition)));
    }
    @Override
    public void onClick(View view) {
        if( view == tvDate){
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day  = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                    String date = "";
                    if(m > 8){
                        date = d + "/" + (m+1) + "/" + y;

                    }
                    else{
                        date = d + "/0" + (m+1) + "/" + y;

                    }
                    tvDate.setText(date);
                }
            },year, month, day);
            dialog.show();

        }
        if(view == btCancel){
            finish();
        }
        if(view == btUpdate){
            String name = tvName.getText().toString();
            String desc = tvDesc.getText().toString();
            String category = spCategory.getSelectedItem().toString();
            String condition = spCondition.getSelectedItem().toString();
            String date = tvDate.getText().toString();
            if(!name.isEmpty() ){
                Item i = new Item(name, desc, date,category, condition);
                SQLHelper db= new SQLHelper((this));
                db.addItem(i);
                finish();
            }

        }

    }
}