package com.example.qrcodenew;

import static com.example.qrcodenew.Database.DEVICE_ID_CODE;
import static com.example.qrcodenew.Database.DEVICE_ISSUE;
import static com.example.qrcodenew.Database.DEVICE_TABLE_NAME; // Cập nhật tên bảng
import static com.example.qrcodenew.Database.DEVICE_HISTORY_TABLE_NAME; // Cập nhật tên bảng
import static com.example.qrcodenew.Database.HISTORY_ENDTIME;
import static com.example.qrcodenew.Database.HISTORY_ERROR_COUNT;
import static com.example.qrcodenew.Database.HISTORY_ID;
import static com.example.qrcodenew.Database.HISTORY_ID_CODE;
import static com.example.qrcodenew.Database.HISTORY_STARTTIME;

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
        db.execSQL(Database.CREATE_DEVICE_HISTORY_TABLE); // Đảm bảo tạo bảng lịch sử
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Database.DEVICE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Database.DEVICE_HISTORY_TABLE_NAME); // Xóa bảng nếu có
        onCreate(db);
    }

    // Kiểm tra nếu ID_CODE đã tồn tại
    public boolean isIdExists(String idCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Database.DEVICE_TABLE_NAME, null, DEVICE_ID_CODE + "=?", new String[]{idCode}, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        db.close();
        return exists;
    }

    // Insert data vào bảng DEVICE
    public boolean insertData(final String idCode, final String code, final String name, final String issue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DEVICE_ID_CODE, idCode);
        values.put(Database.DEVICE_CODE, code);
        values.put(Database.DEVICE_NAME, name);
        values.put(DEVICE_ISSUE, issue);

        try {
            long result = db.insert(Database.DEVICE_TABLE_NAME, null, values);
            db.close();
            return result != -1; // Trả về true nếu chèn thành công
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error while inserting data: " + e.getMessage());
            return false;
        }
    }

