package se.iths.ateam.bring2you.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
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

import java.util.Locale;
import java.util.Objects;

import se.iths.ateam.bring2you.R;
import se.iths.ateam.bring2you.Utils.SettingsSharedPref;

@SuppressWarnings("deprecation")
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
    private FirebaseUser user;
    SettingsSharedPref settingsSharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        settingsSharedPref = new SettingsSharedPref(this);

        if (settingsSharedPref.loadDarkModeState()) {
            setTheme(R.style.darktheme);
        } else {
            setTheme(R.style.AppTheme);
        }

        setLanguageForApp(settingsSharedPref.loadLanguage());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);


        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if (user != null) {
            finish();
            toastMessage(getString(R.string.logged_in_as) + " " + Objects.requireNonNull(user).getEmail());
            startActivity(new Intent(LoginActivity.this, SplashActivity.class));
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
                    mUsername.setError(getText(R.string.no_blank_fields));
                    mPassword.setError(getText(R.string.no_blank_fields));
                    loginButton.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                } else if (mUsername.getText().toString().equals("")) {
                    loginButton.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    mUsername.setError(getText(R.string.no_blank_fields));

                } else if (mPassword.getText().toString().equals("")) {
                    loginButton.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    mPassword.setError(getText(R.string.no_blank_fields));
                } else
                    mAuth.signInWithEmailAndPassword(mUsername.getText().toString(), mPassword.getText().toString())
                            .addOnCompleteListener(task -> {
                                FirebaseUser user = mAuth.getCurrentUser();
                                progressBar.setVisibility(View.INVISIBLE);
                                loginButton.setVisibility(View.VISIBLE);

                                if (task.isSuccessful()) {
                                    finish();
                                    toastMessage(getString(R.string.login_success) + " " + Objects.requireNonNull(user).getEmail());
                                    startActivity(new Intent(LoginActivity.this, SplashActivity.class));
                                } else {
                                    toastMessage(getString(R.string.login_failed));
                                }
                            });

            });

        }

    }

    private void getPreferencesData() {
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

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

    }

    private void setLanguageForApp(String languageToLoad) {
        Locale locale;
        if (languageToLoad.equals("")) {
            locale = Locale.getDefault();
        } else {
            locale = new Locale(languageToLoad);
        }
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    private void findViews() {
        mUsername = findViewById(R.id.usernameEditText);
        mPassword = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        rememberMeCheckBox = findViewById(R.id.rememberMeChk);
    }
}