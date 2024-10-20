package com.example.qrcode;

import static com.example.qrcode.Database.DEVICE_TABLE_NAME;
import static com.example.qrcode.Database.DEVICE_HISTORY_TABLE_NAME;
import static com.example.qrcode.Database.HISTORY_ENDTIME;
import static com.example.qrcode.Database.HISTORY_ERROR_COUNT;
import static com.example.qrcode.Database.HISTORY_ID;
import static com.example.qrcode.Database.HISTORY_ID_CODE;
import static com.example.qrcode.Database.HISTORY_STARTTIME;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {
        super(context, Database.DB_NAME, null, Database.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Database.CREATE_DEVICE_TABLE);
        db.execSQL(Database.CREATE_DEVICE_HISTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Database.DEVICE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Database.DEVICE_HISTORY_TABLE_NAME); // Xóa bảng nếu có
        onCreate(db);
    }

    public boolean isIdExists(String idCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Database.DEVICE_TABLE_NAME, null, Database.DEVICE_ID_CODE + "=?", new String[]{idCode}, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        db.close();
        return exists;
    }

    public boolean insertData(final String idCode, final String code, final String name, final String stage, final String issue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Database.DEVICE_ID_CODE, idCode);
        values.put(Database.DEVICE_CODE, code);
        values.put(Database.DEVICE_NAME, name);
        values.put(Database.DEVICE_STAGE, stage);
        values.put(Database.DEVICE_ISSUE, issue);

        try {
            long result = db.insert(Database.DEVICE_TABLE_NAME, null, values);
            db.close();
            return result != -1;
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error while inserting data: " + e.getMessage());
            return false;
        }
    }
    public boolean insertDevice(Device device) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Database.DEVICE_ID_CODE, device.getIdCode());
        contentValues.put(Database.DEVICE_CODE, device.getCode());
        contentValues.put(Database.DEVICE_NAME, device.getName());
        contentValues.put(Database.DEVICE_STAGE, device.getStageName());
        contentValues.put(Database.DEVICE_ISSUE, device.getIssue());

        long result = db.insert(Database.DEVICE_TABLE_NAME, null, contentValues);
        db.close();

        return result != -1;
    }

    public void insertListData(String scannedId, String code, String deviceName, String stage, String issue, String startTime, String endTime, String totalTime) {
        SQLiteDatabase db = this.getWritableDatabase();

        int currentErrorCount;

        Cursor cursor = db.rawQuery("SELECT " + HISTORY_ERROR_COUNT + " FROM " + DEVICE_HISTORY_TABLE_NAME + " WHERE " + HISTORY_ID_CODE + " = ? ORDER BY " + HISTORY_STARTTIME + " DESC LIMIT 1", new String[]{scannedId});

        if (cursor != null && cursor.moveToFirst()) {
            int errorCountIndex = cursor.getColumnIndex(HISTORY_ERROR_COUNT);
            if (errorCountIndex != -1) {
                currentErrorCount = cursor.getInt(errorCountIndex) + 1;
                Log.d("Database Info", "Existing error count found: " + currentErrorCount);
            } else {
                currentErrorCount = 1;
            }
            cursor.close();
        } else {
            currentErrorCount = 1;
        }

        ContentValues newContentValues = new ContentValues();
        newContentValues.put(HISTORY_ID_CODE, scannedId);
        newContentValues.put(Database.HISTORY_CODE, code);
        newContentValues.put(Database.HISTORY_NAME, deviceName);
        newContentValues.put(Database.HISTORY_STAGE, stage);
        newContentValues.put(Database.HISTORY_ISSUE, issue);
        newContentValues.put(Database.HISTORY_STARTTIME, startTime);
        newContentValues.put(Database.HISTORY_ENDTIME, endTime);
        newContentValues.put(Database.HISTORY_TOTALTIME, totalTime);
        newContentValues.put(HISTORY_ERROR_COUNT, currentErrorCount);

        long result = db.insert(DEVICE_HISTORY_TABLE_NAME, null, newContentValues);
        if (result == -1) {
            Log.e("Database Error", "Insert failed for ID_CODE: " + scannedId);
        } else {
            Log.d("Database", "New record inserted successfully for ID_CODE: " + scannedId + " with Error Count: " + currentErrorCount);
        }

        if (cursor != null) {
            cursor.close();
        }
    }

    public void insertNewIssue(String deviceId, String issue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Lấy dữ liệu hiện có
        Cursor cursor = db.query(DEVICE_TABLE_NAME,
                new String[]{Database.DEVICE_ISSUE},
                Database.DEVICE_ID_CODE + "=?",
                new String[]{deviceId},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int issueIndex = cursor.getColumnIndex(Database.DEVICE_ISSUE);

            if (issueIndex != -1) {
                String existingIssues = cursor.getString(issueIndex);
                List<String> issueList = new ArrayList<>(Arrays.asList(existingIssues.split(",")));
                issueList.add(issue); // Thêm vấn đề mới vào danh sách
                String updatedIssues = TextUtils.join(",", issueList);

                // Cập nhật bản ghi
                ContentValues updateValues = new ContentValues();
                updateValues.put(Database.DEVICE_ISSUE, updatedIssues);
                db.update(DEVICE_TABLE_NAME, updateValues, Database.DEVICE_ID_CODE + "=?", new String[]{deviceId});
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();
    }

    public void deleteIssueFromDevice(String deviceId, String issue) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Truy vấn lấy danh sách các vấn đề hiện có
        Cursor cursor = db.query(DEVICE_TABLE_NAME,
                new String[]{Database.DEVICE_ISSUE},
                Database.DEVICE_ID_CODE + "=?",
                new String[]{deviceId},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int issueIndex = cursor.getColumnIndex(Database.DEVICE_ISSUE);
            if (issueIndex != -1) {
                String existingIssues = cursor.getString(issueIndex);
                List<String> issueList = new ArrayList<>(Arrays.asList(existingIssues.split(",")));

                // Xóa vấn đề đã chỉ định
                issueList.remove(issue.trim());

                // Tạo một ContentValues mới để cập nhật dữ liệu
                ContentValues values = new ContentValues();
                if (issueList.isEmpty()) {
                    // Nếu không còn vấn đề nào, xóa trường này
                    values.put(Database.DEVICE_ISSUE, ""); // Đặt thành chuỗi rỗng
                } else {
                    // Cập nhật lại danh sách vấn đề cho bản ghi
                    String updatedIssues = TextUtils.join(",", issueList);
                    values.put(Database.DEVICE_ISSUE, updatedIssues);
                }

                // Thực hiện cập nhật nếu có dữ liệu để cập nhật
                if (values.size() > 0) {
                    db.update(DEVICE_TABLE_NAME, values, Database.DEVICE_ID_CODE + "=?", new String[]{deviceId});
                }
            }
        }

        // Đảm bảo đóng cursor
        if (cursor != null) {
            cursor.close();
        }
        db.close();
    }

    public boolean updateDevice(Device device) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Database.DEVICE_CODE, device.getCode());
        contentValues.put(Database.DEVICE_NAME, device.getName());
        contentValues.put(Database.DEVICE_STAGE, device.getStageName());
        contentValues.put(Database.DEVICE_ISSUE, device.getIssue());

        int result = db.update(DEVICE_TABLE_NAME, contentValues, Database.DEVICE_ID_CODE + "=?", new String[]{device.getIdCode()});
        return result > 0;
    }

    public boolean deleteDevice(String idCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Database.DEVICE_TABLE_NAME, Database.DEVICE_ID_CODE + " = ?", new String[]{idCode}) > 0;
    }

    public boolean deleteHistoryBId(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DEVICE_HISTORY_TABLE_NAME, HISTORY_ID + " = ?", new String[]{String.valueOf(id)}) > 0;
    }

    public void clearHistoryRecords() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + DEVICE_HISTORY_TABLE_NAME); // Delete all records
        db.close();
    }

    public Cursor selectData(String idCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(Database.DEVICE_TABLE_NAME, null, Database.DEVICE_ID_CODE + "=?", new String[]{idCode}, null, null, null);
    }

    public ArrayList<Device> getAllData() {
        ArrayList<Device> arrayList = new ArrayList<>();
        String query = "SELECT * FROM " + Database.DEVICE_TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        String[] columnNames = cursor.getColumnNames();
        Log.d("Database", "Column Names: " + Arrays.toString(columnNames));

        if (cursor.moveToFirst()) {
            do {
                try {
                    int idIndex = cursor.getColumnIndexOrThrow(Database.DEVICE_ID);
                    int idCodeIndex = cursor.getColumnIndexOrThrow(Database.DEVICE_ID_CODE);
                    int codeIndex = cursor.getColumnIndexOrThrow(Database.DEVICE_CODE);
                    int nameIndex = cursor.getColumnIndexOrThrow(Database.DEVICE_NAME);
                    int stageIndex = cursor.getColumnIndexOrThrow(Database.DEVICE_STAGE);
                    int issueIndex = cursor.getColumnIndexOrThrow(Database.DEVICE_ISSUE);

                    Device model = new Device(
                            cursor.getInt(idIndex),
                            cursor.getString(idCodeIndex),
                            cursor.getString(codeIndex),
                            cursor.getString(nameIndex),
                            cursor.getString(stageIndex),
                            cursor.getString(issueIndex)
                    );

                    arrayList.add(model);
                } catch (IllegalArgumentException e) {
                    Log.e("Database Error", "Column not found: " + e.getMessage());
                }
            } while (cursor.moveToNext());
        } else {
            Log.e("Database", "No data found in cursor.");
        }

        cursor.close();
        db.close();
        return arrayList;
    }

    public ArrayList<Device_History> getHistoryData() {
        ArrayList<Device_History> list = new ArrayList<>();
        String query = "SELECT * FROM " + DEVICE_HISTORY_TABLE_NAME + " ORDER BY " + HISTORY_ENDTIME + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        String[] columnNames = cursor.getColumnNames();
        Log.d("Database", "Column Names: " + Arrays.toString(columnNames));

        if (cursor.moveToFirst()) {
            do {
                try {
                    int idIndex = cursor.getColumnIndexOrThrow(Database.HISTORY_ID);
                    int idCodeIndex = cursor.getColumnIndexOrThrow(HISTORY_ID_CODE);
                    int codeIndex = cursor.getColumnIndexOrThrow(Database.HISTORY_CODE);
                    int nameIndex = cursor.getColumnIndexOrThrow(Database.HISTORY_NAME);
                    int stageIndex = cursor.getColumnIndexOrThrow(Database.HISTORY_STAGE);
                    int issueIndex = cursor.getColumnIndexOrThrow(Database.HISTORY_ISSUE);
                    int startTimeIndex = cursor.getColumnIndexOrThrow(Database.HISTORY_STARTTIME);
                    int endTimeIndex = cursor.getColumnIndexOrThrow(Database.HISTORY_ENDTIME);
                    int totalTimeIndex = cursor.getColumnIndexOrThrow(Database.HISTORY_TOTALTIME);
                    int recordCount = cursor.getColumnIndexOrThrow(Database.HISTORY_ERROR_COUNT);

                    Device_History dh = new Device_History(
                            cursor.getInt(idIndex),
                            cursor.getString(idCodeIndex),
                            cursor.getString(codeIndex),
                            cursor.getString(nameIndex),
                            cursor.getString(stageIndex),
                            cursor.getString(issueIndex),
                            cursor.getString(startTimeIndex),
                            cursor.getString(endTimeIndex),
                            cursor.getString(totalTimeIndex),
                            cursor.getInt(recordCount));
                    list.add(dh);
                } catch (IllegalArgumentException e) {
                    Log.e("Database Error", "Column not found: " + e.getMessage());
                }
            } while (cursor.moveToNext());
        } else {
            Log.e("Database", "No data found in cursor.");
        }

        cursor.close();
        db.close();
        return list;
    }

}
