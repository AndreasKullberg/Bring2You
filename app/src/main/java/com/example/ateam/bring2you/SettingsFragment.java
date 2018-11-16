package com.example.ateam.bring2you;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.util.Locale;
@SuppressWarnings("deprecation")
public class SettingsFragment extends Fragment {

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        return inflater.inflate(R.layout.settings_fragment, container, false);
    }

    public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Switch theme = getView().findViewById(R.id.themeSwitch);
        Switch language = getView().findViewById(R.id.languageSwitch);

        language.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){

                    Configuration config = getActivity().getBaseContext().getResources().getConfiguration();
                    Locale locale = new Locale("en");
                    Locale.setDefault(locale);
                    config.locale = locale;
                    getActivity().getBaseContext().getResources().updateConfiguration(config, getActivity().getBaseContext().getResources().getDisplayMetrics());

                }else {

                    Configuration config = getActivity().getBaseContext().getResources().getConfiguration();
                    Locale locale = new Locale("sv");
                    Locale.setDefault(locale);
                    config.locale = locale;
                    getActivity().getBaseContext().getResources().updateConfiguration(config, getActivity().getBaseContext().getResources().getDisplayMetrics());

                }
            }
        });
    }

}
