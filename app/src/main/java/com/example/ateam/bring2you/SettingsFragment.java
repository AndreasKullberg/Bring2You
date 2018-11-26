package com.example.ateam.bring2you;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import java.util.Locale;
import java.util.Objects;

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

        themeSharedPref = new ThemeSharedPref(Objects.requireNonNull(getActivity()));

        Switch themeSwitch = Objects.requireNonNull(getView()).findViewById(R.id.theme_pref);
        if (themeSharedPref.loadDarkModeState()){
            themeSwitch.setChecked(true);
        }

        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                themeSharedPref.setDarkModeState(true);
                getActivity().recreate();
            }else{
                themeSharedPref.setDarkModeState(false);
                getActivity().recreate();
            }
        });

        Button english = getView().findViewById(R.id.en_btn);
        Button swedish = getView().findViewById(R.id.se_btn);

        english.setOnClickListener(v -> changeLanguage("en"));

        swedish.setOnClickListener(v -> changeLanguage("sv"));

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
}

//TODO: Fix sharedpreferences for when application is ended. Currently all settings that is stored are lost when user closes app
//TODO: Fix if user chooses same language with if else (TOAST warning?)