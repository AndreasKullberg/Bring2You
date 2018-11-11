package com.example.ateam.bring2you;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.MarkerOptions;

// TODO Permissions
import java.io.IOException;
import java.security.Permission;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private int defaultZoom = 15;
    //private static final String TAG = "MapsActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // TODO : Byta kartan, mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);


        /**
         * Added a new marker on IT- Högskolan
         * LatLng(Latitude,Longitude)


        LatLng iths = new LatLng(57.679690, 12.000870);
        LatLng delivery1 = new LatLng(57.679690, 12.001870);
        LatLng delivery2 = new LatLng(57.679790, 12.002870);
        LatLng delivery3 = new LatLng(57.679890, 12.000870);

        mMap.addMarker(new MarkerOptions().position(iths).title("IT-Högskolan"));
        mMap.addMarker(new MarkerOptions().position(delivery1).title("1"));
        mMap.addMarker(new MarkerOptions().position(delivery2).title("2"));
        mMap.addMarker(new MarkerOptions().position(delivery3).title("3"));


        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        /**
         * Will zoom to the chosen location
         * */
        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(iths, 18), 5000, null);
    }

    private void gotoLocation(double latitude, double longitude, float zoomSpeed) {
        LatLng newLocation = new LatLng(latitude,longitude);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(newLocation, zoomSpeed);


        mMap.addMarker(new MarkerOptions().position(newLocation).title("address new location:"));
        mMap.moveCamera(update);
    }

    public void geoLocate(View view) throws IOException {
        EditText editText = (EditText)findViewById(R.id.edit_address);
        String location = editText.getText().toString();

        Geocoder geocoder = new Geocoder(MapsActivity.this);
        List<Address> list = geocoder.getFromLocationName(location, 1);
        // Give me the first item off the list
        Address add = list.get(0);
        String locality = add.getLocality();

        Toast.makeText(this, locality, Toast.LENGTH_LONG).show();

        double latitude = add.getLatitude();
        double longitude = add.getLongitude();

        gotoLocation(latitude,longitude, defaultZoom);
    }

}

/**
 * Användbara länkar:
 * <p>
 * Find coordinates -
 * https://www.latlong.net
 * <p>
 * Google maps with zoom animation -
 * www.youtube.com/watch?v=qXI87-uPNnk
 * <p>
 * Geocoding tutorial -
 * https://developers.google.com/maps/documentation/geocoding/intro#place-id
 * <p>
 * Google maps - playlist
 * https://www.youtube.com/watch?v=OknMZUnTyds&list=PLgCYzUzKIBE-vInwQhGSdnbyJ62nixHCt
 */


/** https://developers.google.com/maps/documentation/geocoding/start
 * Geocoding is the process of converting addresses (like a street address) into geographic coordinates (like latitude and longitude),
 * which you can use to place markers on a map, or position the map.
 */

/**
 * https://developers.google.com/maps/documentation/geocoding/intro#place-id
 * What is Geocoding?
 */

/**
 * Is reverse geocording needed?
 */