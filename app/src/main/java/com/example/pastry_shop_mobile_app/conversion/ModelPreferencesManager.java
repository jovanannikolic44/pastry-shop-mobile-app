package com.example.pastry_shop_mobile_app.conversion;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.pastry_shop_mobile_app.MainActivity;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

public class ModelPreferencesManager {
    private static SharedPreferences preferences;

    private static final String preferences_file = "appStorage";

    public static SharedPreferences getPreferences() {
        return preferences;
    }

    public static void setPreferences(SharedPreferences preferences) {
        ModelPreferencesManager.preferences = preferences;
    }

    public static void initialize(MainActivity app) {
        preferences = app.getSharedPreferences(preferences_file, Context.MODE_PRIVATE);
    }

    public static <T> void put(T object, String key){
        String json_string = new GsonBuilder().create().toJson(object);
        preferences.edit().putString(key, json_string).apply();
    }

    public static <T> T get(String key, Type classType) {
        String saved_json_string = preferences.getString(key, null);
        T object = new GsonBuilder().create().fromJson(saved_json_string, classType);
        return object;
    }

    public static void remove() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    public static boolean contains(String key) {
        boolean does_contain = preferences.contains(key);
        return does_contain;
    }

    public static void deleteKey(String key) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.apply();
    }

}
