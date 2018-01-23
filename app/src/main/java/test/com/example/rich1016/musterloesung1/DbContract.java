package test.com.example.rich1016.musterloesung1;

/**
 * Created by vofr1011 on 15.01.2018.
 */

import android.provider.BaseColumns;

/**
 * Created by wech1025 on 29.11.2017.
 */

public class DbContract {
    private DbContract() {}

    static class TrackTable implements BaseColumns {
        public static final String TABLE_NAME = "track_table";
        public static final String COLUMN_Track_ID = "track_id";
        public static final String COLUMN_Track_NAME = "track_name";
        public static final String COLUMN_Track_DATE = "track_date";
        public static final String COLUMN_Track_LENGTH = "track_length";
        public static final String COLUMN_Track_DURATION = "track_duration";
    }
    public static final String SQL_CREATE_TRACK_TABLE = "CREATE TABLE " + TrackTable.TABLE_NAME +  " (" +
            TrackTable._ID + " INTEGER PRIMARY KEY," +
            TrackTable.COLUMN_Track_ID + " INTEGER," +
            TrackTable.COLUMN_Track_NAME + " TEXT," +
            TrackTable.COLUMN_Track_DATE + " TEXT," +
            TrackTable.COLUMN_Track_LENGTH  + " TEXT," +
            TrackTable.COLUMN_Track_DURATION + " TEXT)";
    static class KoordinateTable implements BaseColumns {

        public static final String TABLE_NAME = "koo_table";
        public static final String COLUMN_Koordinate_TRACKREFNUMBER = "koo_tracknumber";
        public static final String COLUMN_Track_MODE = "track_verkehrsmittel";
        public static final String COLUMN_Koordinate_KOO = "koo_koo";

    }

    public static final String SQL_CREATE_META_TABLE = "CREATE TABLE " + KoordinateTable.TABLE_NAME + " (" +
            KoordinateTable._ID + " INTEGER PRIMARY KEY," +
            KoordinateTable.COLUMN_Koordinate_TRACKREFNUMBER + " TEXT," +
            KoordinateTable.COLUMN_Track_MODE + " TEXT," +
            KoordinateTable.COLUMN_Koordinate_KOO + " TEXT)";
}

