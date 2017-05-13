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
    // table names
    private static final String TABLE_CATEGORY = "category";
    private static final String TABLE_SUBAMOUNT = "subamount"; //weak
    // Category Table Column names
    private static final String KEY_NAME = "name"; //key
    private static final String COL_TOTAL_AMOUNT = "totalamount";
    // SubAmount Table Column names
    private static final String KEY_ID = "subAmountId"; //key <-- could be the same as KEY_NAME
    private static final String COL_AMOUNT = "amount";
    private static final String COL_EVENT = "event";
    private static final String COL_REFID = "refid";


    public DBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //<editor-fold desc="Category methods">
    // Adding new Category
    public void addCategory(CategoryModel cm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, cm.getName()); // Category Name
        values.put(COL_TOTAL_AMOUNT, cm.getTotalAmount()); // Total Amount
    // Inserting Row
        db.insert(TABLE_CATEGORY, null, values);
        db.close(); // Closing database connection
    }

    // Getting one Category TODO: Restructure this, like SAM
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

    // Getting Categories count, TODO: not sure if I need this... Check!
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
        int returnValue = db.update(TABLE_CATEGORY, values, KEY_NAME + " = ?",
                new String[]{String.valueOf(cm.getName())});
        //Kenny recommended this
        if(db.isOpen()){
            db.close();
        }
        return returnValue;
    }

    // Deleting a shop
    public void deleteCategory(CategoryModel cm) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CATEGORY, KEY_NAME + " = ?",
                new String[] { String.valueOf(cm.getName()) });
        db.close();
    }
    //</editor-fold>

    //<editor-fold desc="SubAmount methods">
    //=================================================
    // Adding new SubAmount
    public void addSubAmount(SubAmountModel sam) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_ID, sam.getSubAmountId());
        values.put(COL_AMOUNT, sam.getAmount());
        values.put(COL_EVENT, sam.getEvent());
        values.put(COL_REFID, sam.getRefID());
        // Inserting Row
        db.insert(TABLE_SUBAMOUNT, null, values);
        db.close(); // Closing database connection
    }

    public SubAmountModel getSubAmountModel(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_SUBAMOUNT + " WHERE "
                + KEY_ID + " = " + id;

        Cursor c = db.rawQuery(selectQuery, null);
        SubAmountModel sam = new SubAmountModel();
        if (c != null) {
            c.moveToFirst();

            sam.setSubAmountId(c.getInt(c.getColumnIndex(KEY_ID)));
            sam.setAmount(c.getInt(c.getColumnIndex(COL_AMOUNT)));
            sam.setEvent(c.getString(c.getColumnIndex(COL_EVENT)));
            sam.setRefID(c.getString(c.getColumnIndex(COL_REFID)));
            c.close();
        }
        // Kenny recommended this
        if(db.isOpen()){
            db.close();
        }
        return sam;
    }

    public List<SubAmountModel> getAllSubAmounts() {
        List<SubAmountModel> subAmountList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_SUBAMOUNT;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                SubAmountModel sam = new SubAmountModel();
                sam.setSubAmountId(c.getInt(c.getColumnIndex(KEY_ID)));
                sam.setAmount(c.getInt(c.getColumnIndex(COL_AMOUNT)));
                sam.setEvent(c.getString(c.getColumnIndex(COL_EVENT)));
                sam.setRefID(c.getString(c.getColumnIndex(COL_REFID)));

                subAmountList.add(sam);
            } while (c.moveToNext());
        }

        c.close();
        // Kenny recommended this
        if(db.isOpen()){
            db.close();
        }
        return subAmountList;
    }

    public List<SubAmountModel> getAllSubToCategory(String category) {
        List<SubAmountModel> subAmountList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_SUBAMOUNT + " WHERE " +
                TABLE_SUBAMOUNT + "." + COL_REFID + " = " + "'" + category + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                SubAmountModel sam = new SubAmountModel();
                sam.setSubAmountId(c.getInt(c.getColumnIndex(KEY_ID)));
                sam.setAmount(c.getInt(c.getColumnIndex(COL_AMOUNT)));
                sam.setEvent(c.getString(c.getColumnIndex(COL_EVENT)));
                sam.setRefID(c.getString(c.getColumnIndex(COL_REFID)));

                subAmountList.add(sam);
            } while (c.moveToNext());
        }

        c.close();
        // Kenny recommended this
        if(db.isOpen()){
            db.close();
        }
        return subAmountList;
    }

    // TODO: Check if I need this.
    // TODO: Test this!
    public int updateSubAmount(SubAmountModel sam) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_EVENT, sam.getEvent());
        values.put(COL_AMOUNT, sam.getAmount());

        // updating row
        int returnValue = db.update(TABLE_SUBAMOUNT, values, KEY_ID + " = ?",
                new String[] { String.valueOf(sam.getSubAmountId())});
        // Kenny recommended this
        if(db.isOpen()){
            db.close();
        }
        return returnValue;
    }

    // TODO: Test this!
    public void deleteSubAmount(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SUBAMOUNT, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
        if(db.isOpen()){
            db.close();
        }
    }

    //</editor-fold>

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CATEGORY + "("
                + KEY_NAME + " VARCHAR(50) PRIMARY KEY," + COL_TOTAL_AMOUNT + " INTEGER)";

        String CREATE_SUBAMOUNT_TABLE = "CREATE TABLE " + TABLE_SUBAMOUNT + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + COL_AMOUNT + " INTEGER,"
                + COL_EVENT + " VARCHAR(50)," + COL_REFID + " VARCHAR(50),"
                + "CONSTRAINT fk FOREIGN KEY(" + COL_REFID
                + ") REFERENCES "+ TABLE_CATEGORY + "(" + KEY_NAME + "))";

        db.execSQL(CREATE_CATEGORY_TABLE);
        db.execSQL(CREATE_SUBAMOUNT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBAMOUNT);
        // Creating tables again
        onCreate(db);
    }



    public void populateDatabaseWithData(){
        // Category
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

        // SubAmount
        SubAmountModel sam = new SubAmountModel(1,100,"Resa hem","Transport");
        addSubAmount(sam);
        sam = new SubAmountModel(2,130,"Resa bort", "Transport");
        addSubAmount(sam);
        sam = new SubAmountModel(3,180,"Mat", "Food");
        addSubAmount(sam);
        sam = new SubAmountModel(4,250,"Ett nytt spel","Entertainment");
        addSubAmount(sam);
        sam = new SubAmountModel(5,350,"Två nya spel","Entertainment");
        addSubAmount(sam);
        sam = new SubAmountModel(6,450,"Tre nya spel","Entertainment");
        addSubAmount(sam);
        sam = new SubAmountModel(7,1250,"Fyra nya spel","Entertainment");
        addSubAmount(sam);
    }
}