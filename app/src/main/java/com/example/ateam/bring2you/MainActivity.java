package com.example.ateam.bring2you;


import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {


    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



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
                /*    case R.id.nav_add:
                            selectedFrag = new  ();
                               break; */
                    case R.id.nav_maps:
                            selectedFrag = new MapFragment();
                            break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, selectedFrag).commit();

                return true;

            };

}
