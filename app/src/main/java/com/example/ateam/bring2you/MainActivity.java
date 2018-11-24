package com.example.ateam.bring2you;


import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {


    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    ThemeSharedPref themeSharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        themeSharedPref = new ThemeSharedPref(this);

        if(themeSharedPref.loadDarkModeState()) {
            setTheme(R.style.darktheme);
        } else {
            setTheme(R.style.AppTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Toolbar set up*/
/*        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);*/

/*        Objects.requireNonNull(getSupportActionBar()).setTitle("Order");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        Fragment listFragment= new ListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,listFragment).commit();


        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        /* Den aktivitet som startas upp efter man har loggat in */
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new ListFragment()).commit();


    }



    /* Om en av knapparna på Bottom navigation baren klickas så skickas det vidare till dess fragment
    *
    * Retunerar: klickad "knapp"
    * */

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
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
                            selectedFrag = new MapFragment();
                            break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, selectedFrag).commit();

                return true;

            };


    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
/* getSupportActionBar().setTitle("Name")
 * getSupportActionBar().setDisplayHomeAsUpEnabled(true)
 */