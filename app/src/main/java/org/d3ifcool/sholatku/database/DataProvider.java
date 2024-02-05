package org.d3ifcool.sholatku.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class DataProvider extends ContentProvider {

    public static final String LOG_TAG = DataProvider.class.getSimpleName();
    private static final int SHOLATS = 100;
    private static final int SHOLATKU_ID = 101;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(DataContract.CONTENT_AUTHORITY, DataContract.PATH, SHOLATS);
        sUriMatcher.addURI(DataContract.CONTENT_AUTHORITY, DataContract.PATH + "/#", SHOLATKU_ID);
    }

    private DatabaseHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case SHOLATS:
                cursor = database.query(DataContract.Entry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case SHOLATKU_ID:
                selection = DataContract.Entry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(DataContract.Entry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case SHOLATS:
                return insertSholat(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }
    private Uri insertSholat(Uri uri, ContentValues values) {
        // Check that the name is not null
        String name = values.getAsString(DataContract.Entry.COLUMN_SHOLAT);
        if (name == null) {
            throw new IllegalArgumentException("Dibutuhkan nama sholat");
        }
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        long id = database.insert(DataContract.Entry.TABLE_NAME, null, values);
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case SHOLATS:
                return updateSholatku(uri, contentValues, selection, selectionArgs);
            case SHOLATKU_ID:
                selection = DataContract.Entry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateSholatku(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }
    private int updateSholatku(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (values.containsKey(DataContract.Entry.COLUMN_SHOLAT)) {
            String name = values.getAsString(DataContract.Entry.COLUMN_SHOLAT);
            if (name == null) {
                throw new IllegalArgumentException("requires a name");
            }
        }
        if (values.size() == 0) {
            return 0;
        }
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int rowsUpdated = database.update(DataContract.Entry.TABLE_NAME, values, selection, selectionArgs);
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case SHOLATS:
                rowsDeleted = database.delete(DataContract.Entry.TABLE_NAME, selection, selectionArgs);
                break;
            case SHOLATKU_ID:
                selection = DataContract.Entry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(DataContract.Entry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }


    public Cursor readDatabase(Context context, String tanggal, String sholat) {
        String[] projection = {
                DataContract.Entry._ID,
                DataContract.Entry.COLUMN_SHOLAT,
                DataContract.Entry.COLUMN_TANGGAL,
                DataContract.Entry.COLUMN_WAKTU_SHOLAT,
                DataContract.Entry.COLUMN_WAKTU_SHOLAT_DIKERJAKAN,
                DataContract.Entry.COLUMN_WAKTU_TELAT};

        String selection = DataContract.Entry.COLUMN_TANGGAL + " = '" + tanggal +
                "' AND " + DataContract.Entry.COLUMN_SHOLAT + " = '" + sholat + "'";
        return context.getContentResolver().query(
                DataContract.Entry.CONTENT_URI,
                projection,
                selection,
                null,
                null);
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case SHOLATS:
                return DataContract.Entry.CONTENT_LIST_TYPE;
            case SHOLATKU_ID:
                return DataContract.Entry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }
}
