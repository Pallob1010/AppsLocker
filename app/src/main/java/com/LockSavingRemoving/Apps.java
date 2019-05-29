package com.LockSavingRemoving;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.loop.appslocker.R;

public class Apps {

    ApplicationInfo applicationInfo;
    Context context;
    SharedPreference preference;
    ArrayList<Apps> allAppsArrayList = new ArrayList<>();
    ArrayList<Apps> installedApps = new ArrayList<>();
    private String packageNamE;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int type;

    public Apps(Context context) {
        this.context=context;
        getAllApps();
      //  GetApps();
    }

    public Apps(String packageName, int type,Context context) {
        this.packageNamE = packageName;
        this.context = context;
        this.type=type;
    }


    public String getPackageName() {
        return packageNamE;
    }

    public ArrayList<Apps>allAppsArrayList() {
        return allAppsArrayList;
    }


    public void getAllApps() {
        allAppsArrayList.add(new Apps("System Apps",Constants.SYSTEM_TITLE,context));
        allAppsArrayList.add(new Apps(Constants.Package_Installer,Constants.SYSTEM_APPS,context));
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        List<ResolveInfo> resolveInfoList = context.getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfoList) {
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            if (isSystemPackage(resolveInfo)) {
                allAppsArrayList.add(new Apps(activityInfo.applicationInfo.packageName,Constants.SYSTEM_APPS,context));
            } else {
                if (!activityInfo.applicationInfo.packageName.equals("com.loop.appslocker"))
                installedApps.add(new Apps(activityInfo.applicationInfo.packageName,Constants.INSTALLED_APPS,context));

            }
        }
        allAppsArrayList.add(new Apps("Installed Apps",Constants.INSTALLED_TITLE,context));
        allAppsArrayList.addAll(installedApps);

    }

    public boolean isSystemPackage(ResolveInfo resolveInfo) {

        return ((resolveInfo.activityInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 1);
    }

    private void GetApps(){
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager packageManager=context.getPackageManager();
        allAppsArrayList.add(new Apps("System Apps",Constants.SYSTEM_TITLE,context));
        List<ResolveInfo> appList = packageManager.queryIntentActivities(mainIntent, 0);
        Collections.sort(appList, new ResolveInfo.DisplayNameComparator(packageManager));
        List<PackageInfo> packs = packageManager.getInstalledPackages(0);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            ApplicationInfo a = p.applicationInfo;

            if ((a.flags & ApplicationInfo.FLAG_SYSTEM) == 1) {
                 allAppsArrayList.add(new Apps(p.packageName,Constants.SYSTEM_APPS,context));
            }else {
                if (!p.packageName.equals("com.loop.appslocker"))
                    installedApps.add(new Apps(p.packageName,Constants.INSTALLED_APPS,context));
            }
        }
        allAppsArrayList.add(new Apps("Installed Apps",Constants.INSTALLED_TITLE,context));
        allAppsArrayList.addAll(installedApps);


    }






}

