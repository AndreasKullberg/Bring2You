package se.iths.ateam.bring2you.Activities;


import android.content.Context;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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
import java.util.Objects;

import se.iths.ateam.bring2you.R;
import se.iths.ateam.bring2you.Utils.SettingsSharedPref;


@SuppressWarnings("deprecation")
public class MapsNavbarActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private float zoom = 15;
    private String location;


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
        setContentView(R.layout.activity_navbar_maps);
        initMap();
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        Objects.requireNonNull(mapFragment).getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        LatLng SwedenCenterPoint = new LatLng(62.3833318, 16.2824905);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SwedenCenterPoint, 4));
    }

    private void gotoLocation(double latitude, double longitude, float ZoomValue) {
        LatLng newLocation = new LatLng(latitude, longitude);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(newLocation, ZoomValue);
        //TODO Visa markörens information?
        //TODO Ta bort androids sökfält efter man har sökt
        mMap.addMarker(new MarkerOptions()
                .position(newLocation)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
                .title(location)
        );
        mMap.moveCamera(update);
    }

    public void geoLocate(View view) throws IOException {
        EditText editText = findViewById(R.id.edit_address);
        location = editText.getText().toString();

        // Removes soft keyboard after input
        view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        Geocoder geocoder = new Geocoder(MapsNavbarActivity.this);
        List<Address> list = geocoder.getFromLocationName(location, 1);
        Address add = list.get(0);
        String locality = add.getLocality();
        if (locality == null) {
            Toast.makeText(this, getString(R.string.destination) + " " + location, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, getString(R.string.destination) + " " + locality, Toast.LENGTH_LONG).show();
        }
        double latitude = add.getLatitude();
        double longitude = add.getLongitude();

        gotoLocation(latitude, longitude, zoom);
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




