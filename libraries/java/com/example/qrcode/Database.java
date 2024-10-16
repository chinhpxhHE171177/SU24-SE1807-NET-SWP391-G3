package com.example.qrcode;

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
            DEVICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DEVICE_CODE + " TEXT," +
            DEVICE_ID_CODE + " TEXT," +
            DEVICE_NAME + " TEXT," +
            //DEVICE_STAGE + " TEXT," +
            DEVICE_ISSUE + " TEXT" +
            ")";

    // SQL để tạo bảng DEVICE_HISTORY
    public static final String CREATE_DEVICE_HISTORY_TABLE = "CREATE TABLE " + DEVICE_HISTORY_TABLE_NAME + " (" +
            HISTORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            HISTORY_ID_CODE + " TEXT," +
            HISTORY_CODE + " TEXT," +
            HISTORY_NAME + " TEXT," +
            //HISTORY_STAGE + " TEXT," +
            HISTORY_ISSUE + " TEXT," +
            HISTORY_ERROR_COUNT + " INTEGER DEFAULT 0," +
            HISTORY_STARTTIME + " TEXT," +
            HISTORY_ENDTIME + " TEXT," +
            HISTORY_TOTALTIME + " TEXT" +
            ")";
}

