package com.example.th2;

import com.example.th2.model.Item;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class DateComparator implements Comparator<Item> {

    @Override
    public int compare(Item item1, Item item2) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date1 = sdf.parse(item1.getDate());
            Date date2 = sdf.parse(item2.getDate());
            return date1.compareTo(date2);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
