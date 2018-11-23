package com.example.ateam.bring2you;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.util.Objects;


public class SettingsFragment extends Fragment {

    ThemeSharedPref themeSharedPref;

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

    }
}
