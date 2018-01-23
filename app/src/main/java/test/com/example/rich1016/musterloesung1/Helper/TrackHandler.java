package test.com.example.rich1016.musterloesung1.Helper;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import test.com.example.rich1016.musterloesung1.DbHelper;

/**
 * Created by rich1016 on 30.11.2017.
 */

public class TrackHandler {

    private static GoogleMap mMap;
    private Location mLocation;
    private ArrayList<Location> mLocationList;
    private Context mContext;
    private Polyline polyline;
    private static TrackHandler instance;
    private ArrayList<Long> timestampArray = new ArrayList<>();

    public ArrayList<Location> getmLocationList() {
        return mLocationList;
    }

    public static TrackHandler getInstance(Context context) {
        if (instance == null) {
            instance = new TrackHandler(context, mMap);
        }
        return instance;
    }

    public TrackHandler(Context context, GoogleMap map) {
        mContext = context;
        mMap = map;
        mLocationList = new ArrayList<>();
    }

    private LatLng convertToLatLng (Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        LatLng latLng = new LatLng(lat, lng);
        return latLng;
    }

    /*double dist;

    public double calculateLength () {
        for (int i = 0; i < mLocationList.size() - 1; i++){
            LatLng latest = convertToLatLng(mLocationList.get(i));
            LatLng next = convertToLatLng(mLocationList.get(i+1));
            dist = dist +  Location

//            dist = dist + (double) lastLoc.distanceTo(nextLoc);
        }
        return dist;
    }*/

    public void draw(Location location) {

//        dist = 0.0;

        mLocationList.add(location);
        double lat;
        double lon;
        PolylineOptions options = new PolylineOptions().width(12).color(Color.CYAN);
        for (int i = 0; i<mLocationList.size(); i++) {
            Location tempLocation = mLocationList.get(i);
            lat = tempLocation.getLatitude();
            lon = tempLocation.getLongitude();
            LatLng latlng = new LatLng(lat, lon);
            options.add(latlng);
            timestampArray.add(System.currentTimeMillis());
        }
        polyline = mMap.addPolyline(options);
    }

    public ArrayList<Long> getTimestampArray() {
        return timestampArray;
    }



    public void stopDraw(){
        mLocationList.clear();

    }
}
