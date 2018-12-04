
package se.iths.ateam.bring2you.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import se.iths.ateam.bring2you.R;

public class SplashActivity extends AppCompatActivity {


    private static int time=2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {


                Intent in = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(in);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);

                finish();
            }
        }, time);
    }
}

