package LockSavingRemoving;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPreference {

    Context context;
    PackageManager packageManager;
    public SharedPreference() {

    }

    public SharedPreference(Context context) {
        this.context = context;
/*
        SharedPreferences sharedPreferences;
        sharedPreferences=context.getSharedPreferences(Constants.APPS_PREFERENCE,Context.MODE_PRIVATE);
        if(!sharedPreferences.contains(Constants.SYSTEM_APPS) | !sharedPreferences.contains(Constants.INSTALLED_APPS)){
            LoadTask loadTask=new LoadTask();
            loadTask.execute();

        }*/

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


    public void addSystemAppsPackage(ArrayList<String>packages) {
        SharedPreferences.Editor editor;
        SharedPreferences systemPackage;
        systemPackage = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE);
        editor = systemPackage.edit();
        Gson gson = new Gson();
        String jsonLocked = gson.toJson(packages);
        editor.putString(Constants.SYSTEM_APPS, jsonLocked);
        editor.commit();
    }

    public void addInstalledAppsPackage(ArrayList<String>packages) {
        SharedPreferences.Editor editor;
        SharedPreferences systemPackage;
        systemPackage = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE);
        editor = systemPackage.edit();
        Gson gson = new Gson();
        String jsonLocked = gson.toJson(packages);
        editor.putString(Constants.INSTALLED_APPS, jsonLocked);
        editor.commit();
    }

    public ArrayList<String>getSystemApps(Context context){
        SharedPreferences settings;
        List<String> packages=new ArrayList<>();
        settings = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE);
        if (settings.contains(Constants.SYSTEM_APPS)) {
            String jsonLocked = settings.getString(Constants.SYSTEM_APPS, null);
            Gson gson = new Gson();
            String[] systemapps = gson.fromJson(jsonLocked, String[].class);
            packages = Arrays.asList(systemapps);
            packages = new ArrayList<>(packages);
        }

        return (ArrayList<String>) packages;
    }
    public ArrayList<String>getInstallApps(Context context){
        SharedPreferences settings;
        List<String> packages=new ArrayList<>();
        settings = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE);
        if (settings.contains(Constants.INSTALLED_APPS)) {
            String jsonLocked = settings.getString(Constants.INSTALLED_APPS, null);
            Gson gson = new Gson();
            String[] systemapps = gson.fromJson(jsonLocked, String[].class);
            packages = Arrays.asList(systemapps);
            packages = new ArrayList<>(packages);
        }
        return (ArrayList<String>) packages;
    }

    public class LoadTask extends AsyncTask<String,String,String>{


        @Override
        protected String doInBackground(String... strings) {
            packageManager=context.getPackageManager();
            ArrayList<String>system=new ArrayList<>();
            ArrayList<String>install=new ArrayList<>();
            system.add(Constants.Package_Installer);
            Intent intent = new Intent(Intent.ACTION_MAIN, null);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(intent, 0);
            for (ResolveInfo resolveInfo : resolveInfoList) {
                ActivityInfo activityInfo = resolveInfo.activityInfo;
                if (isSystemPackage(resolveInfo)) {
                    system.add(activityInfo.applicationInfo.packageName);
                } else {
                    install.add(activityInfo.applicationInfo.packageName);
                }
            }
            addSystemAppsPackage(system);
            addInstalledAppsPackage(install);

            return null;
        }
    }


    public boolean isSystemPackage(ResolveInfo resolveInfo) {

        return ((resolveInfo.activityInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }

}
