package com.example.dkorh.ListeDemande;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class MyAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public MyAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Encours homeFragment = new Encours();
                return homeFragment;
            case 1:
                Valider sportFragment = new Valider();
                return sportFragment;
            case 2:
                Rejecter movieFragment = new Rejecter();
                return movieFragment;
            default:
                Encours homeFragmentx = new Encours();
                return homeFragmentx;
        }
    }
    @Override
    public int getCount() {
        return totalTabs;
    }
}