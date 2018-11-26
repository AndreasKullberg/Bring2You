package se.iths.ateam.bring2you;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private float DEFAULT_ZOOM = 15;
    private static final String TAG = "MapActivity";
    private FusedLocationProviderClient mFusedLocationClient;
    private Boolean mLocationPermissionGranted;
    ListItemInfo listItemInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent i = getIntent();
        listItemInfo = (ListItemInfo) i.getSerializableExtra("mapKey");

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        initMap();
    }


    private void initMap() {
        Log.d(TAG, "@initMap::: initializing the map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        //Swedens central point
        LatLng SwedenCenterPoint = new LatLng(62.3833318, 16.2824905);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SwedenCenterPoint, 4));
        recieveCoordinates();

    }

    private void gotoLocation(double latitude, double longitude, float ZoomValue) {
        LatLng newLocation = new LatLng(latitude, longitude);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(newLocation, ZoomValue);
        mMap.addMarker(new MarkerOptions()
                .position(newLocation)
                .title(listItemInfo.getAdress() + ", " + listItemInfo.getPostalCode())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
                .snippet(
                        listItemInfo.getName() + " " +
                                "ID: " + listItemInfo.getSenderId())

        );
        mMap.moveCamera(update);
    }

    public void recieveCoordinates() {
        String location = listItemInfo.getAdress();

        Geocoder geocoder = new Geocoder(MapsActivity.this);
        List<Address> list = null;
        try {
            list = geocoder.getFromLocationName(location, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Give me the first item off the list
        Address add = list.get(0);
        if(list.size() > 0) {
            String locality = add.getLocality();
            if(locality == null) {
                Toast.makeText(this, "Destination: " + location, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Destination: " + locality, Toast.LENGTH_LONG).show();
            }
            double latitude = add.getLatitude();
            double longitude = add.getLongitude();

            gotoLocation(latitude, longitude, DEFAULT_ZOOM);
        } else {
            Toast.makeText(this, "The destination was not found.", Toast.LENGTH_SHORT).show();
        }
    }

    //TODO Om nu vi ska ha map ikonen
    public void geoLocate(View view) throws IOException {

        EditText editText = (EditText) findViewById(R.id.edit_address);
        String location = editText.getText().toString();

        Geocoder geocoder = new Geocoder(MapsActivity.this);
        List<Address> list = geocoder.getFromLocationName(location, 1);
        // Give me the first item off the list
        Address add = list.get(0);
        // Gives me googles locality of my input location
/*        String locality = add.getLocality();
          if(locality == null) {
            Toast.makeText(this, "Destination: " + location, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Destination: " + locality, Toast.LENGTH_LONG).show();
        }
        // Simple toast that displays the locality
*/
        double latitude = add.getLatitude();
        double longitude = add.getLongitude();

        gotoLocation(latitude, longitude, DEFAULT_ZOOM);
    }


    //TODO Lägga till map typ meny
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.menucontext , menu);
        return true;
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

    @Override
    public void onConnected(Bundle bundle) {
        // When connecting to service
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


