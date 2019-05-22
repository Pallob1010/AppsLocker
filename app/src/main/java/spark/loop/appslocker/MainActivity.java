package spark.loop.appslocker;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import Fragments.About;
import Fragments.AllApps;
import Fragments.Settings;
import LockSavingRemoving.SharedPreference;
import expandablerecyclerview.Apps;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, CompoundButton.OnCheckedChangeListener {
    FragmentManager fragmentManager;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    boolean v = false;
    SharedPreference sharedPreference;
    TextView textViewlock;
    Apps apps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apps=new Apps(this);
        setContentView(R.layout.activity_main);
        sharedPreference = new SharedPreference(this);
        drawerLayout = findViewById(R.id.drawerlayout);
        AllApps();
        navigationView = findViewById(R.id.navigationView);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        OnOffSwitch(navigationView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {

            case R.id.unlock_all:
                if (v) {
                    item.setIcon(R.drawable.unlock_all);
                    v = false;
                } else {
                    item.setIcon(R.drawable.lock_all);
                    v = true;
                }

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.all_apps:
                drawerLayout.closeDrawers();
                AllApps();
                return true;
            case R.id.change_pattern:
                Toast.makeText(MainActivity.this, "A", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawers();
                return true;
            case R.id.change_pin:
                Toast.makeText(MainActivity.this, "A", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawers();
                return true;
            case R.id.lock_type:
                Toast.makeText(MainActivity.this, "A", Toast.LENGTH_SHORT).show();
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
        if (isChecked) {
            buttonView.setText("Locked");
            textViewlock.setBackgroundResource(R.drawable.lock_all);
            sharedPreference.On();
        } else {
            buttonView.setText("Unlocked");
            textViewlock.setBackgroundResource(R.drawable.unlock_all);
            sharedPreference.Off();
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



    public void AllApps() {
        AllApps allApps = new AllApps(MainActivity.this,apps);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, allApps);
        fragmentTransaction.commit();
    }

    public void Settings() {

        Settings settings = new Settings();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, settings);
        fragmentTransaction.commit();
    }

    public void About() {
        About about = new About();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, about);
        fragmentTransaction.commit();
    }
}
