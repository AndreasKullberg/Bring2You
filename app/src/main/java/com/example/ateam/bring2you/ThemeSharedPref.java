package com.example.ateam.bring2you;

import android.content.Context;
import android.content.SharedPreferences;

class ThemeSharedPref {

    private final SharedPreferences sharedPreferences;

    ThemeSharedPref(Context context) {
        sharedPreferences = context.getSharedPreferences("filename",Context.MODE_PRIVATE);
    }

    //To save the night mode state as true or false.
    void setDarkModeState(Boolean state) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("NightMode",state);
        editor.apply();
    }

    //To load the night or day mode.
    Boolean loadDarkModeState(){
        return sharedPreferences.getBoolean("NightMode",false);
    }
}