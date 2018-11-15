package com.example.ateam.bring2you;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private static final int ERROR_DIALOG_REQUEST = 123;
    private float DEFAULT_ZOOM = 15;
    private String ORDER_INFORMATION_TITLE = "Leverans nr: 001";
    private String ORDER_INFORMATION = "TESTING TESTING TEST";

    private double ITHS_LAT = 57.679690;
    private double ITHS_LNG = 12.000870;

    private GoogleApiClient mLocationClient;

    private static final String TAG = "MapActivity";

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Boolean mLocationPermissionGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        initMap();
    }

    private void initMap() {
        Log.d(TAG, "@initMap::: initializing the map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private void deviceLocation() {
        Log.d(TAG, "@deviceLocation::: getting the current location of the user");
        //TODO !!!Priority!!!
        //mMap.setMyLocationEnabled(true);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        //Swedens central point
        LatLng SwedenCenterPoint = new LatLng(62.3833318, 16.2824905);

        // Fågelvägen mellan IT-Högskolan och Göteborg
        LatLng ITHS = new LatLng(57.679690, 12.000870);
        LatLng GBG = new LatLng(57.708870, 11.974560);
        // Add polylines and polygons to the map. This section shows just
        // a single polyline. Read the rest of the tutorial to learn more.
        Polyline polyline = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .color(0xffb71c1c) //TODO Byta färg för att passa vårt valda tema
                .add(
                        ITHS,
                        GBG));

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SwedenCenterPoint, 4));
    }

    //TODO Buttons? Information on marker?
    public void TEMP_BUTTON_FOR_RESET_LOCATION(View view) {
        LatLng SwedenCenterPoint = new LatLng(62.3833318, 16.2824905);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SwedenCenterPoint, 4));
    }

    public void TEMP_BUTTON_FOR_ZOOM_IN(View view) {
        DEFAULT_ZOOM++;
    }

    public void TEMP_BUTTON_FOR_ZOOM_OUT(View view) {
        DEFAULT_ZOOM--;
    }




    private void gotoLocation(double latitude, double longitude, float zoomSpeed) {
        LatLng newLocation = new LatLng(latitude, longitude);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(newLocation, zoomSpeed);
        mMap.addMarker(new MarkerOptions()
                .position(newLocation)
                .title(ORDER_INFORMATION_TITLE)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
                .snippet(ORDER_INFORMATION)


        );
        mMap.moveCamera(update);
    }

    public void geoLocate(View view) throws IOException {
        EditText editText = (EditText) findViewById(R.id.edit_address);
        String location = editText.getText().toString();

        Geocoder geocoder = new Geocoder(MapsActivity.this);
        List<Address> list = geocoder.getFromLocationName(location, 1);
        // Give me the first item off the list
        Address add = list.get(0);
        // Gives me googles locality of my input location
        String locality = add.getLocality();
        if(locality == null) {
            Toast.makeText(this, "Destination: " + location, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Destination: " + locality, Toast.LENGTH_LONG).show();
        }
        // Simple toast that displays the locality



        double latitude = add.getLatitude();
        double longitude = add.getLongitude();


        gotoLocation(latitude, longitude, DEFAULT_ZOOM);
    }

    //TODO Add a settings icon
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        /*
        switch(itemId) {
            case R.id.mapTypeNone;
                mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
            case R.id.mapTypeNormal;
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.mapTypeSatellite;
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.mapTypeTerrain;
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.mapTypeHybrid;
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;

        }*/
        return super.onOptionsItemSelected(item);
    }




// TODO För att använda location listener i min onCreate
// så var jag tvungen att overridea alla dessa, vad gör jag med dem?

    @Override
    public void onConnected(Bundle bundle) {
        // When connect to servic
        Toast.makeText(this, "Ready to map!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        // When connection stopped
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // Connection failed
    }
}


/**
 * Temporarily removed code snippets
 */
/*
    public void showCurrentLocation(MenuItem item) {
        Location currentLocation = LocationServices.FusedLocationApi.getLastLocation(mLocationClient);
        if(currentLocation == null) {
            Toast.makeText(this, "You can not be displayed", Toast.LENGTH_SHORT).show();
        } else {
            LatLng latLng = new LatLng(
                    currentLocation.getLatitude(),
                    currentLocation.getLongitude());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM);
            mMap.animateCamera(update);
        }
    }
    */

/*
    private boolean checkPlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        ERROR_DIALOG_REQUEST).show();
            }
            return false;
        }
        return true;
    }

    private boolean initMap() {
        if (mMap == null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
        return (mMap != null);
    }
*/

/*
        if (checkPlayServices()) {
            setContentView(R.layout.activity_maps);
            if (initMap()) {
                gotoLocation(ITHS_LAT, ITHS_LNG, DEFAULT_ZOOM);

                mLocationClient = new GoogleApiClient.Builder(this)
                        .addApi(LocationServices.API)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .build();

                mLocationClient.connect();

            } else {
                //TODO Varför kommer denna fram? Titta massa på permissions men funkar inte?
                //Toast.makeText(this, "Denied access", Toast.LENGTH_SHORT).show();
            }
        } else {
            setContentView(R.layout.activity_main);
        }
 */