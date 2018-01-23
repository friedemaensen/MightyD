package test.com.example.rich1016.musterloesung1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class TrackDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_detail);

        int pos = getIntent().getExtras().getInt("pos");
        List<Track> trackList = TrackData.getInstance().getTrackList();
        Track track = trackList.get(pos);

        TextView textId = (TextView) findViewById(R.id.tvId);
        TextView textName = (TextView) findViewById(R.id.tvName);
        TextView textDate = (TextView) findViewById(R.id.tvDate);
        TextView textDuration = (TextView) findViewById(R.id.tvDuration);
        TextView textLength = (TextView) findViewById(R.id.tvLength);

        textId.setText(String.valueOf(track.getId()));
        textName.setText(track.getName());
        textDate.setText(track.getDate());
        textDuration.setText(track.getDuration());
        textLength.setText(Double.toString(track.getLength()));



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
