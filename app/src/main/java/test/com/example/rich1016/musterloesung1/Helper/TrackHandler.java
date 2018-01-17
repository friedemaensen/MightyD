package test.com.example.rich1016.musterloesung1.Helper;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

/**
 * Created by rich1016 on 30.11.2017.
 */

public class TrackHandler {

    private GoogleMap mMap;
    private Location mLocation;
    private ArrayList<Location> mLocationList;
    private Context mContext;
    private Polyline polyline;

    public TrackHandler(Context context, GoogleMap map) {
        mContext = context;
        mMap = map;
        mLocationList = new ArrayList<>();

    }

    public void draw(Location location) {


        mLocationList.add(location);
        double lat;
        double lon;
        PolylineOptions options = new PolylineOptions().width(12).color(Color.CYAN);
        for (int i = 0; i < mLocationList.size(); i++) {
            Location tempLocation = mLocationList.get(i);

            lat = tempLocation.getLatitude();
            lon = tempLocation.getLongitude();
            LatLng latlng = new LatLng(lat, lon);
            options.add(latlng);
        }

        polyline = mMap.addPolyline(options);


    }

    public void stopDraw(){
        mLocationList.clear();

    }
}
