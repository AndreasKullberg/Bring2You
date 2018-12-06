package se.iths.ateam.bring2you.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Objects;
import se.iths.ateam.bring2you.Fragments.ListFragment;
import se.iths.ateam.bring2you.Fragments.MapFragment;
import se.iths.ateam.bring2you.R;
import se.iths.ateam.bring2you.Fragments.SettingsFragment;
import se.iths.ateam.bring2you.Fragments.SignFragment;
import se.iths.ateam.bring2you.Utils.ThemeSharedPref;


public class MainActivity extends AppCompatActivity {

    String scanResult;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private ThemeSharedPref themeSharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Sätter upp temat beroende på vad som är valt i settings menyn
        themeSharedPref = new ThemeSharedPref(this);

        if(themeSharedPref.loadDarkModeState()) {
            setTheme(R.style.darktheme);
        } else {
            setTheme(R.style.AppTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //(TextView)findViewById(R.id.toolbar_title).
        //currentUserSignedIn = findViewById(R.id.currentUserText);
        ScannerFilter();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        openList();

        // on click listener for navbar button.
        findViewById(R.id.nav_add).setOnClickListener(view -> Scan());


        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

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

        /* Den aktivitet som startas upp efter man har loggat in */
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new ListFragment()).commit();


        }
    }

    private void Scan() {
        Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
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
                    case R.id.nav_maps:
                            startActivity(new Intent(MainActivity.this,MapsNavbarActivity.class));
                            //avoid null pointer exception
                            return true;

                    case R.id.nav_signout:
                        FirebaseAuth.getInstance().signOut();
                        toastMessage("Signing out..");
                        finish();
                        startActivity(new Intent(MainActivity.this,LoginActivity.class));
                        return  true;
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
        if (getSupportFragmentManager().getBackStackEntryCount() > 0){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            super.onBackPressed();
        }
    }
}


