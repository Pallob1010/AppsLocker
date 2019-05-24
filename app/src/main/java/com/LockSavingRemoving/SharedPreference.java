package com.LockSavingRemoving;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.SparseBooleanArray;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPreference {

    Context context;
    PackageManager packageManager;
    SharedPreferences preferences;

    public SharedPreference() {

    }

    public SharedPreference(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE);
        if (!preferences.contains(Constants.SPARSEARRAY)) {
            createState();
        }


    }

    public void saveLockState(boolean var) {
        SharedPreferences saveLockState;
        SharedPreferences.Editor editor;
        saveLockState = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE);
        editor = saveLockState.edit();
        editor.putBoolean(Constants.LOCKED_STATE, var);
        editor.commit();
    }


    public void On() {
        saveLockState(true);
    }

    public void Off() {
        saveLockState(false);
    }

    public boolean isRunning() {
        SharedPreferences getstate;
        getstate = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE);
        if (!getstate.contains(Constants.LOCKED_STATE)) {
            Off();

        }
        return getstate.getBoolean(Constants.LOCKED_STATE, false);
    }


    public void saveLockedApp(List<String> LockedApp) {
        SharedPreferences.Editor editor;
        SharedPreferences setting;
        setting = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE);
        editor = setting.edit();
        Gson gson = new Gson();
        String jsonLocked = gson.toJson(LockedApp);
        editor.putString(Constants.LOCKED_APP_LIST, jsonLocked);
        editor.commit();
    }

    public void addAppToLockedList(String app) {
        List<String> lockedApp = getLockedListedApp();
        if (lockedApp == null) {
            lockedApp = new ArrayList<>();
        }
        lockedApp.add(app);
        saveLockedApp(lockedApp);
    }

    public void removeAppFromLockedList(String app) {
        ArrayList<String> locked = getLockedListedApp();
        if (locked != null) {
            locked.remove(app);
            saveLockedApp(locked);
        }
    }

    public ArrayList<String> getLockedListedApp() {
        SharedPreferences settings;
        List<String> locked;
        settings = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE);
        if (settings.contains(Constants.LOCKED_APP_LIST)) {
            String jsonLocked = settings.getString(Constants.LOCKED_APP_LIST, null);
            Gson gson = new Gson();
            String[] lockedItems = gson.fromJson(jsonLocked, String[].class);
            locked = Arrays.asList(lockedItems);
            locked = new ArrayList<>(locked);
        } else {
            return null;
        }

        return (ArrayList<String>) locked;
    }

    public SparseBooleanArray getState() {
        SparseBooleanArray booleanArray;
        SharedPreferences preferences;
        preferences = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE);
        booleanArray = new Gson().fromJson(preferences.getString(Constants.SPARSEARRAY, ""), SparseBooleanArray.class);
        return booleanArray;

    }

    public void changeStateTo(boolean value, int position) {
        SparseBooleanArray booleanArray=getState();
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE).edit();
        booleanArray.put(position,value);
        editor.putString(Constants.SPARSEARRAY,new Gson().toJson(booleanArray));
        editor.commit();
    }


    public void createState() {
        SparseBooleanArray booleanArray = new SparseBooleanArray();
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE).edit();
        editor.putString(Constants.SPARSEARRAY, new Gson().toJson(booleanArray));
        editor.commit();
    }


    public void savePasswordPattern(String password) {
        SharedPreferences passwordPattern;
        SharedPreferences.Editor editor;
        passwordPattern = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE);
        editor = passwordPattern.edit();
        editor.putString(Constants.PASSWORD_PATTERN, password);

    }

    public String getPasswordPattern() {
        SharedPreferences passwordPattern;
        passwordPattern = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE);
        if (passwordPattern.contains(Constants.PASSWORD_PATTERN)) {
            return passwordPattern.getString(Constants.PASSWORD_PATTERN, "");
        }
        return "";
    }

    public void savePasswordPin(String password) {
        SharedPreferences passwordPin;
        SharedPreferences.Editor editor;
        passwordPin = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE);
        editor = passwordPin.edit();
        editor.putString(Constants.PASSWORD_PIN, password);
        editor.commit();

    }

    public String getPasswordPin() {
        SharedPreferences passwordPin;
        passwordPin = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE);
        if (passwordPin.contains(Constants.PASSWORD_PIN)) {
            return passwordPin.getString(Constants.PASSWORD_PIN, "");
        }
        return "";
    }
}
