package test.com.example.rich1016.musterloesung1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vofr1011 on 20.01.2018.
 */

public class TrackData {
    private List<Track> trackList;
    private static TrackData instance;

    private TrackData () {
        trackList = new ArrayList<Track>();
    }

    public List<Track> getTrackList() {
        return trackList;
    }

    public void addTrackToList(Track track) {
        trackList.add(track);
    }

    public static TrackData getInstance() {
        if (TrackData.instance == null) {
            TrackData.instance = new TrackData();
        }
        return TrackData.instance;
    }


}
