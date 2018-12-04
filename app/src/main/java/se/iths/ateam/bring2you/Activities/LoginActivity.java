package se.iths.ateam.bring2you.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Objects;

import se.iths.ateam.bring2you.R;
import se.iths.ateam.bring2you.Utils.ThemeSharedPref;

public class LoginActivity extends AppCompatActivity {

    // TODO Ifall Man får fel om att API nyckeln är expired eller invalid,
    // TODO så måste api nyckeln bytas i Json filen, mot någon av  nycklarna som ligger här: https://console.developers.google.com/apis/credentials?project=bring2you-da7a0

    private static final String TAG = "LoginActivity";

    private FirebaseAuth mAuth;
    private EditText mUsername, mPassword;
    private Button loginButton;
    private ProgressBar progressBar;
    private CheckBox rememberMeCheckBox;
    private SharedPreferences mPrefs;
    private static final String PREFS_NAME = "PrefsFile";
    private boolean swap = true;
    private FirebaseUser user;
    ThemeSharedPref themeSharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Sätter upp temat beroende på vad som är valt i settings menyn
        themeSharedPref = new ThemeSharedPref(this);

        if (themeSharedPref.loadDarkModeState()) {
            setTheme(R.style.darktheme);
        } else {
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);


        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if (user != null) {
            toastMessage("logged in as: " + user.getEmail());
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        } else {

            findViews();
            getPreferencesData();

            loginButton.setOnClickListener(v -> {
                loginButton.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                if (rememberMeCheckBox.isChecked()) {
                    Boolean boolIsChecked = rememberMeCheckBox.isChecked();
                    SharedPreferences.Editor editor = mPrefs.edit();
                    editor.putString("pref_name", mUsername.getText().toString());
                    editor.putString("pref_password", mPassword.getText().toString());
                    editor.putBoolean("pref_check", boolIsChecked);
                    editor.apply();

                } else {
                    mPrefs.edit().clear().apply();
                }

                if (mUsername.getText().toString().equals("") && mPassword.getText().toString().equals("")) {
                    mUsername.setError("No blank fields");
                    mPassword.setError("No blank fields!");
                    loginButton.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                } else if (mUsername.getText().toString().equals("")) {
                    loginButton.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    mUsername.setError("No blank fields!");

                } else if (mPassword.getText().toString().equals("")) {
                    loginButton.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    mPassword.setError("No blank fields!");
                } else
                    mAuth.signInWithEmailAndPassword(mUsername.getText().toString(), mPassword.getText().toString())
                            .addOnCompleteListener(task -> {
                                FirebaseUser user = mAuth.getCurrentUser();
                                progressBar.setVisibility(View.INVISIBLE);
                                loginButton.setVisibility(View.VISIBLE);

                                if (task.isSuccessful()) {
                                    toastMessage("Successfully logged in as: " + Objects.requireNonNull(user).getEmail());
                                    startActivity(new Intent(LoginActivity.this, SplashActivity.class));
                                } else {
                                    toastMessage("Failure login in..");
                                }
                            });

            });

        }

    }

    private void getPreferencesData(){
        SharedPreferences sp = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if (sp.contains("pref_name")) {
            String u = sp.getString("pref_name", "not found");
            mUsername.setText(u);
        }

        if (sp.contains("pref_password")) {
            String p = sp.getString("pref_password", "not found");
            mPassword.setText(p);
        }
        if (sp.contains("pref_check")) {
            Boolean b = sp.getBoolean("pref_check", false);
            rememberMeCheckBox.setChecked(b);
        }
    }
    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

    }
    private void findViews(){
        mUsername = findViewById(R.id.usernameEditText);
        mPassword = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        rememberMeCheckBox = findViewById(R.id.rememberMeChk);
    }
}