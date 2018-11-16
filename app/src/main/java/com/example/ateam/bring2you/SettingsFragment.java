package com.example.ateam.bring2you;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.util.Locale;
import java.util.Objects;

@SuppressWarnings("deprecation")

public class SettingsFragment extends Fragment {

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        return inflater.inflate(R.layout.settings_fragment, container, false);
    }

    public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Switch theme = getView().findViewById(R.id.themeSwitch);
        Switch language = getView().findViewById(R.id.languageSwitch);
        language.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                Configuration config = Objects.requireNonNull(getContext()).getResources().getConfiguration();
                Locale locale = new Locale("sv");
                Locale.setDefault(locale);
                config.locale = locale;
                getContext().getResources().updateConfiguration(config, getContext().getResources().getDisplayMetrics());

            }else {

            }
        });
    }



}
