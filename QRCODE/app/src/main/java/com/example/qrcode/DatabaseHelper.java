package com.example.qrcode;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "records.db";
    private static final String TABLE_NAME = "record_table";
    private static final String COL_ID = "id";
    private static final String COL_DEVICE_ID = "device_id";
    private static final String COL_DEVICE_NAME = "device_name";
    private static final String COL_COMMON_ISSUES = "common_issues";
    private static final String COL_START_TIME = "start_time";
    private static final String COL_END_TIME = "end_time";
    private static final String COL_TOTAL_TIME = "total_time";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_DEVICE_ID + " TEXT, "
                + COL_DEVICE_NAME + " TEXT, "
                + COL_COMMON_ISSUES + " TEXT, "
                + COL_START_TIME + " TEXT, "
                + COL_END_TIME + " TEXT, "
                + COL_TOTAL_TIME + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

//    public void addRecord(Record record) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COL_DEVICE_ID, record.getDeviceId());
//        values.put(COL_DEVICE_NAME, record.getDeviceName());
//        values.put(COL_COMMON_ISSUES, record.getCommonIssues());
//        values.put(COL_START_TIME, record.getStartTime());
//        values.put(COL_END_TIME, record.getEndTime());
//        values.put(COL_TOTAL_TIME, record.getTotalTime());
//        db.insert(TABLE_NAME, null, values);
//        db.close();
//    }

    public void addRecord(Record record) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_DEVICE_ID, record.getDeviceId());
        values.put(COL_DEVICE_NAME, record.getDeviceName());
        values.put(COL_COMMON_ISSUES, record.getCommonIssues());
        values.put(COL_START_TIME, record.getStartTime());
        values.put(COL_END_TIME, record.getEndTime());
        values.put(COL_TOTAL_TIME, record.getTotalTime());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }


    public void updateRecord(Record record) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_DEVICE_NAME, record.getDeviceName());
        values.put(COL_COMMON_ISSUES, record.getCommonIssues());
        values.put(COL_START_TIME, record.getStartTime());
        values.put(COL_END_TIME, record.getEndTime());
        values.put(COL_TOTAL_TIME, record.getTotalTime());
        db.update(TABLE_NAME, values, COL_ID + "=?", new String[]{String.valueOf(record.getId())});
        db.close();
    }


    public ArrayList<Record> getAllRecords() {
        ArrayList<Record> records = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                Record record = new Record(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6));
                records.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return records;
    }

    public void deleteRecord(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COL_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

//    public void updateRecord(Record record) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COL_DEVICE_NAME, record.getDeviceName());
//        values.put(COL_COMMON_ISSUES, record.getCommonIssues());
//        db.update(TABLE_NAME, values, COL_ID + "=?", new String[]{String.valueOf(record.getId())});
//        db.close();
//    }

    public Record getRecord(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COL_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Record record = new Record(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6));
            cursor.close();
            return record;
        }
        return null;
    }
}
