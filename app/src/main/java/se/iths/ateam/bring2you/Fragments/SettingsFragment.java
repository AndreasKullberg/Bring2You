package se.iths.ateam.bring2you.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Locale;
import java.util.Objects;

import se.iths.ateam.bring2you.Activities.MainActivity;
import se.iths.ateam.bring2you.R;
import se.iths.ateam.bring2you.Utils.SettingsSharedPref;

@SuppressWarnings( "deprecation" )
public class SettingsFragment extends Fragment {

    private SettingsSharedPref settingsSharedPref;
    private Fragment selectedFrag;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.titleSettings);
        settingsSharedPref = new SettingsSharedPref(Objects.requireNonNull(getActivity()));

        Switch themeSwitch = Objects.requireNonNull(getView()).findViewById(R.id.theme_pref);
        if (settingsSharedPref.loadDarkModeState()){
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
        ImageButton english = getView().findViewById(R.id.en_btn);
        ImageButton swedish = getView().findViewById(R.id.se_btn);
        Locale current = getResources().getConfiguration().locale;

        english.setOnClickListener(v -> {
            if (current.getLanguage().equals("sv")){
                settingsSharedPref.setLanguage("en");
                changeLanguage("en");
                toastMessage("Language is now set to English!");
            }else{
                toastMessage("Language is already set to English!");
            }
        });

        swedish.setOnClickListener(v -> {
            if (current.getLanguage().equals("en")){
                settingsSharedPref.setLanguage("sv");
                changeLanguage("sv");
                toastMessage("Språket är nu inställt på Svenska!");
            }else{
                toastMessage("Språket är redan inställt på Svenska!");
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
        getActivity().overridePendingTransition(0,0);
        startActivity(intent);
        Objects.requireNonNull(getActivity()).recreate();
    }

    private void changeTheme(Boolean darkTheme){
        settingsSharedPref.setDarkModeState(darkTheme);
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getActivity().overridePendingTransition(0,0);
        startActivity(intent);
        Objects.requireNonNull(getActivity()).recreate();
    }

    private void toastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

}

//TODO Byta ikon efter man valt språk (Inställningar är förvalt även om man blir skickad tillbaka till Order listan)