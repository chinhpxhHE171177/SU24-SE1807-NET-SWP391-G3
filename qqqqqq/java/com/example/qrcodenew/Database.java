package com.example.qrcodenew;

import android.database.sqlite.SQLiteDatabase;

public class Database {
    public static final String DB_NAME = "DB";
    public static final int DB_VERSION = 2;

    // Bảng DEVICE
    public static final String DEVICE_TABLE_NAME = "DEVICE";
    public static final String DEVICE_ID = "ID";
    public static final String DEVICE_ID_CODE = "ID_CODE";
    public static final String DEVICE_CODE = "CODE";
    public static final String DEVICE_NAME = "NAME";
    public static final String DEVICE_ISSUE = "ISSUE";

    // Bảng DEVICE_HISTORY
    public static final String DEVICE_HISTORY_TABLE_NAME = "DEVICE_HISTORY";
    public static final String HISTORY_ID = "ID";
    public static final String HISTORY_ID_CODE = "ID_CODE";
    public static final String HISTORY_CODE = "CODE";
    public static final String HISTORY_NAME = "NAME";
    public static final String HISTORY_ISSUE = "ISSUE";
    public static final String HISTORY_ERROR_COUNT = "ERROR_COUNT";
    public static final String HISTORY_STARTTIME = "STARTTIME";
    public static final String HISTORY_ENDTIME = "ENDTIME";
    public static final String HISTORY_TOTALTIME = "TOTALTIME";

    // SQL để tạo bảng DEVICE
    public static final String CREATE_DEVICE_TABLE = "CREATE TABLE " + DEVICE_TABLE_NAME + " (" +
            DEVICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + // Thêm AUTOINCREMENT
            DEVICE_CODE + " TEXT," +
            DEVICE_ID_CODE + " TEXT," +
            DEVICE_NAME + " TEXT," +
            DEVICE_ISSUE + " TEXT" +
            ")";

    // SQL để tạo bảng DEVICE_HISTORY
    public static final String CREATE_DEVICE_HISTORY_TABLE = "CREATE TABLE " + DEVICE_HISTORY_TABLE_NAME + " (" +
            HISTORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            HISTORY_ID_CODE + " TEXT," +
            HISTORY_CODE + " TEXT," +
            HISTORY_NAME + " TEXT," +
            HISTORY_ISSUE + " TEXT," +
            HISTORY_ERROR_COUNT + " INTEGER DEFAULT 0," +
            HISTORY_STARTTIME + " TEXT," +
            HISTORY_ENDTIME + " TEXT," +
            HISTORY_TOTALTIME + " TEXT" +
            ")";
}

//package com.example.qrcodenew;
//
//public class Database {
//    public static final String DB_NAME = "DB";
//    public static final int DB_VERSION = 2;
//    public static final String TABLE_NAME = "DEVICE";
//    public static final String ID = "ID";
//    public static final String ID_CODE = "ID_CODE";
//    public static final String CODE = "CODE";
//    public static final String NAME = "NAME";
//    public static final String ISSUE = "ISSUE";
//    public static final String STARTTIME = "STARTTIME";
//    public static final String ENDTIME = "ENDTIME";
//    public static final String TOTALTIME = "TOTALTIME";
//    public static final String ERROR_COUNT = "ERROR_COUNT";
//
//    // SQL để tạo bảng
//    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
//            ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +  // Thêm AUTOINCREMENT
//            ID_CODE + " TEXT," +
//            CODE + " TEXT," +
//            NAME + " TEXT," +
//            ISSUE + " TEXT," +
//            STARTTIME + " TEXT," +
//            ENDTIME + " TEXT," +
//            TOTALTIME + " TEXT," +
//            ERROR_COUNT + " INTEGER DEFAULT 0)";
//}


