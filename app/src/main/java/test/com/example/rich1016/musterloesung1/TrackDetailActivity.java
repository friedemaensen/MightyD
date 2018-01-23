package test.com.example.rich1016.musterloesung1;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class TrackDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_detail);

        int pos = getIntent().getExtras().getInt("pos");
        List<Track> trackList = TrackData.getInstance().getTrackList();
        final Track track = trackList.get(pos);

        TextView textId = (TextView) findViewById(R.id.tvId);
        TextView textName = (TextView) findViewById(R.id.tvName);
        TextView textDate = (TextView) findViewById(R.id.tvDate);
        TextView textDuration = (TextView) findViewById(R.id.tvDuration);
        TextView textLength = (TextView) findViewById(R.id.tvLength);

        String duration = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(Long.valueOf(track.getDuration())),
                TimeUnit.MILLISECONDS.toSeconds(Long.valueOf(track.getDuration()))
        );

        long millis = Long.valueOf(track.getDate());

        Date date = new Date(millis);

        String length = track.getLength() + " m";

        textId.setText(String.valueOf(track.getId()));
        textName.setText(track.getName());
        textDate.setText(DateFormat.getDateTimeInstance().format(date));
        textDuration.setText(duration);
        textLength.setText(length);

        Button showOnMap = (Button) findViewById(R.id.btnShowOnMap);
        showOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Location> locations = DbHelper.getInstance(TrackDetailActivity.this).getLocationsFromDB(track);
                Toast.makeText(TrackDetailActivity.this, "Hier sollte sich die Karte wieder öffnen und eine Polyline erscheinen. \nAnzahl der gelesenen Koordinaten: " + String.valueOf(locations.size()) +
                        "\nID des Tracks: " + String.valueOf(track.getId()), Toast.LENGTH_LONG).show();
                Log.i("TRACK-ID", String.valueOf(track.getId()));
                Log.i("RETURN_ARRAY_SIZE", String.valueOf(locations.size()));

//                Bundle bundle = new Bundle();
//                bundle.putInt("idToPolyLine", track.getId());
//                Intent intent = new Intent(TrackDetailActivity.this, MainActivity.class);
//                intent.put(bundle);
//                startActivity(intent);
            }
        });



        /*DbHelper.getInstance(TrackDetailActivity.this).getTrackDB();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        trackListViewAdapter = new TrackListViewAdapter(this, TrackData.getInstance().getTrackList());
        ListView listView = (ListView) findViewById(R.id.lvTrackList);
        listView.setAdapter(trackListViewAdapter);*/
        /* TODO implement onItemClick... for show track on map
        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TrackDetailActivity.this, TrackDetailFragment.class);
                intent.putExtra(pos, position);
                startActivity(intent);
            }
        });*/
    }

}
