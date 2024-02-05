package org.d3ifcool.sholatku.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = DatabaseHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "sholatku.db";
    private static final int DATABASE_VERSION = 1;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TABLE =  "CREATE TABLE " + DataContract.Entry.TABLE_NAME + " ("
                + DataContract.Entry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DataContract.Entry.COLUMN_SHOLAT + " TEXT NOT NULL, "
                + DataContract.Entry.COLUMN_TANGGAL + " TEXT, "
                + DataContract.Entry.COLUMN_WAKTU_SHOLAT + " TEXT, "
                + DataContract.Entry.COLUMN_WAKTU_SHOLAT_DIKERJAKAN + " TEXT, "
                + DataContract.Entry.COLUMN_WAKTU_TELAT + " TEXT);";
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}