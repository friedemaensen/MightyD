package test.com.example.rich1016.musterloesung1;

/**
 * Created by vofr1011 on 15.01.2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wech1025 on 29.11.2017.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static DbHelper instance;

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



/*    public void funktion(Track track) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valuesUser = new ContentValues();
        valuesUser.put(DbContract.KoordinateTable.COLUMN_NAME_USERNAME, track.getName());
        valuesUser.put(DbContract.KoordinateTable.COLUMN_NAME_BIRTHDAY, track.getGeburtsta());
        valuesUser.put(DbContract.KoordinateTable.COLUMN_NAME_GENDER, track.getGeschlecht());
        db.insert(DbContract.KoordinateTable.TABLE_NAME, null, valuesUser);
        db.close();
    }*/
  /*  public void funktion2() {
        String selectQuery = "SELECT * FROM " + DbContract.UserTable.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()) {
            do {
                String userName = cursor.getString(cursor.getColumnIndex(DbContract.UserTable.COLUMN_NAME_USERNAME));
                String userGeburtstag = cursor.getString(cursor.getColumnIndex(DbContract.UserTable.COLUMN_NAME_BIRTHDAY));
                String userGeschlecht = cursor.getString(cursor.getColumnIndex(DbContract.UserTable.COLUMN_NAME_GENDER));
                User user = new User();
                user.setNamen(userName);
                user.setGeburtstag(userGeburtstag);
                user.setGeschlecht(userGeschlecht);
                UserData.getInstance().addUser(user);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
    }*/
}