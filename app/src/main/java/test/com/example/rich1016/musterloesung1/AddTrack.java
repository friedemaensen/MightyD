package test.com.example.rich1016.musterloesung1;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Icon;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import test.com.example.rich1016.musterloesung1.Fragments.IconFragment;
import test.com.example.rich1016.musterloesung1.Helper.TrackHandler;

/**
 * Created by vofr1011 on 15.01.2018.
 */

public class AddTrack extends AppCompatActivity {

    Track track;

    public void pressStart () {
        track = new Track();
        String idForKooDB = DbHelper.getInstance(this).getCurrentPrimaryKey(track);

        //TODO track.setMode(IconFragment.);  <-- Wie funktioniert das?? (Tut)


        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormator = new SimpleDateFormat("dd.MM.yyyy");
        String date = dateFormator.format(currentDate);
        track.setDate(date);

        /* TODO connect Tables
        *  TODO set First/Last Location to Meta Table
        *  TODO set Loop for saving datapoints
        */
    }



    public void pressStop () {

        ArrayList<Location> mLocationList;

        mLocationList = TrackHandler.getInstance(this).getmLocationList();

        DbHelper.getInstance(this).saveTrackToKooDB(mLocationList, track);
        //track.setName(editText.getText//Eingabe im Dialog durch User//);
    }

}
