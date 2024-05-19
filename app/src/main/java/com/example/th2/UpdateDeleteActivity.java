package com.example.th2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.th2.adapter.ViewPagerAdapter;
import com.example.th2.dal.SQLHelper;
import com.example.th2.model.Item;

import java.util.Calendar;

public class UpdateDeleteActivity extends AppCompatActivity implements View.OnClickListener {
    public Spinner spCategory, spCondition;
    private EditText eName, eDesc, eDate;
    private Button btUpdate, btBack, btRemove;
    private Item item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);
        initView();
        btBack.setOnClickListener(this);
        btUpdate.setOnClickListener(this);
        btRemove.setOnClickListener(this);
        eDate.setOnClickListener(this);
        Intent intent = getIntent();
        item = (Item) intent.getSerializableExtra("item");
        eName.setText(item.getName());
        eDesc.setText(item.getDesc());
        eDate.setText(item.getDate());
        int p = 0;
        for(int i = 0; i < spCategory.getCount(); i++){
            if(spCategory.getItemAtPosition(i).toString().equalsIgnoreCase(item.getCategory())){
                p = i;
                break;
            }
        }
        spCategory.setSelection(p);
        int p1 = 0;
        for(int i = 0; i < spCondition.getCount(); i++){
            if(spCondition.getItemAtPosition(i).toString().equalsIgnoreCase(item.getCondition())){
                p1 = i;
                break;
            }
        }
        spCondition.setSelection(p1);
    }
    private void initView() {
        spCategory = findViewById(R.id.spCategory);
        spCondition = findViewById(R.id.spCondition);
        eName = findViewById(R.id.tvName);
        eDesc = findViewById(R.id.tvDesc);
        eDate = findViewById(R.id.tvDate);
        btUpdate = findViewById(R.id.btUpdate);
        btBack = findViewById(R.id.btBack);
        btRemove = findViewById(R.id.btRemove);
        spCategory.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner, getResources().getStringArray(R.array.category)));
        spCondition.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner, getResources().getStringArray(R.array.condition)));

    }

    @Override
    public void onClick(View view) {
        if(view == eDate){
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day  = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(UpdateDeleteActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                    String date = "";
                    if(m > 8){
                        date = d + "/" + (m+1) + "/" + y;

                    }
                    else{
                        date = d + "/0" + (m+1) + "/" + y;

                    }
                    eDate.setText(date);
                }
            },year, month, day);
            dialog.show();
        }
        if(view == btBack){
            finish();
        }
        if(view == btUpdate){
            String name = eName.getText().toString();
            String desc = eDesc.getText().toString();
            String category = spCategory.getSelectedItem().toString();
            String condition = spCondition.getSelectedItem().toString();

            String d = eDate.getText().toString();
            if(!name.isEmpty() ){
                int id = item.getId();
                System.out.println("Update" + id);
                Item i = new Item(id,name,desc, d, category, condition );
                SQLHelper db1 = new SQLHelper(this);
                db1.update(i);
                finish();
            }
        }
        if(view == btRemove){
            int id = item.getId();
            System.out.println("Delete" + id);
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Thông báo xoá");
            builder.setMessage("Bạn có muốn xoá " + item.getName() + " không?");
            builder.setIcon(R.drawable.ic_delete);
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    SQLHelper db1 = new SQLHelper(getApplicationContext());
                    db1.delete(id);
                    System.out.println("Xoá thành công");
                    finish();
                }
            });
            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}