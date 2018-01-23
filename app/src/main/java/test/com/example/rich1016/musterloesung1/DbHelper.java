package test.com.example.rich1016.musterloesung1;

/**
 * Created by vofr1011 on 15.01.2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by wech1025 on 29.11.2017.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static DbHelper instance;
    private ArrayList<Long> timestampArray = new ArrayList<>();


    private DbHelper(Context context) {
        super(context, "track_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DbContract.SQL_CREATE_TRACK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public static DbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DbHelper(context);
        }
        return instance;
    }

    public int getNumberOfRows() {
        SQLiteDatabase database = this.getReadableDatabase();

        int max = (int) DatabaseUtils.queryNumEntries(database, DbContract.TrackTable.TABLE_NAME);

        /*String count = "SELECT COUNT(*) FROM " + DbContract.TrackTable.TABLE_NAME;
        Cursor cursor = database.rawQuery(count, null);
        int max = cursor.getCount();*/

        database.close();
        //cursor.close();

        return max;
    }

    public String getLatestName () {
        int i = getNumberOfRows();
        String latestName = "";

        String selectQ = "SELECT * FROM " + DbContract.TrackTable.TABLE_NAME;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQ, null);
        if (cursor.moveToLast()){
                latestName = cursor.getString(cursor.getColumnIndex(DbContract.TrackTable.COLUMN_Track_NAME));
        }

        cursor.close();
        database.close();

        return latestName;
    }

    public void getTrackDB() {
        String selectQ = "SELECT * FROM " + DbContract.TrackTable.TABLE_NAME;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQ, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DbContract.TrackTable.COLUMN_Track_ID));
                String name = cursor.getString(cursor.getColumnIndex(DbContract.TrackTable.COLUMN_Track_NAME));
                String date = cursor.getString(cursor.getColumnIndex(DbContract.TrackTable.COLUMN_Track_DATE));
                double length = cursor.getDouble(cursor.getColumnIndex(DbContract.TrackTable.COLUMN_Track_LENGTH));
                String duration = cursor.getString(cursor.getColumnIndex(DbContract.TrackTable.COLUMN_Track_DURATION));

                Track track = new Track();

                track.setId(id);
                track.setName(name);
                track.setDuration(duration);
                track.setDate(date);
                track.setLength(length);

                TrackData.getInstance().addTrackToList(track);

            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
    }

    public void saveTrackToDB (Track track) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues trackVals = new ContentValues();
        trackVals.put(DbContract.TrackTable.COLUMN_Track_ID, track.getId());
        trackVals.put(DbContract.TrackTable.COLUMN_Track_NAME, track.getName());
        trackVals.put(DbContract.TrackTable.COLUMN_Track_DATE, track.getDate());
        trackVals.put(DbContract.TrackTable.COLUMN_Track_DURATION, track.getDuration());
        trackVals.put(DbContract.TrackTable.COLUMN_Track_LENGTH, track.getLength());

        database.insert(DbContract.TrackTable.TABLE_NAME, null, trackVals);
        database.close();
    }
}