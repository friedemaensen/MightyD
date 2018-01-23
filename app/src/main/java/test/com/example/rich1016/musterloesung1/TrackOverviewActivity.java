package test.com.example.rich1016.musterloesung1;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class TrackOverviewActivity extends AppCompatActivity {

    private TrackListViewAdapter trackListViewAdapter;
    String pos = "pos";


    @Override
    protected void onResume() {
        super.onResume();

        ListView listView = (ListView) findViewById(R.id.lvTrackList);
        listView.setAdapter(trackListViewAdapter);
        trackListViewAdapter.notifyDataSetChanged();

        Log.i("DATA", String.valueOf(DbHelper.getInstance(TrackOverviewActivity.this).getNumberOfRows()));
        Log.i("DATA", String.valueOf(DbHelper.getInstance(TrackOverviewActivity.this).getLatestName()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_overview);

        DbHelper.getInstance(TrackOverviewActivity.this).getTrackDB();
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        trackListViewAdapter = new TrackListViewAdapter(this,TrackData.getInstance().getTrackList());
        ListView listView = (ListView) findViewById(R.id.lvTrackList);
        listView.setAdapter(trackListViewAdapter);
        listView.setOnItemClickListener(new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TrackOverviewActivity.this, TrackDetailActivity.class);
                intent.putExtra(pos, position);
                startActivity(intent);
            }
            });
    }
}
