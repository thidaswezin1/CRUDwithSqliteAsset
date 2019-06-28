package com.example.dbtesting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {

        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all quotes from the database.
     *
     * @return a List of quotes
     */

    public void insertEmployee(Employee e){
        ContentValues values = new ContentValues();
        values.put("name",e.getName());
        values.put("email",e.getEmail());
        values.put("address",e.getAddress());
        database.insert("employee_info",null,values);

    }

    public void updateEmployee(Employee oldEmployee,Employee newEmployee){
        ContentValues values = new ContentValues();
        values.put("name",newEmployee.getName());
        values.put("email",newEmployee.getEmail());
        values.put("address",newEmployee.getAddress());
        database.update("employee_info",values,"name=?",new String[]{oldEmployee.getName()});
    }

    public void deleteEmployee(){
        database.delete("employee_info",null,null);
    }

    public List<Employee> getEmployee(){
        List<Employee> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * from employee_info",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Employee e = new Employee();
            e.setName(cursor.getString(1));
            e.setEmail(cursor.getString(2));
            e.setAddress(cursor.getString(3));
            list.add(e);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

} 