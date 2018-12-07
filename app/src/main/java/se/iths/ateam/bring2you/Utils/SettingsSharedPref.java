package se.iths.ateam.bring2you.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsSharedPref {

    private final SharedPreferences sharedPreferences;

    public SettingsSharedPref(Context context) {
        sharedPreferences = context.getSharedPreferences("name", Context.MODE_PRIVATE);
    }

    public void setDarkModeState(Boolean state) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("DarkMode", state);
        editor.apply();
    }

    public Boolean loadDarkModeState() {
        return sharedPreferences.getBoolean("DarkMode", false);
    }

    public void setLanguage(String lang) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Language", lang);
        editor.apply();
    }

    public String loadLanguage() {

        return sharedPreferences.getString("Language", "");
    }
}
