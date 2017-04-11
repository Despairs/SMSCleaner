package despairs.smscleaner.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import java.util.Map;

import despairs.smscleaner.app.ApplicationLoader;

/**
 * Created by Home on 11.04.2017.
 */

public class UserConfig {
    public static final String cfgName = "despairs.smscleaner.cfg";
    public static final int cfgMode = Context.MODE_PRIVATE;

    public static String sessionId = null;
    public static boolean rememberMe = false;
    public static String passcodeHash = null;
    public static byte[] passcodeSalt = new byte[0];
    public static boolean passcodeEnabled = false;
    public static long autoLockIn = 30;
    public static long lastPauseTime = 0;

    public static void init() {
        load();
    }

    public static void save() {
        SharedPreferences.Editor editor = ApplicationLoader.applicationContext.getSharedPreferences(cfgName, cfgMode).edit();
        editor.putString("sessionId", sessionId);
        editor.putBoolean("rememberMe", rememberMe);
        editor.putString("passcodeHash", passcodeHash);
        editor.putString("passcodeSalt", passcodeSalt.length > 0 ? Base64.encodeToString(passcodeSalt, Base64.DEFAULT) : "");
        editor.putBoolean("passcodeEnabled", passcodeEnabled);
        editor.putString("lastPauseTime", String.valueOf(lastPauseTime));
        editor.putString("autoLockIn", String.valueOf(autoLockIn));
        editor.commit();
    }

    public static void load() {
        SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences(cfgName, cfgMode);
        sessionId = preferences.getString("sessionId", null);
        rememberMe = preferences.getBoolean("rememberMe", false);
        passcodeHash = preferences.getString("passcodeHash", "");
        passcodeEnabled = preferences.getBoolean("passcodeEnabled", false);
//        user = JsonHelper.parseJson(preferences.getString("user", null), User.class);
        String passcodeSaltString = preferences.getString("passcodeSalt", "");
        if (passcodeSaltString.length() > 0) {
            passcodeSalt = Base64.decode(passcodeSaltString, Base64.DEFAULT);
        } else {
            passcodeSalt = new byte[0];
        }
        lastPauseTime = Long.parseLong(preferences.getString("lastPauseTime", "0"));
        autoLockIn = Long.parseLong(preferences.getString("autoLockIn", "30"));
        printAll(preferences);
    }

    public static void clearAll() {
        sessionId = null;
        rememberMe = false;
        passcodeHash = null;
        passcodeSalt = new byte[0];
        lastPauseTime = 0;
        autoLockIn = 0;
        save();
    }

    public static void forceClear() {
        ApplicationLoader.applicationContext.getSharedPreferences(cfgName, cfgMode).edit().clear().commit();
    }


    public static void printAll(SharedPreferences preferences) {
        for (Map.Entry e : preferences.getAll().entrySet()) {
            Log.d("BAZAR", e.getKey() + " = " + e.getValue());
        }
    }


}
