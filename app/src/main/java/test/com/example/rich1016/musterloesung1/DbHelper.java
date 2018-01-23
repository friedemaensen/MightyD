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
import android.location.Location;
import android.util.Log;

import java.util.ArrayList;

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
        sqLiteDatabase.execSQL(DbContract.SQL_CREATE_KOORDINATE_TABLE);
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

    public int getNumberOfRowsTrackDB() {
        SQLiteDatabase database = this.getReadableDatabase();
        int max = (int) DatabaseUtils.queryNumEntries(database, DbContract.TrackTable.TABLE_NAME);
        database.close();

        return max;
    }

    public int getNumberOfRowsKooDB() {
        SQLiteDatabase database = this.getReadableDatabase();
        int max = (int) DatabaseUtils.queryNumEntries(database, DbContract.KoordinateTable.TABLE_NAME);
        database.close();

        return max;
    }

    public String getLatestName () {
        int i = getNumberOfRowsTrackDB();
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

    public void saveKoos (ArrayList<Location> locations, Track track) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues kooVals = new ContentValues();
        Log.i("LOCATIONS", String.valueOf(locations.size()));
        final int REFNUMBER = track.getId();
        for (int i = 0; i < locations.size(); i++) {
            kooVals.put(DbContract.KoordinateTable.COLUMN_Koordinate_TRACKREFNUMBER, REFNUMBER);
            kooVals.put(DbContract.KoordinateTable.COLUMN_Koordinate_LAT, String.valueOf(locations.get(i).getLatitude()));
            kooVals.put(DbContract.KoordinateTable.COLUMN_Koordinate_LNG, String.valueOf(locations.get(i).getLongitude()));
            kooVals.put(DbContract.KoordinateTable.COLUMN_Koordinate_MODE, "MODE DEFAULT");
            Log.i("LOCATIONS", "Index: " + String.valueOf(i));
            database.insert(DbContract.KoordinateTable.TABLE_NAME, null, kooVals);
        }

        database.close();
    }

    //Hier wird das Location-Array aus der Datenbank zurückgebastelt
    public ArrayList<Location> getLocationsFromDB (Track track) {
        ArrayList<Location> locations = new ArrayList<>();
        final int REFNUMBER = track.getId();
        String selectQ = "SELECT * FROM " + DbContract.KoordinateTable.TABLE_NAME +
                " WHERE " + DbContract.KoordinateTable.COLUMN_Koordinate_TRACKREFNUMBER +
                " = " + REFNUMBER;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQ, null);

        if (cursor.moveToFirst()) {
            do {
                String dbContentLat = cursor.getString(cursor.getColumnIndex(DbContract.KoordinateTable.COLUMN_Koordinate_LAT));
                String dbContentLng = cursor.getString(cursor.getColumnIndex(DbContract.KoordinateTable.COLUMN_Koordinate_LNG));

                Location location = new Location("Location");
                location.setLatitude(Double.parseDouble(dbContentLat));
                location.setLongitude(Double.parseDouble(dbContentLng));
                locations.add(location);

            } while (cursor.moveToNext());
        }

        return  locations;
    }
}