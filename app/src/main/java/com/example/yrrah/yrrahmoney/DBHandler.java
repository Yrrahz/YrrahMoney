package com.example.yrrah.yrrahmoney;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * This class is handling all traffic to and from the Database.
 *
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
    private static final String TABLE_MONTHSTAT = "monthstat";
    // Category Table Column names
    private static final String KEY_NAME = "name"; //key
    private static final String COL_TOTAL_AMOUNT = "totalamount";
    // SubAmount Table Column names
    private static final String KEY_ID = "subAmountId"; //key <-- could be the same as KEY_NAME
    private static final String COL_AMOUNT = "amount";
    private static final String COL_EVENT = "event";
    private static final String COL_REFID = "refid";
    // Monthstat Table Column names
    private static final String KEY_MONTH_ID = "monthId";
    private static final String COL_MONTH = "monthName";
    //private static final String COL_TOTAL = "totalamount";
    private static final String COL_INFO = "monthInfo";
    // Trigger
    private static final String TRIGGER_UPDATE_AMOUNT = "updateTotalAmountTrigger";


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

    // Getting one Category TODO: Restructure this, like SAM, IF I need it...
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

    // Getting Categories count, TODO: not sure if I need this... Check! (Only used in testCases)
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
    // TODO : What the hell is this updating and how??
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

    // Deleting a Category TODO: Does this method need a CM object? I think just a String will do.
    public void deleteCategory(CategoryModel cm) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CATEGORY, KEY_NAME + " = ?",
                new String[] { String.valueOf(cm.getName()) });
        db.close();
    }

    public int totalAmount(){
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT SUM("+COL_TOTAL_AMOUNT+") AS 'sum' FROM "+TABLE_CATEGORY;
        Cursor c = db.rawQuery(countQuery, null);

        if(c != null){
            c.moveToFirst();
            int totalAmount = c.getInt(c.getColumnIndex("sum"));
            c.close();
            db.close();
            return totalAmount;
        }

        if(db.isOpen()){
            db.close();
        }
        return -1;
    }

    public void deleteAllCategoryData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBAMOUNT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);

        String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CATEGORY + "("
                + KEY_NAME + " VARCHAR(50) PRIMARY KEY," + COL_TOTAL_AMOUNT + " INTEGER)";

        String CREATE_SUBAMOUNT_TABLE = "CREATE TABLE " + TABLE_SUBAMOUNT + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + COL_AMOUNT + " INTEGER,"
                + COL_EVENT + " VARCHAR(50)," + COL_REFID + " VARCHAR(50),"
                + "CONSTRAINT fk FOREIGN KEY(" + COL_REFID
                + ") REFERENCES "+ TABLE_CATEGORY + "(" + KEY_NAME + "))";

        db.execSQL(CREATE_CATEGORY_TABLE);
        db.execSQL(CREATE_SUBAMOUNT_TABLE);
    }
    //</editor-fold>

    //<editor-fold desc="SubAmount methods">
    //=================================================
    // Adding new SubAmount
    public void addSubAmount(SubAmountModel sam) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //values.put(KEY_ID, sam.getSubAmountId());
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

    //<editor-fold desc="Month methods">
    // Adding new Month
    public void addMonth(MonthModel monthModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //values.put(KEY_ID, sam.getSubAmountId());
        values.put(COL_MONTH, monthModel.getMonth());
        values.put(COL_TOTAL_AMOUNT, monthModel.getTotalAmount());
        values.put(COL_INFO, monthModel.getText());
        // Inserting Row
        db.insert(TABLE_MONTHSTAT, null, values);
        db.close(); // Closing database connection
    }

    public MonthModel getMonthModel(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_MONTHSTAT + " WHERE "
                + KEY_MONTH_ID + " = " + id;

        Cursor c = db.rawQuery(selectQuery, null);
        MonthModel month = new MonthModel();
        if (c != null) {
            c.moveToFirst();

            month.setId(c.getInt(c.getColumnIndex(KEY_MONTH_ID)));
            month.setMonth(c.getInt(c.getColumnIndex(COL_MONTH)));
            month.setTotalAmount(c.getInt(c.getColumnIndex(COL_TOTAL_AMOUNT)));
            month.setText(c.getString(c.getColumnIndex(COL_INFO)));
            c.close();
        }
        // Kenny recommended this
        if(db.isOpen()){
            db.close();
        }
        return month;
    }

    public List<MonthModel> getAllMonths() {
        List<MonthModel> monthList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_MONTHSTAT;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                MonthModel month = new MonthModel();
                month.setId(c.getInt(c.getColumnIndex(KEY_MONTH_ID)));
                month.setMonth(c.getInt(c.getColumnIndex(COL_MONTH)));
                month.setTotalAmount(c.getInt(c.getColumnIndex(COL_TOTAL_AMOUNT)));
                month.setText(c.getString(c.getColumnIndex(COL_INFO)));

                monthList.add(month);
            } while (c.moveToNext());
        }

        c.close();
        // Kenny recommended this
        if(db.isOpen()){
            db.close();
        }
        return monthList;
    }

    public int returnMonthCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT COUNT(*) AS nrOfMonths FROM " + TABLE_MONTHSTAT;

        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null){
            c.moveToFirst();
            int nrOfMonths = c.getInt(c.getColumnIndex("nrOfMonths"));
            c.close();
            return nrOfMonths;
        }
        return -1;
    }

    public int returnLatestMonth(){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_MONTHSTAT + " WHERE " + KEY_MONTH_ID + " = (SELECT MAX(" + KEY_MONTH_ID +") FROM " + TABLE_MONTHSTAT + ")";

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null){
            c.moveToFirst();

            int monthLastInserted = c.getInt(c.getColumnIndex(COL_MONTH));
            c.close();
            return monthLastInserted;
        }
        return -1;
    }

    public void deleteMonth(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MONTHSTAT, KEY_MONTH_ID + " = ?",
                new String[] { String.valueOf(id) });
        if(db.isOpen()){
            db.close();
        }
    }

    //</editor-fold>

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO : Save these strings somewhere else, they are also used in deleteAllCategoryData
        String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CATEGORY + "("
                + KEY_NAME + " VARCHAR(50) PRIMARY KEY," + COL_TOTAL_AMOUNT + " INTEGER)";

        String CREATE_SUBAMOUNT_TABLE = "CREATE TABLE " + TABLE_SUBAMOUNT + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + COL_AMOUNT + " INTEGER,"
                + COL_EVENT + " VARCHAR(50)," + COL_REFID + " VARCHAR(50),"
                + "CONSTRAINT fk FOREIGN KEY(" + COL_REFID
                + ") REFERENCES "+ TABLE_CATEGORY + "(" + KEY_NAME + "))";

        String CREATE_MONTHSTAT_TABLE = "CREATE TABLE " + TABLE_MONTHSTAT + "("
                + KEY_MONTH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + COL_MONTH
                + " INTEGER," + COL_TOTAL_AMOUNT + " INTEGER," + COL_INFO + " TEXT)";

        String CREATE_TRIGGER = "CREATE TRIGGER " + TRIGGER_UPDATE_AMOUNT + " AFTER INSERT ON "
                + TABLE_SUBAMOUNT + " BEGIN UPDATE " + TABLE_CATEGORY + " SET " + COL_TOTAL_AMOUNT
                + " = (SELECT SUM(" + COL_TOTAL_AMOUNT + ") FROM " + TABLE_CATEGORY + " WHERE " + KEY_NAME
                + " = NEW."+COL_REFID+") + NEW."+COL_AMOUNT+" WHERE " + KEY_NAME + " = NEW."+COL_REFID+";END;";

        db.execSQL(CREATE_CATEGORY_TABLE);
        db.execSQL(CREATE_SUBAMOUNT_TABLE);
        db.execSQL(CREATE_MONTHSTAT_TABLE);
        db.execSQL(CREATE_TRIGGER);

        //setFirstMonth(db); // TODO : This might be a very costly "check" if there is data in TABLE_MONTHSTAT. Check if so and if needed...
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TRIGGER IF EXISTS " + TRIGGER_UPDATE_AMOUNT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBAMOUNT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MONTHSTAT);
        // Creating tables again
        onCreate(db);
    }

    private void setFirstMonth(SQLiteDatabase db){
        String count = "SELECT COUNT(*) FROM " + TABLE_MONTHSTAT;
        Cursor cursor = db.rawQuery(count, null);
        cursor.moveToFirst();

        if(!(cursor.getInt(0) > 0)) {
            Calendar cal=Calendar.getInstance();
            SimpleDateFormat month_date = new SimpleDateFormat("MM", Locale.ENGLISH);

            MonthModel month = new MonthModel(Integer.parseInt(month_date.format(cal.getTime())),0,"indexMonth");
            addMonth(month,db);
        }
        cursor.close();
    }

    private void addMonth(MonthModel monthModel, SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        values.put(COL_MONTH, monthModel.getMonth());
        values.put(COL_TOTAL_AMOUNT, monthModel.getTotalAmount());
        values.put(COL_INFO, monthModel.getText());
        // Inserting Row
        db.insert(TABLE_MONTHSTAT, null, values);
    }

    public void populateDatabaseWithData(){
        // Category
        // Expenditure
        CategoryModel cm = new CategoryModel("Transport", 0);
        addCategory(cm);
        cm = new CategoryModel("Food",0);
        addCategory(cm);
        cm = new CategoryModel("Eating Out",0);
        addCategory(cm);
        cm = new CategoryModel("Rent",0);
        addCategory(cm);
        cm = new CategoryModel("Hygiene",0);
        addCategory(cm);
        cm = new CategoryModel("Entertainment",0);
        addCategory(cm);
        /*
        cm = new CategoryModel("Gifts",0);
        addCategory(cm);
        cm = new CategoryModel("Bills",0);
        addCategory(cm);
        cm = new CategoryModel("Other",0);
        addCategory(cm);
        // Income
        cm = new CategoryModel("Salary",0);
        addCategory(cm);
        cm = new CategoryModel("Other Income",0);
        addCategory(cm);
        */

        // SubAmount
        SubAmountModel sam = new SubAmountModel(100,100,"Resa hem","Transport");
        addSubAmount(sam);
        sam = new SubAmountModel(212,130,"Resa bort", "Transport");
        addSubAmount(sam);
        sam = new SubAmountModel(3421,180,"Mat", "Food");
        addSubAmount(sam);
        sam = new SubAmountModel(434,250,"Ett nytt spel","Entertainment");
        addSubAmount(sam);
        sam = new SubAmountModel(511,350,"Två nya spel","Entertainment");
        addSubAmount(sam);
        sam = new SubAmountModel(623,450,"Tre nya spel","Entertainment");
        addSubAmount(sam);
        sam = new SubAmountModel(756,1250,"Fyra nya spel","Entertainment");
        addSubAmount(sam);
        sam = new SubAmountModel(800,1450,"Fyra nya spel","Entertainment");
        addSubAmount(sam);
        sam = new SubAmountModel(19,250,"Fyra nya spel","Entertainment");
        addSubAmount(sam);

        // Month
        MonthModel month = new MonthModel(0,1500,"Entertainment 44.3;Food 21.2;Transport 34.5;");
        addMonth(month);
        month = new MonthModel(1,1250,"Entertainment 90.7;Hygiene 0.0;Rent 0.0;Eating_Out 0.0;Food 4.08;Transport 5.22;");
        addMonth(month);
        month = new MonthModel(2,3500,"Text2");
        addMonth(month);
        month = new MonthModel(3,10345,"Text3");
        addMonth(month);
        month = new MonthModel(4,5100,"Text4");
        addMonth(month);
        month = new MonthModel(5,5300,"Text5");
        addMonth(month);
    }
}