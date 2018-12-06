package se.iths.ateam.bring2you.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Locale;
import java.util.Objects;

import se.iths.ateam.bring2you.Activities.MainActivity;
import se.iths.ateam.bring2you.R;
import se.iths.ateam.bring2you.Utils.ThemeSharedPref;

@SuppressWarnings( "deprecation" )
public class SettingsFragment extends Fragment {

    private ThemeSharedPref themeSharedPref;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.titleSettings);
        themeSharedPref = new ThemeSharedPref(Objects.requireNonNull(getActivity()));

        Switch themeSwitch = Objects.requireNonNull(getView()).findViewById(R.id.theme_pref);
        if (themeSharedPref.loadDarkModeState()){
            themeSwitch.setChecked(true);
        }

        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                changeTheme(true);
            }else{
                changeTheme(false);
            }
        });

        Button about = getView().findViewById(R.id.about_btn);
        Button english = getView().findViewById(R.id.en_btn);
        Button swedish = getView().findViewById(R.id.se_btn);
        Locale current = getResources().getConfiguration().locale;

        english.setOnClickListener(v -> {
            if (current.getLanguage().equals("sv")){
                changeLanguage("en");
            }else{
                toastMessage("Language is already set to English!");
            }
        });

        swedish.setOnClickListener(v -> {
            if (current.getLanguage().equals("en")){
                changeLanguage("sv");
            }else{
                toastMessage("Språket är redan inställt på svenska!");
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        getActivity());
                builder.setTitle(R.string.about);
                builder.setMessage(R.string.app_creation);
                builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setCancelable(false);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }

    private void changeLanguage(String languageToLoad){

        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        Objects.requireNonNull(getContext()).getResources().updateConfiguration(config,getContext().getResources().getDisplayMetrics());

        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        Objects.requireNonNull(getActivity()).recreate();
    }

    private void changeTheme(Boolean darkTheme){
        themeSharedPref.setDarkModeState(darkTheme);
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        Objects.requireNonNull(getActivity()).recreate();
    }

    private void toastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

}