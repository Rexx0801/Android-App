package com.example.th2.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.th2.model.Item;
import com.example.th2.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

public class SQLHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Quanlydb";
    private static int DATABASE_VERSION = 2;

    public SQLHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE tasks("+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "name TEXT, description TEXT, date TEXT, category TEXT, condition BOOLEAN)";
        db.execSQL(sql);
        String sql1 = "CREATE TABLE users("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "username TEXT,password TEXT, email TEXT)";
        db.execSQL(sql1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }


    public long addItem(Item i){
        ContentValues values = new ContentValues();
        values.put("name", i.getName());
        values.put("description", i.getDesc());
        values.put("date", i.getDate());
        values.put("category", i.getCategory());
        values.put("condition", i.getCondition().equals("Còn hàng"));
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert("tasks", null, values);
    }
    public List<Item> getAll(){
        List list = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();
        String order = "date DESC";
        Cursor rs = st.query("tasks", null, null, null, null, null, order);
        while(rs != null && rs.moveToNext()){
            int id = rs.getInt(0);
            String name = rs.getString(1);
            String desc = rs.getString(2);
            String date = rs.getString(3);
            String category = rs.getString(4);
//            String condition = rs.getString(5);
            boolean conditionValue = rs.getInt(5) == 1;
            String conditionStr = conditionValue ? "Còn hàng" : "Hết hàng";
            list.add(new Item(id,name, desc, date, category, conditionStr));

        }
        return list;
    }
    public List<Item> getByDate(String date){
        List<Item> list = new ArrayList<>();
        String whereClause = "date like ?";
        String[] whereArgs ={date};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("tasks", null, whereClause, whereArgs, null, null, null);
        while(rs != null && rs.moveToNext()){
            int id = rs.getInt(0);
            String name = rs.getString(1);
            String desc = rs.getString(2);
            String date1 = rs.getString(3);
            String category = rs.getString(4);
            boolean conditionValue = rs.getInt(5) == 1;
            String conditionStr = conditionValue ? "Còn hàng" : "Hết hàng";
            list.add(new Item(id,name, desc, date1, category, conditionStr));

        }
        return list;
    }
    public int update(Item i){
        ContentValues values = new ContentValues();
        values.put("name", i.getName());
        values.put("description", i.getDesc());
        values.put("date", i.getDate());
        values.put("category", i.getCategory());
        values.put("condition", i.getCondition().equals("Còn hàng"));
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String whereClause = "ID = ?";
        String[] whereArgs ={String.valueOf(i.getId())};
        System.out.println("Update Item" + String.valueOf(i.getId()));
        return sqLiteDatabase.update("tasks", values, whereClause, whereArgs);
    }
    //delete
    public int delete(int id){
        String whereClause = "ID = ?";
        String[] whereArgs = {String.valueOf(id)};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        System.out.println("Delete Item" + String.valueOf(id));
        return sqLiteDatabase.delete("tasks", whereClause, whereArgs);
    }
    public List<Item> searchByDateFromTo(String from, String to){
        List<Item> list = new ArrayList<>();
        String whereClause = "date between ? AND ?";
        String[] whereArgs ={from, to};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("tasks", null, whereClause, whereArgs, null, null, null);
        while(rs != null && rs.moveToNext()){
            int id = rs.getInt(0);
            String name = rs.getString(1);
            String desc = rs.getString(2);
            String date1 = rs.getString(3);
            String category = rs.getString(4);
            boolean conditionValue = rs.getInt(5) == 1;
            String conditionStr = conditionValue ? "Còn hàng" : "Hết hàng";
            list.add(new Item(id,name, desc, date1, category, conditionStr));

        }
        return list;
    }
    public List<Item> searchByCategory(String tt){


        List<Item> list = new ArrayList<>();
        String whereClause = "category like ?";
        String[] whereArgs ={tt};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("tasks", null, whereClause, whereArgs, null, null, null);
        while(rs != null && rs.moveToNext()){
            int id = rs.getInt(0);
            String name = rs.getString(1);
            String desc = rs.getString(2);
            String date1 = rs.getString(3);
            String category = rs.getString(4);
            boolean conditionValue = rs.getInt(5) == 1;
            String conditionStr = conditionValue ? "Còn hàng" : "Hết hàng";
            list.add(new Item(id,name, desc, date1, category, conditionStr));

        }
        return list;
    }
