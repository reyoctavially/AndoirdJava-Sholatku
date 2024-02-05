package org.d3ifcool.sholatku.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class DataContract {
    private DataContract() {}
    public static final String CONTENT_AUTHORITY = "org.d3ifcool.sholatku";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH = "sholatku";
    public static final class Entry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH);
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;

        public final static String TABLE_NAME = "sholatku";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_SHOLAT ="sholat";
        public final static String COLUMN_TANGGAL ="tanggal";
        public final static String COLUMN_WAKTU_SHOLAT ="waktusholat";
        public final static String COLUMN_WAKTU_SHOLAT_DIKERJAKAN ="waktudikerjakan";
        public final static String COLUMN_WAKTU_TELAT ="waktutelat";
    }
}
