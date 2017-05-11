package com.example.yrrah.yrrahmoney;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yrrah on 2017-05-05.
 */

public class DBHandler extends SQLiteOpenHelper{

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "YrrahMoneyDB";
    // Contacts table name
    private static final String TABLE_CATEGORY = "category";
    // cms Table Columns names
    private static final String KEY_NAME = "name";
    private static final String COL_TOTAL_AMOUNT = "totalamount";

    public DBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Adding new cm
    public void addCategory(CategoryModel cm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, cm.getName()); // Category Name
        values.put(COL_TOTAL_AMOUNT, cm.getTotalAmount()); // Total Amount
    // Inserting Row
        db.insert(TABLE_CATEGORY, null, values);
        db.close(); // Closing database connection
    }

    // Getting one Category
    public CategoryModel getCategoryModel(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CATEGORY, new String[] { KEY_NAME, COL_TOTAL_AMOUNT },
                KEY_NAME + "=?",
                new String[] { name }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();}

        CategoryModel contact = null;
        if (cursor != null) {
            contact = new CategoryModel(cursor.getString(0), Integer.parseInt(cursor.getString(1)));
        }
        if (cursor != null) {
            cursor.close();
        }
        // Kenny recommended this
        if(db.isOpen()){
            db.close();
        }
        // return Category
        return contact;
    }

    // Getting All Categories
    public List<CategoryModel> getAllCategories() {
        List<CategoryModel> cmList = new ArrayList<>();
    // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_CATEGORY;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
    // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CategoryModel cm = new CategoryModel();
                cm.setName(cursor.getString(0));
                cm.setTotalAmount(Integer.parseInt(cursor.getString(1)));
                cmList.add(cm);
            } while (cursor.moveToNext());
        }
    // return contact list
        cursor.close();
        //Kenny recommended this
        if(db.isOpen()){
            db.close();
        }
        return cmList;
    }

    // Getting Categories count, not sure if I need this...
    public int getCategoriesCount() {
        int count;
        String countQuery = "SELECT * FROM " + TABLE_CATEGORY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        count = cursor.getCount();
        cursor.close();
        //Kenny recommended this
        if(db.isOpen()){
            db.close();
        }
        return count;
    }

    // Updating a Category
    public int updateCategory(CategoryModel cm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TOTAL_AMOUNT, cm.getTotalAmount());
    // updating row
        return db.update(TABLE_CATEGORY, values, KEY_NAME + " = ?",
                new String[]{String.valueOf(cm.getName())});
    }

    // Deleting a shop
    public void deleteCategory(CategoryModel cm) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CATEGORY, KEY_NAME + " = ?",
                new String[] { String.valueOf(cm.getName()) });
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CATEGORY + "("
        + KEY_NAME + " VARCHAR(50) PRIMARY KEY," + COL_TOTAL_AMOUNT + " INTEGER" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        // Creating tables again
        onCreate(db);
    }



    public void populateDatabaseWithData(){
        // Expenditure
        CategoryModel cm = new CategoryModel("Transport", 1000);
        addCategory(cm);
        cm = new CategoryModel("Food",2000);
        addCategory(cm);
        cm = new CategoryModel("Eating Out",2000);
        addCategory(cm);
        cm = new CategoryModel("Rent",4500);
        addCategory(cm);
        cm = new CategoryModel("Hygiene",250);
        addCategory(cm);
        cm = new CategoryModel("Entertainment",5412);
        addCategory(cm);
        cm = new CategoryModel("Gifts",397);
        addCategory(cm);
        cm = new CategoryModel("Bills",4750);
        addCategory(cm);
        cm = new CategoryModel("Other",3713);
        addCategory(cm);
        // Income
        cm = new CategoryModel("Salary",19000);
        addCategory(cm);
        cm = new CategoryModel("Other Income",500);
        addCategory(cm);
    }
}