public List<Item> searchByCondition(boolean conditionValue){
    List<Item> list = new ArrayList<>();
    String whereClause = "condition = ?";
    String[] whereArgs = { conditionValue ? "1" : "0" };

    SQLiteDatabase st = getReadableDatabase();
    Cursor rs = st.query("tasks", null, whereClause, whereArgs, null, null, null);

    while(rs != null && rs.moveToNext()){
        int id = rs.getInt(0);
        String name = rs.getString(1);
        String desc = rs.getString(2);
        String date1 = rs.getString(3);
        String category = rs.getString(4);
        String conditionStr = conditionValue ? "Còn hàng" : "Hết hàng";

        list.add(new Item(id, name, desc, date1, category, conditionStr));
    }

    return list;
}

    public List<Item> searchByName(String key){
        List<Item> list = new ArrayList<>();
        String whereClause = "name like ?";
        String[] whereArgs ={"%" + key + "%"};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("tasks", null, whereClause, whereArgs, null, null, null);
        while(rs != null && rs.moveToNext()){
            int id = rs.getInt(0);
            String name = rs.getString(1);
            String desc = rs.getString(2);
            String date1 = rs.getString(3);
            String category = rs.getString(4);
//            String condition = rs.getString(5);
            boolean conditionValue = rs.getInt(5) == 1;
            String conditionStr = conditionValue ? "Còn hàng" : "Hết hàng";
            list.add(new Item(id,name, desc, date1, category, conditionStr));

        }
        return list;
    }
    public List<Item> searchByConditionAndCategory(boolean conditionValue, String category) {
        List<Item> list = new ArrayList<>();

        String whereClause = "condition = ? AND category = ?";
        String[] whereArgs = { conditionValue ? "1" : "0", category };

        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("tasks", null, whereClause, whereArgs, null, null, null);

        while(rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String name = rs.getString(1);
            String desc = rs.getString(2);
            String date1 = rs.getString(3);
            String categoryStr = rs.getString(4);
            String conditionStr = conditionValue ? "Còn hàng" : "Hết hàng";

            list.add(new Item(id, name, desc, date1, categoryStr, conditionStr));
        }

        return list;
    }
    public List<Item> getStatistic(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        List<Item> itemList = new ArrayList<>();
        String[] projection = {
                "category",
                "COUNT(*) AS soluong"
        };
        String orderBy = "soluong DESC";

        Cursor cursor = sqLiteDatabase.query(
                "tasks",
                projection,
                null,
                null,
                "category",
                null,
                orderBy
        );
        while(cursor!=null && cursor.moveToNext()){
            String category = cursor.getString(0);
            int soluong = cursor.getInt(1);
            Item item = new Item(soluong,category);
            itemList.add(item);
        }
        return itemList;
    }

    //User
    public List<User> getAllUser(){
        List<User> list = new ArrayList<>();

        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("users",null,null,null,null,null,null);
        while (rs!=null && rs.moveToNext()){
            int id = rs.getInt(0);
            String username = rs.getString(1);
            String password = rs.getString(2);
            String email = rs.getString(3);
            list.add(new User(id,username,password,email));
        }
        return list;
    }
    public long addUser(User u){

        ContentValues values = new ContentValues();
        values.put("username",u.getUsername());
        values.put("password",u.getPassword());
        values.put("email",u.getEmail());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        System.out.println("da add user"+u.getUsername()+" thanh cong");
        return sqLiteDatabase.insert("users", null, values);
    }
    public int deleteUser(int id){
        String whereClause = "id= ?";
        String[] whereArgs = {Integer.toString(id)};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete("users",whereClause,whereArgs);
    }
    public boolean checkUserSigup(String username){
        List<User> list = getAllUser();
        for(User x:list){
            if(x.getUsername().equalsIgnoreCase(username)){
                return false;
            }
        }
        return true;
    }
    public boolean checkUser(User u){
        List<User> list = getAllUser();
        for(User x:list){
            if(x.getUsername().equalsIgnoreCase(u.getUsername()) && x.getPassword().equalsIgnoreCase(u.getPassword())){
                return true;
            }
        }
        return false;
    }
    public User getUserByUserName(String username) {
        String[] projection = {
                "id",
                "username",
                "email",
                "password",
        };

        String selection = "username LIKE ?";
        String[] selectionArgs = { "%" + username + "%" };
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        // Thực hiện truy vấn
        Cursor cursor = sqLiteDatabase.query(
                "users", projection, selection, selectionArgs, null, null, null
        );

        User user = null;
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String ten = cursor.getString(1);
            String email = cursor.getString(2);
            String password = cursor.getString(3);
            user = new User();
            user.setId(id);
            user.setEmail(email);
            user.setUsername(ten);
            user.setPassword(password);
        }

        // Đóng Cursor và SQLiteDatabase
        if (cursor != null) {
            cursor.close();
        }
        sqLiteDatabase.close();

        return user;
    }

}
