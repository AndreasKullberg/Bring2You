package se.iths.ateam.bring2you.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
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
import java.util.Locale;

import se.iths.ateam.bring2you.R;
import se.iths.ateam.bring2you.Utils.ListItemInfo;
import se.iths.ateam.bring2you.Utils.SettingsSharedPref;


@SuppressWarnings("deprecation")
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private float zoom = 17;
    ListItemInfo listItemInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SettingsSharedPref settingsSharedPref = new SettingsSharedPref(this);

        if(settingsSharedPref.loadDarkModeState()) {
            setTheme(R.style.darktheme);
        } else {
            setTheme(R.style.AppTheme);
        }

        setLanguageForApp(settingsSharedPref.loadLanguage());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Intent i = getIntent();
        listItemInfo = (ListItemInfo) i.getSerializableExtra("mapKey");
        initMap();
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        LatLng SwedenCenterPoint = new LatLng(62.3833318, 16.2824905);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SwedenCenterPoint, 4));
        receiveCoordinates();
    }

    private void gotoLocation(double latitude, double longitude, float ZoomValue) {
        LatLng newLocation = new LatLng(latitude, longitude);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(newLocation, ZoomValue);
        mMap.addMarker(new MarkerOptions()
                .position(newLocation)
                .title(listItemInfo.getAdress() + ", " + listItemInfo.getPostalCode())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
                .snippet(listItemInfo.getName() + " " + "ID: " + listItemInfo.getSenderId())
        );
        mMap.moveCamera(update);
    }

    public void receiveCoordinates() {
        String location = listItemInfo.getAdress();
        Geocoder geocoder = new Geocoder(MapsActivity.this);
        List<Address> list = null;

        try {
            list = geocoder.getFromLocationName(location, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (list.size() > 0) {
            Address add = list.get(0);
            String locality = add.getLocality();
            if (locality == null) {
                Toast.makeText(this, getString(R.string.destination) + " " + location, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.destination) + " " + locality, Toast.LENGTH_LONG).show();
            }
            double latitude = add.getLatitude();
            double longitude = add.getLongitude();

            gotoLocation(latitude, longitude, zoom);
        } else {
            Toast.makeText(this,getString(R.string.dest_not_found), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(this, getString(R.string.ready_to_map), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    private void setLanguageForApp(String languageToLoad){
        Locale locale;
        if(languageToLoad.equals("")){
            locale = Locale.getDefault();
        }
        else {
            locale = new Locale(languageToLoad);
        }
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }
}


