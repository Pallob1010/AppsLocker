package com.loop.appslocker;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.Adapters.MultiViewTypeAdapter;
import com.Fragments.About;
import com.Fragments.SetPatternLock;
import com.Fragments.SetPinLock;
import com.Fragments.Settings;
import com.Listener.BackListener;
import com.LockSavingRemoving.Apps;
import com.LockSavingRemoving.SharedPreference;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, CompoundButton.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener, BackListener {
    FragmentManager fragmentManager;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    SharedPreference sharedPreference;
    TextView textViewlock;
    Apps apps;
    RecyclerView recyclerView;
    MultiViewTypeAdapter adapter;
    ArrayList<Apps> list;
    Intent intent;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //  OpeningDialog();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreference = new SharedPreference(this);
        apps = new Apps(this);
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationView);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = apps.allAppsArrayList();
        adapter = new MultiViewTypeAdapter(list, this, sharedPreference);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        OnOffSwitch(navigationView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top, menu);
        MenuItem item = menu.findItem(R.id.unlock_all);
        if (sharedPreference.isAllLocked()) {
            item.setIcon(R.drawable.lock_all);
        } else {
            item.setIcon(R.drawable.unlock_all);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {

            case R.id.unlock_all:
                if (!sharedPreference.isAllLocked()) {
                    item.setIcon(R.drawable.lock_all);
                    sharedPreference.lockAll(list.size(), list);
                    adapter.notifyDataSetChanged();

                } else {
                    item.setIcon(R.drawable.unlock_all);
                    sharedPreference.unLockAll();
                    adapter.notifyDataSetChanged();
                }

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.all_apps:
                clearApps();
                drawerLayout.closeDrawers();
                return true;
            case R.id.change_pattern:
                SetPattern();
                drawerLayout.closeDrawers();
                return true;
            case R.id.change_pin:
                SetPin();
                drawerLayout.closeDrawers();
                return true;
            case R.id.lock_type:
                SetLockType();
                drawerLayout.closeDrawers();

                return true;
            case R.id.settings:
                Settings();
                drawerLayout.closeDrawers();
                return true;
            case R.id.about:
                About();
                drawerLayout.closeDrawers();
                return true;
            case R.id.exit:
                finish();
                return true;
            default:
                return false;

        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        intent = new Intent(MainActivity.this, AppsLockerService.class);
        if (isChecked) {
            buttonView.setText("Locked");
            textViewlock.setBackgroundResource(R.drawable.lock_all);
            sharedPreference.On();
            startService(intent);
        } else {
            buttonView.setText("Unlocked");
            textViewlock.setBackgroundResource(R.drawable.unlock_all);
            sharedPreference.Off();
            stopService(intent);
        }


    }


    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radioPattern:
                sharedPreference.saveLockType(0);
                break;
            case R.id.radioPin:
                sharedPreference.saveLockType(1);
                break;
        }
    }
    @Override
    public void Back() {
        clearApps();
    }
    public void clearApps() {
        for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
            getSupportFragmentManager().popBackStack();
        }

    }

    public void OnOffSwitch(NavigationView navigationView) {
        View view = navigationView.getHeaderView(0);
        Switch aSwitch = view.findViewById(R.id.toggleSwitch);
        textViewlock = view.findViewById(R.id.textView);
        aSwitch.setOnCheckedChangeListener(this);


        if (sharedPreference.isRunning()) {
            aSwitch.setChecked(true);
            aSwitch.setText("Locked");
            textViewlock.setBackgroundResource(R.drawable.lock_all);
        } else {
            aSwitch.setChecked(false);
            aSwitch.setText("Unlocked");
            textViewlock.setBackgroundResource(R.drawable.unlock_all);
        }


    }

    public void SetLockType() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.locktype);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.show();
        RadioGroup radioGroup = dialog.findViewById(R.id.radioLock);
        radioGroup.setOnCheckedChangeListener(this);
        RadioButton radioPin = dialog.findViewById(R.id.radioPin);
        RadioButton radioPattern = dialog.findViewById(R.id.radioPattern);
        switch (sharedPreference.getLockType()) {
            case 0:
                radioPattern.setChecked(true);
                break;
            case 1:
                radioPin.setChecked(true);
                break;
        }
    }


    public void Settings() {
        clearApps();
        Settings settings = new Settings();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, settings);
        fragmentTransaction.addToBackStack("settings");
        fragmentTransaction.commit();
    }

    public void SetPattern() {
        clearApps();
        BackListener backListener=this;
        SetPatternLock setPatternLock = new SetPatternLock(backListener,this);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, setPatternLock);
        fragmentTransaction.addToBackStack("setPattern");
        fragmentTransaction.commit();

    }
    public void SetPin(){
        clearApps();
        BackListener backListener=this;
        SetPinLock setPinLock = new SetPinLock(backListener,this);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, setPinLock);
        fragmentTransaction.addToBackStack("setPinLock");
        fragmentTransaction.commit();

    }

    public void About() {
        clearApps();
        About about = new About();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, about);
        fragmentTransaction.addToBackStack("about");
        fragmentTransaction.commit();
    }

    public void OpeningDialog() {

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptsView = layoutInflater.inflate(R.layout.header, null);
        dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setContentView(promptsView);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.show();

    }


}
