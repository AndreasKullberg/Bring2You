package se.iths.ateam.bring2you.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

import se.iths.ateam.bring2you.Fragments.ListFragment;
import se.iths.ateam.bring2you.R;
import se.iths.ateam.bring2you.Fragments.SettingsFragment;
import se.iths.ateam.bring2you.Fragments.SignFragment;
import se.iths.ateam.bring2you.Utils.SettingsSharedPref;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity {

    String scanResult;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private SettingsSharedPref settingsSharedPref;
    SharedPreferences sharedPreferences;
    BottomNavigationView bottomNav;
    private View v;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        settingsSharedPref = new SettingsSharedPref(this);
        progressBar = findViewById(R.id.main_proggress_bar);

        if(settingsSharedPref.loadDarkModeState()) {
            setTheme(R.style.darktheme);
        } else {
            setTheme(R.style.AppTheme);
        }

        setLanguageForApp(settingsSharedPref.loadLanguage());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ScannerFilter();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        openList();

        findViewById(R.id.nav_add).setOnClickListener(view -> Scan());
        findViewById(R.id.nav_maps).setOnClickListener(v -> maps());


        bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

    }

    public static void startLoading(ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
    }
    public static void stopLoading(ProgressBar progressBar) {
        progressBar.setVisibility(View.GONE);
    }


    private void openList() {
        if(getIntent().getStringExtra("scanResult") == null) {
            Fragment listFragment = new ListFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, listFragment).commit();
        }
    }

    private void ScannerFilter() {
        if (getIntent().getStringExtra("scanResult") != null){

            Fragment signfragment = new SignFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            SignFragment.setScanResult(getIntent().getStringExtra("scanResult"));
            fragmentTransaction.replace(R.id.frameLayout,signfragment).commit();
            stopLoading(progressBar);
            v = findViewById(R.id.frameLayout);
            v.setVisibility(View.INVISIBLE);
            slideUp(v);


        }
    }
    public void slideUp(View view){
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(700);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }
    public void slideDown(View view){
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(700);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    private void Scan() {
        Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
        startActivity(intent);
    }
    private void maps(){
        Intent intent = new Intent(MainActivity.this,MapsNavbarActivity.class );
        startActivity(intent);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selectedFrag = null;
                switch (item.getItemId()){
                    case R.id.nav_assignment:
                        selectedFrag = new ListFragment();
                        break;
                    case R.id.nav_settings:
                        selectedFrag = new SettingsFragment();
                        break;
                    /*case R.id.nav_maps:
                        startActivity(new Intent(MainActivity.this,MapsNavbarActivity.class));
                        return true;*/

                    case R.id.nav_signout:
                        FirebaseAuth.getInstance().signOut();
                        toastMessage(getString(R.string.signing_out));
                        finish();
                        startActivity(new Intent(MainActivity.this,LoginActivity.class));
                        return true;
                }
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout, selectedFrag)
                            .addToBackStack(null)
                            .commit();
                return true;
                };


    public void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack(null, 1);
            if (bottomNav.getSelectedItemId() != R.id.nav_assignment){
                bottomNav.setSelectedItemId(R.id.nav_assignment);
            }
        } else {
            super.onBackPressed();
        }
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
