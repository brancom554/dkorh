package com.example.dkorh.Event;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import com.example.jisrh.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Evennement extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private final String TAG = this.getClass().getSimpleName();

    private BottomNavigationView bottomNavigationView;

    private final Fragment calendarFragment = new CalendarFragment();
    private final Fragment upcomingEventsFragment = new UpcomingEventsFragment();
    private final Fragment userSettingsFragment = new UserSettingsFragment();

    private final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evennement);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.MainActivity_BottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

       // fragmentManager.beginTransaction().add(R.id.MainActivity_FrameLayout_Container, userSettingsFragment).hide(userSettingsFragment).commit();
        fragmentManager.beginTransaction().add(R.id.MainActivity_FrameLayout_Container, upcomingEventsFragment).hide(upcomingEventsFragment).commit();
        fragmentManager.beginTransaction().add(R.id.MainActivity_FrameLayout_Container, calendarFragment).commit();

      /*  if (getFlag("isChanged")) {
            bottomNavigationView.setSelectedItemId(R.id.BottomNavigation_Item_Settings);
            bottomNavigationView.performClick();
            saveFlag("isChanged", false);
        }*/
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.BottomNavigation_Item_Calendar:
                ((CalendarFragment) calendarFragment).setUpCalendar();
                fragmentManager.beginTransaction()
                        .hide(userSettingsFragment)
                        .hide(upcomingEventsFragment)
                        .show(calendarFragment)
                        .commit();
                break;
            case R.id.BottomNavigation_Item_UpcomingEvents:
                ((UpcomingEventsFragment) upcomingEventsFragment).setUpRecyclerView();
                fragmentManager.beginTransaction()
                        .hide(calendarFragment)
                        .hide(userSettingsFragment)
                        .show(upcomingEventsFragment)
                        .commit();
                break;

        }

        return true;
    }


    private void saveFlag(String key, boolean flag) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, flag);
        editor.apply();
    }

    private boolean getFlag(String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getBoolean(key, false);
    }

    private String getString(String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getString(key, "Indigo");
    }


}

