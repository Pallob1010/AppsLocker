package com.LockSavingRemoving;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.SparseBooleanArray;

import com.google.gson.Gson;
import com.loop.appslocker.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPreference {

    Context context;
    List<String> lockedapp, empty;


    public SharedPreference(Context context) {
        this.context = context;
    }

/////////////////////////////////////////////////////////////Getters Method////////////////////////////////////////////////////////////////////////////////////////

    public boolean isAllLocked() {
        return (context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE).getBoolean(Constants.ALL_APPS_LOCKED, false));
    }

    public boolean isRunning() {
        return ((context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE)).getBoolean(Constants.APPS_RUNNING, false));
    }

    public void setRunning(boolean var) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE).edit();
        editor.putBoolean(Constants.APPS_RUNNING, var).commit();

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
        if (!preferences.contains(Constants.SPARSEARRAY)) {
            booleanArray = new SparseBooleanArray();
            createState(booleanArray);

        } else {
            booleanArray = new Gson().fromJson(preferences.getString(Constants.SPARSEARRAY, ""), SparseBooleanArray.class);

        }
        return booleanArray;

    }

    public String getPasswordPattern() {
        return (context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE).getString(Constants.PASSWORD_PATTERN, ""));
    }

    public String getPasswordPin() {
        return (context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE)).
                getString(Constants.PASSWORD_PIN, "");
    }

    public int getLockType() {
        return (context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE).getInt(Constants.LOCKED_TYPE, 3));
    }


    public int pinLockSize() {
        return (context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE).getInt(Constants.PIN_LOCK_SIZE, 4));
    }
    public int patternSize() {
        return (context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE).getInt(Constants.PATTERN_SIZE, 3));
    }

    public int patternDot(){
        return (context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE).getInt(Constants.PATTERN_DOT, R.drawable.white_normal));
    }

    public int patternHighlighted(){
        return (context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE).getInt(Constants.PATTERN_HIGHLIGHTED, R.drawable.white_highlight));
    }

    public String patternLineColor(){
        return (context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE).getString(Constants.PATTERN_LINE,"#000000"));
    }
    public int patternLineSize(){
        return (context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE).getInt(Constants.PATTERN_LINE_SIZE, 15));
    }

    public String patternVisibility(){
        return (context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE).getString(Constants.VISIBILITY,"visible"));

    }

    /////////////////////////////////////////////////////////////Setters Method/////////////////////////////////////////////////////////////////////////


    public void saveLockState(boolean var) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE).edit();
        editor.putBoolean(Constants.ALL_APPS_LOCKED, var).commit();
    }

    public void unLockAll() {
        SparseBooleanArray booleanArray = new SparseBooleanArray();
        createState(booleanArray);
        saveLockState(false);
        empty = new ArrayList<>();
        saveLockedApp(empty);


    }

    public void lockAll(int size, ArrayList<Apps> list) {
        saveLockState(true);
        lockedapp=new ArrayList<>();
        SparseBooleanArray booleanArray = new SparseBooleanArray();
        for (int i = 0; i < size; i++) {
            booleanArray.put(i, true);
            lockedapp.add(list.get(i).getPackageName());
        }
        createState(booleanArray);
        saveLockedApp(lockedapp);

    }

    public void saveLockedApp(List<String> LockedApp) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE).edit();
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


    public void changeStateTo(boolean value, int position) {
        SparseBooleanArray booleanArray = getState();
        booleanArray.put(position, value);
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE).edit();
        editor.putString(Constants.SPARSEARRAY, new Gson().toJson(booleanArray)).commit();
    }

    public void createState(SparseBooleanArray booleanArray) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE).edit();
        editor.putString(Constants.SPARSEARRAY, new Gson().toJson(booleanArray)).commit();
    }

    public void savePasswordPattern(String password) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE).edit();
        editor.putString(Constants.PASSWORD_PATTERN, password).commit();
    }


    public void savePasswordPin(String password) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE).edit();
        editor.putString(Constants.PASSWORD_PIN, password).commit();
    }


    public void saveLockType(int type) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE).edit();
        editor.putInt(Constants.LOCKED_TYPE, type).commit();
    }

    public void setPinLockSize(int size) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE).edit();
        editor.putInt(Constants.PIN_LOCK_SIZE, size).commit();
    }


    public void setPatternSize(int size) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE).edit();
        editor.putInt(Constants.PATTERN_SIZE, size).commit();
    }

    public void setPatternDot(int position){
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE).edit();
        editor.putInt(Constants.PATTERN_DOT,position).commit();
    }
    public void setPatternHighLighted(int position){
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE).edit();
        editor.putInt(Constants.PATTERN_HIGHLIGHTED,position).commit();
    }

    public void setPatternLineColor(String color){
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE).edit();
        editor.putString(Constants.PATTERN_LINE,color).commit();
    }


    public void setPatternLineSize(int size){
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE).edit();
        editor.putInt(Constants.PATTERN_LINE_SIZE,size).commit();
    }
    public void setVisibility(String visibility){
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE).edit();
        editor.putString(Constants.VISIBILITY,visibility).commit();
    }


}
