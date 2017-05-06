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
        return cmList;
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

    private void populateDatabaseWithData(){
        // TODO : Write this method. Populate the database with proper data.
    }
}
