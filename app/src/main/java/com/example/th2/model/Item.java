package com.example.th2.model;

import java.io.Serializable;

public class Item implements Serializable {

    private int id,soluong;
    private String name, desc, date, category, condition;

    public Item(String name, String desc, String date, String category, String condition) {
        this.name = name;
        this.desc = desc;
        this.date = date;
        this.category = category;
        this.condition = condition;
    }

    public Item(int id, String name, String desc, String date, String category, String condition) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.date = date;
        this.category = category;
        this.condition = condition;
    }
    public Item(int soluong, String category) {
        this.soluong = soluong;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