//    public void insertListData(String scannedId, String code, String deviceName, String issue, String startTime, String endTime, String totalTime) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        // Khởi tạo biến để lưu số lần lỗi
//        int currentErrorCount = 1; // Mặc định số lỗi là 1
//
//        // Kiểm tra xem đã có bản ghi nào cho ID_CODE chưa
//        Cursor cursor = selectHistoryData(scannedId);
//
//        if (cursor != null && cursor.moveToFirst()) {
//            // Lấy chỉ số cột
//            int errorCountIndex = cursor.getColumnIndex(Database.HISTORY_ERROR_COUNT);
//
//            // Kiểm tra chỉ số cột hợp lệ
//            if (errorCountIndex != -1) {
//                // Lấy số lần lỗi hiện tại và tăng lên 1
//                currentErrorCount = cursor.getInt(errorCountIndex) + 1; // Tăng lỗi lên 1
//            } else {
//                Log.e("Database Error", "Column HISTORY_ERROR_COUNT not found.");
//            }
//
//            cursor.close(); // Đóng cursor
//        }
//
//        // Tạo một bản ghi mới với số lần lỗi đã được cập nhật
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(HISTORY_ID_CODE, scannedId);
//        contentValues.put(Database.HISTORY_CODE, code);
//        contentValues.put(Database.HISTORY_NAME, deviceName);
//        contentValues.put(Database.HISTORY_ISSUE, issue);
//        contentValues.put(Database.HISTORY_STARTTIME, startTime);
//        contentValues.put(Database.HISTORY_ENDTIME, endTime);
//        contentValues.put(Database.HISTORY_TOTALTIME, totalTime);
//        contentValues.put(HISTORY_ERROR_COUNT, currentErrorCount); // Đặt số lỗi đã cập nhật
//
//        // Thêm bản ghi mới vào database
//        db.insert(DEVICE_HISTORY_TABLE_NAME, null, contentValues);
//    }

    public void insertListData(String scannedId, String code, String deviceName, String issue, String startTime, String endTime, String totalTime) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Khởi tạo biến để lưu số lần lỗi
        int currentErrorCount;

        // Truy vấn xem ID_CODE đã tồn tại trong lịch sử chưa
        Cursor cursor = db.rawQuery("SELECT " + HISTORY_ERROR_COUNT + " FROM " + DEVICE_HISTORY_TABLE_NAME + " WHERE " + HISTORY_ID_CODE + " = ? ORDER BY " + HISTORY_STARTTIME + " DESC LIMIT 1", new String[]{scannedId});

        if (cursor != null && cursor.moveToFirst()) {
            // Kiểm tra nếu có giá trị tồn tại
            int errorCountIndex = cursor.getColumnIndex(HISTORY_ERROR_COUNT);
            if (errorCountIndex != -1) {
                // Nếu cột tồn tại, lấy giá trị và tăng số lỗi lên 1
                currentErrorCount = cursor.getInt(errorCountIndex) + 1;
                Log.d("Database Info", "Existing error count found: " + currentErrorCount);
            } else {
                currentErrorCount = 1; // Đặt mặc định nếu không tìm thấy
            }
            cursor.close();
        } else {
            // Nếu không tồn tại , gán số lỗi là 1
            currentErrorCount = 1;
        }

        // Chèn bản ghi mới
        ContentValues newContentValues = new ContentValues();
        newContentValues.put(HISTORY_ID_CODE, scannedId);
        newContentValues.put(Database.HISTORY_CODE, code);
        newContentValues.put(Database.HISTORY_NAME, deviceName);
        newContentValues.put(Database.HISTORY_ISSUE, issue);
        newContentValues.put(Database.HISTORY_STARTTIME, startTime);
        newContentValues.put(Database.HISTORY_ENDTIME, endTime);
        newContentValues.put(Database.HISTORY_TOTALTIME, totalTime);
        newContentValues.put(HISTORY_ERROR_COUNT, currentErrorCount); // Đặt số lỗi đã cập nhật

        // Chèn bản ghi mới
        long result = db.insert(DEVICE_HISTORY_TABLE_NAME, null, newContentValues);
        if (result == -1) {
            Log.e("Database Error", "Insert failed for ID_CODE: " + scannedId);
        } else {
            Log.d("Database", "New record inserted successfully for ID_CODE: " + scannedId + " with Error Count: " + currentErrorCount);
        }

        // Đảm bảo đóng cursor nếu nó không null
        if (cursor != null) {
            cursor.close();
        }
    }

    public void insertNewIssue(String deviceId, String issue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Lấy dữ liệu hiện có
        Cursor cursor = db.query(DEVICE_TABLE_NAME,
                new String[]{DEVICE_ISSUE},
                DEVICE_ID_CODE + "=?",
                new String[]{deviceId},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int issueIndex = cursor.getColumnIndex(DEVICE_ISSUE);

            if (issueIndex != -1) {
                String existingIssues = cursor.getString(issueIndex);
                List<String> issueList = new ArrayList<>(Arrays.asList(existingIssues.split(",")));
                issueList.add(issue); // Thêm vấn đề mới vào danh sách
                String updatedIssues = TextUtils.join(",", issueList);

                // Cập nhật bản ghi
                ContentValues updateValues = new ContentValues();
                updateValues.put(DEVICE_ISSUE, updatedIssues);
                db.update(DEVICE_TABLE_NAME, updateValues, DEVICE_ID_CODE + "=?", new String[]{deviceId});
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();
    }


    public Cursor selectHistoryData(String idCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT COUNT(*) FROM " + DEVICE_HISTORY_TABLE_NAME + " WHERE ID_CODE = ?", new String[]{idCode});
    }

    // Cập nhật dữ liệu cho bảng DEVICE
    public void updateData(final String idCode, final String name, final String issue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Database.DEVICE_NAME, name);
        values.put(DEVICE_ISSUE, issue);
        db.update(Database.DEVICE_TABLE_NAME, values, DEVICE_ID_CODE + " = ?", new String[]{idCode});
        db.close();
    }

    // Phương thức xóa thiết bị theo ID_CODE
    public boolean deleteDevice(String idCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Database.DEVICE_TABLE_NAME, DEVICE_ID_CODE + " = ?", new String[]{idCode}) > 0;
    }

    // Phương thức xóa thiết bị theo ID_CODE
    public boolean deleteHistory(String idCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DEVICE_HISTORY_TABLE_NAME, HISTORY_ID_CODE + " = ?", new String[]{idCode}) > 0;
    }

    // Phương thức xóa thiết bị theo ID
    public boolean deleteHistoryBId(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DEVICE_HISTORY_TABLE_NAME, HISTORY_ID + " = ?", new String[]{String.valueOf(id)}) > 0;
    }

    public void deleteIssueFromDevice(String deviceId, String issue) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Truy vấn lấy danh sách các vấn đề hiện có
        Cursor cursor = db.query(DEVICE_TABLE_NAME,
                new String[]{DEVICE_ISSUE},
                DEVICE_ID_CODE + "=?",
                new String[]{deviceId},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int issueIndex = cursor.getColumnIndex(DEVICE_ISSUE);
            if (issueIndex != -1) {
                String existingIssues = cursor.getString(issueIndex);
                List<String> issueList = new ArrayList<>(Arrays.asList(existingIssues.split(",")));

                // Xóa vấn đề đã chỉ định
                issueList.remove(issue.trim());

                // Tạo một ContentValues mới để cập nhật dữ liệu
                ContentValues values = new ContentValues();
                if (issueList.isEmpty()) {
                    // Nếu không còn vấn đề nào, xóa trường này
                    values.put(DEVICE_ISSUE, ""); // Đặt thành chuỗi rỗng
                } else {
                    // Cập nhật lại danh sách vấn đề cho bản ghi
                    String updatedIssues = TextUtils.join(",", issueList);
                    values.put(DEVICE_ISSUE, updatedIssues);
                }

                // Thực hiện cập nhật nếu có dữ liệu để cập nhật
                if (values.size() > 0) {
                    db.update(DEVICE_TABLE_NAME, values, DEVICE_ID_CODE + "=?", new String[]{deviceId});
                }
            }
        }

        // Đảm bảo đóng cursor
        if (cursor != null) {
            cursor.close();
        }
        db.close();
    }



    // Lấy thông tin một thiết bị theo ID_CODE
    public Cursor selectData(String idCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(Database.DEVICE_TABLE_NAME, null, DEVICE_ID_CODE + "=?", new String[]{idCode}, null, null, null);
    }

    public ArrayList<Device> getAllData() {
        ArrayList<Device> arrayList = new ArrayList<>();
        String query = "SELECT * FROM " + Database.DEVICE_TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // Log các tên cột để gỡ lỗi
        String[] columnNames = cursor.getColumnNames();
        Log.d("Database", "Column Names: " + Arrays.toString(columnNames));

        if (cursor.moveToFirst()) {
            do {
                try {
                    // Lấy chỉ số cột
                    int idIndex = cursor.getColumnIndexOrThrow(Database.DEVICE_ID);
                    int idCodeIndex = cursor.getColumnIndexOrThrow(DEVICE_ID_CODE);
                    int codeIndex = cursor.getColumnIndexOrThrow(Database.DEVICE_CODE);
                    int nameIndex = cursor.getColumnIndexOrThrow(Database.DEVICE_NAME);
                    int issueIndex = cursor.getColumnIndexOrThrow(DEVICE_ISSUE);

                    // Tạo mô hình Device và thêm vào danh sách
                    Device model = new Device(
                            cursor.getInt(idIndex),
                            cursor.getString(idCodeIndex),
                            cursor.getString(codeIndex),
                            cursor.getString(nameIndex),
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

        // Log các tên cột để gỡ lỗi
        String[] columnNames = cursor.getColumnNames();
        Log.d("Database", "Column Names: " + Arrays.toString(columnNames));

        if (cursor.moveToFirst()) {
            do {
                try {
                    // Lấy chỉ số cột
                    int idIndex = cursor.getColumnIndexOrThrow(Database.HISTORY_ID);
                    int idCodeIndex = cursor.getColumnIndexOrThrow(HISTORY_ID_CODE);
                    int codeIndex = cursor.getColumnIndexOrThrow(Database.HISTORY_CODE);
                    int nameIndex = cursor.getColumnIndexOrThrow(Database.HISTORY_NAME);
                    int issueIndex = cursor.getColumnIndexOrThrow(Database.HISTORY_ISSUE);
                    int startTimeIndex = cursor.getColumnIndexOrThrow(Database.HISTORY_STARTTIME);
                    int endTimeIndex = cursor.getColumnIndexOrThrow(Database.HISTORY_ENDTIME);
                    int totalTimeIndex = cursor.getColumnIndexOrThrow(Database.HISTORY_TOTALTIME);
                    int recordCount = cursor.getColumnIndexOrThrow(Database.HISTORY_ERROR_COUNT);

                    // Tạo mô hình Device và thêm vào danh sách
                    Device_History dh = new Device_History(
                            cursor.getInt(idIndex),
                            cursor.getString(idCodeIndex),
                            cursor.getString(codeIndex),
                            cursor.getString(nameIndex),
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

    // Phương thức tăng số lượng lỗi
//    public void incrementErrorCount(String idCode) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(HISTORY_ERROR_COUNT, HISTORY_ERROR_COUNT + 1);
//
//        db.update(Database.DEVICE_TABLE_NAME, values, HISTORY_ID_CODE + "=?", new String[]{idCode});
//        db.close();
//    }
//
//    // Lấy số lần lỗi
//    public int getErrorCount(String idCode) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query(DEVICE_HISTORY_TABLE_NAME, new String[]{HISTORY_ERROR_COUNT}, HISTORY_ID_CODE + "=?", new String[]{idCode}, null, null, null);
//
//        int count = 0; // Số lần lỗi mặc định
//        if (cursor.moveToFirst()) {
//            count = cursor.getInt(0);
//        }
//        cursor.close();
//        db.close();
//        return count;
//    }
}


//package com.example.qrcodenew;
//
//import static com.example.qrcodenew.Database.ERROR_COUNT;
//import static com.example.qrcodenew.Database.ID;
//import static com.example.qrcodenew.Database.ID_CODE;
//import static com.example.qrcodenew.Database.TABLE_NAME;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.util.Log;
//
//import androidx.annotation.Nullable;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//
//public class DatabaseHelper extends SQLiteOpenHelper {
//
//    public DatabaseHelper(@Nullable Context context) {
//        super(context,Database.DB_NAME, null, Database.DB_VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(Database.CREATE_TABLE);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + Database.TABLE_NAME);
//        onCreate(db); // Recreate the table
//    }
//
//    // Check if the ID_CODE already exists
//    public boolean isIdExists(String idCode) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query(Database.TABLE_NAME, null, Database.ID_CODE + "=?", new String[]{idCode}, null, null, null);
//        boolean exists = (cursor.getCount() > 0);
//        cursor.close();
//        db.close();  // Don't forget to close the database connection
//        return exists;
//    }
//
//    // Insert data into the database with error handling
//    public boolean insertData(final String idCode, final String code, final String name, final String issue) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(Database.ID_CODE, idCode);
//        values.put(Database.CODE, code);
//        values.put(Database.NAME, name);
//        values.put(Database.ISSUE, issue);
//
//        try {
//            long result = db.insert(Database.TABLE_NAME, null, values);
//            db.close();
//            return result != -1;  // Return true if insertion was successful
//        } catch (Exception e) {
//            Log.e("DatabaseHelper", "Error while inserting data: " + e.getMessage());
//            return false;
//        }
//    }
//
//    public void insertListData(final String code, final String name, final String issue, final String startTime, final String endTime, final String totalTime){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(Database.CODE,code);
//        values.put(Database.NAME,name);
//        values.put(Database.ISSUE,issue);
//        values.put(Database.STARTTIME, startTime);
//        values.put(Database.ENDTIME, endTime);
//        values.put(Database.TOTALTIME, totalTime);
//        db.insert(TABLE_NAME, null , values);
//        db.close();
//    }
//
//    public void insertListData(final String idCode, final String code, final String name,
//                               final String issue, final String startTime,
//                               final String endTime, final String totalTime) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(Database.ID_CODE, idCode);
//        values.put(Database.CODE, code);
//        values.put(Database.NAME, name);
//        values.put(Database.ISSUE, issue);
//        values.put(Database.STARTTIME, startTime);
//        values.put(Database.ENDTIME, endTime);
//        values.put(Database.TOTALTIME, totalTime);
//        db.insert(TABLE_NAME, null, values);
//        db.close();
//    }
//
//    public void updateData(final String id, final String idCode, final String code, final String name,final String issue){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(Database.ID_CODE,idCode);
//        values.put(Database.CODE,code);
//        values.put(Database.NAME,name);
//        values.put(Database.ISSUE,issue);
//        db.update(TABLE_NAME, values, ID_CODE + " = ? ", new String[]{id});
//        db.close();
//    }
//
//    // Phương thức xóa thiết bị theo ID
//    public boolean deleteDevice(String id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.delete(TABLE_NAME, ID_CODE + " = ?", new String[]{id}) > 0; // Sử dụng TABLE_NAME và ID
//    }
//
//    public void deleteData(String id){
//        SQLiteDatabase db = getWritableDatabase();
//        db.delete(TABLE_NAME,"ID = ?", new String[]{id});
//        db.close();
//    }
//
//    public Cursor selectData(String id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        return db.query(TABLE_NAME, null, ID_CODE + "=?", new String[]{id}, null, null, null);
//    }
//
//
//    public ArrayList<Device> getAllData() {
//        ArrayList<Device> arrayList = new ArrayList<>();
//        String query = "SELECT * FROM " + TABLE_NAME;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//
//        // Log column names for debugging
//        String[] columnNames = cursor.getColumnNames();
//        Log.d("Database", "Column Names: " + Arrays.toString(columnNames));
//
//        if (cursor.moveToFirst()) {
//            do {
//                try {
//                    // Get column indices using getColumnIndexOrThrow to catch errors
//                    int idIndex = cursor.getColumnIndexOrThrow(ID);
//                    int idCodeIndex = cursor.getColumnIndexOrThrow(Database.ID_CODE);
//                    int codeIndex = cursor.getColumnIndexOrThrow(Database.CODE);
//                    int nameIndex = cursor.getColumnIndexOrThrow(Database.NAME);
//                    int issueIndex = cursor.getColumnIndexOrThrow(Database.ISSUE);
//                    int startTimeIndex = cursor.getColumnIndexOrThrow(Database.STARTTIME);
//                    int endTimeIndex = cursor.getColumnIndexOrThrow(Database.ENDTIME);
//                    int totalTimeIndex = cursor.getColumnIndexOrThrow(Database.TOTALTIME);
//
//                    // Create Device model and add to the list
//                    Device model = new Device(
//                            cursor.getInt(idIndex),
//                            cursor.getString(idCodeIndex),
//                            cursor.getString(codeIndex),
//                            cursor.getString(nameIndex),
//                            cursor.getString(issueIndex),
//                            cursor.getString(startTimeIndex),
//                            cursor.getString(endTimeIndex),
//                            cursor.getString(totalTimeIndex)
//                    );
//
//                    arrayList.add(model);
//                } catch (IllegalArgumentException e) {
//                    Log.e("Database Error", "Column not found: " + e.getMessage());
//                }
//            } while (cursor.moveToNext());
//        } else {
//            Log.e("Database", "No data found in cursor.");
//        }
//
//        cursor.close();
//        db.close();
//        return arrayList;
//    }
//
//    public void incrementErrorCount(String idCode) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(ERROR_COUNT, ERROR_COUNT + 1);
//
//        db.update(TABLE_NAME, values, ID_CODE + "=?", new String[]{idCode});
//        db.close();
//    }
//
//    public int getErrorCount(String idCode) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query(TABLE_NAME, new String[]{ERROR_COUNT}, ID_CODE + "=?", new String[]{idCode}, null, null, null);
//
//        int count = 1;
//        if (cursor.moveToFirst()) {
//            count = cursor.getInt(0);
//        }
//        cursor.close();
//        db.close();
//        return count;
//    }
//
//}
//
//
