package com.example.ateam.bring2you;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;



public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openMapsButtonClicked(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
