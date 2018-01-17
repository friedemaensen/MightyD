package test.com.example.rich1016.musterloesung1;

import android.graphics.drawable.Icon;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import test.com.example.rich1016.musterloesung1.Fragments.IconFragment;

/**
 * Created by vofr1011 on 15.01.2018.
 */

public class AddTrack extends AppCompatActivity {

    Track track;

    public void pressStart () {
        track = new Track();
        track.setId(DbContract.TrackTable._ID.toString());
        track.setMode(IconFragment.getMode);


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
        for (int i = 0; i < mLocationList.size(); i++) {

        }
        track.setName(editText.getText//Eingabe im Dialog durch User//);
    }

}
