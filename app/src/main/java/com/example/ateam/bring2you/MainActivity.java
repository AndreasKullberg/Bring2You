package com.example.ateam.bring2you;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

  //  private Button signOutButton;
    private TextView currentUserSignedIn;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // currentUserSignedIn = findViewById(R.id.currentUserText);
        //currentUserSignedIn = findViewById(R.id.currentUserText);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        Fragment listFragment= new ListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,listFragment).commit();
        // on click listener for navbar button.
        findViewById(R.id.nav_add).setOnClickListener(view -> Scan());

/*        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                toastMessage("Signing out..");
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });*/


        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

    }

    private void Scan() {
        Intent intent = new Intent(this, ScannerActivity.class);
        startActivity(intent);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selectedFrag = null;

                switch (item.getItemId()) {
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


    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}
