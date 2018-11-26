package com.example.ateam.bring2you;


import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import android.support.v7.widget.Toolbar;

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
                    case R.id.nav_signout:
                        FirebaseAuth.getInstance().signOut();
                        toastMessage("Signing out..");
                        finish();
                        startActivity(new Intent(MainActivity.this,LoginActivity.class));
                        return  true;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, selectedFrag).commit();

                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, selectedFrag).commit();

                return true;

            };


    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}
