package test.com.example.rich1016.musterloesung1;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;

import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

import test.com.example.rich1016.musterloesung1.Fragments.IconFragment;
import test.com.example.rich1016.musterloesung1.Fragments.NameFragment;
import test.com.example.rich1016.musterloesung1.Helper.TrackHandler;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, IconFragment.OnFragmentInteractionListener, NameFragment.OnFragmentInteractionListener {


    private GoogleMap mMap;
    public static final String TAG = MainActivity.class.getSimpleName();

    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 123;

    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationClient;
    private UiSettings uiSettings;
    private Location mCurrentLocation;
    private LocationCallback mLocationCallback;
    private boolean mRequestingLocationUpdates;
    private boolean isTracking = false;
    private TrackHandler mTrackHandler;
    FloatingActionButton buttonModeIcon;
    Track track;
    long startTime;
    long calcDuration;

    private LatLng startLoc;
    private LatLng stopLoc;

    public void startTracking() {
        track = new Track();
        //track.setId(DbHelper.getInstance(MainActivity.this).getMaxID() + 1);
        track.setId(DbHelper.getInstance(MainActivity.this).getNumberOfRows() + 1);

        startTime = System.currentTimeMillis();
        track.setDate(Long.toString(startTime));
        createLocationRequest();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

    }

    public void stopTracking() {
        if (System.currentTimeMillis()>startTime) {
            calcDuration = System.currentTimeMillis() - startTime;
        }
        track.setDuration(Long.toString(calcDuration));
        ArrayList<Location> locations = TrackHandler.getInstance(MainActivity.this).getmLocationList();
        track.setLength(TrackHandler.getInstance(MainActivity.this).calculateLength(locations));
        //TODO get name from NameFragment
        //TODO save on NameFragment.saveButton



        Log.i("DATEN", DbHelper.getInstance(MainActivity.this).getLatestName());
        Log.i("DATEN", Integer.toString(DbHelper.getInstance(MainActivity.this).getNumberOfRows()));
    }


    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        buttonModeIcon = (FloatingActionButton) findViewById(R.id.button_modeIcon);
        buttonModeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IconFragment fragment = new IconFragment();
                fragment.show(getSupportFragmentManager(), "");

            }
        });



        final FloatingActionButton buttonTrack = (FloatingActionButton) findViewById(R.id.button_track);
        buttonTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isTracking) {
                    buttonTrack.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.cast_ic_mini_controller_stop));
                    isTracking = true;
                    //mMap.clear();
                    startTracking();
                } else {
                    buttonTrack.setImageDrawable(ContextCompat.getDrawable(MainActivity.this,R.drawable.ic_add_black_24dp));
                    NameFragment nameFragment = new NameFragment();
                    nameFragment.show(getSupportFragmentManager(), "");

                    stopTracking();

                    isTracking = false;
                    mTrackHandler.stopDraw();

                }
            }
        });





        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (isGooglePlayServicesAvailable(this)) {

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        } else {
            finish();

        }

        LocationManager manager = (LocationManager) getSystemService(this.LOCATION_SERVICE);

        if ( ! manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            showGpsConfirmationDialog();
        }


        createLocationRequest();







        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));
                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(new LatLng(location.getLatitude(), location.getLongitude()))
                                .zoom(17)
                                .bearing(0)
                                .build();
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        if (isTracking) {
                            mTrackHandler.draw(location);

                        }
                    }

                }
            }
        };
        startLocationUpdates();

        //Hier wird ListView erstellt

        /*final ListView listView = (ListView) findViewById(R.id.listView);
        userListViewAdapter = new UserListViewAdapter(this, UserData.getInstance().getUserList());
        listView.setAdapter(userListViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, AusgabeActivity.class);

                intent.putExtra(KEY, position);
                startActivity(intent);
            }
        });*/
    }


    @Override
    protected void onStart() {
        super.onStart();
        moveToLastLocation();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopLocationUpdates();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Hier wird Listview aufgerufen
       /* ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(userListViewAdapter);
        userListViewAdapter.notifyDataSetChanged();*/
        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    // Asking for Permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Start Location updates
                    Log.d(TAG, "Permission Granted");
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    } if (mMap != null) {
                        mMap.setMyLocationEnabled(true);
                    }

                } else {
                    Log.d(TAG, "Permission denied");
                    // Show an explanation to the user *asynchronously*
                    AlertDialog.Builder ADbuilder = new AlertDialog.Builder(this);
                    ADbuilder.setMessage("This permission is important for the App to function properly.")
                            .setTitle("Important permission required")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_FINE_LOCATION);
                                }
                            });
                    ADbuilder.create();
                    ADbuilder.show();
                }
            }
        }
    }

    public boolean isGooglePlayServicesAvailable(Activity activity) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(activity);
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(activity, status, 2404).show();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. 
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            return;
        }
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        uiSettings = mMap.getUiSettings();

        //setMyLocationEnabled only works, if permission is given

        uiSettings.setMyLocationButtonEnabled(true);

        mTrackHandler = new TrackHandler(this, mMap);




        LatLng karlsruhe = new LatLng(49.008085, 8.403756);
        mMap.addMarker(new MarkerOptions().position(karlsruhe).title("Marker in Karlsruhe"));


    }

    //Used for getting the Location at startup of App

    private void moveToLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(new LatLng(location.getLatitude(), location.getLongitude()))
                            .zoom(17)
                            .bearing(0)
                            .build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                }
            }
        });
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        // start other Activities via Intents and pass Data if needed
        int id = item.getItemId();

         if (id == R.id.nav_last_tracks) {
             Intent intent = new Intent(MainActivity.this, TrackOverviewActivity.class);
             startActivity(intent);

        }  else if (id == R.id.nav_imprint) {


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(3000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                mLocationCallback,
                getMainLooper());
        mRequestingLocationUpdates = true;
    }


    @Override
    public void onFragmentInteraction(Drawable background) {

        buttonModeIcon.setImageDrawable(background);


    }

    private void showGpsConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        builder.setMessage(R.string.gpsConfirmation_message)
                .setCancelable(false)
                .setPositiveButton(R.string.gpsConfirmation_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton(R.string.gpsConfirmation_no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void stopLocationUpdates(){
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    @Override
    public void onFragmentInteraction(String trackname) {
        track.setName(trackname);
        DbHelper.getInstance(MainActivity.this).saveTrackToDB(track);
        Log.i("DATEN", DbHelper.getInstance(MainActivity.this).getLatestName());
        Log.i("DATEN", Integer.toString(DbHelper.getInstance(MainActivity.this).getNumberOfRows()));
    }
}






