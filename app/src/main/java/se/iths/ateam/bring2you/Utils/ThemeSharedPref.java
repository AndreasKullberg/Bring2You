package se.iths.ateam.bring2you.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class ThemeSharedPref {

    private final SharedPreferences sharedPreferences;

    public ThemeSharedPref(Context context) {
        sharedPreferences = context.getSharedPreferences("filename",Context.MODE_PRIVATE);
    }

    //To save the night mode state as true or false.
    public void setDarkModeState(Boolean state) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("NightMode",state);
        editor.apply();
    }

    //To load the night or day mode.
    public Boolean loadDarkModeState(){
        return sharedPreferences.getBoolean("NightMode",false);
    }
}