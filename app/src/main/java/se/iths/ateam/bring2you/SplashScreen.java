package se.iths.ateam.bring2you;


import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {


    private static int time=2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent in = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(in);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);

                finish();
            }
        }, time);
    }
}
