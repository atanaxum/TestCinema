package com.example.autumn.testcinema;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Autumn on 14/03/2018.
 */

public class SectionsPageAdapter extends FragmentPagerAdapter {
    private final List<Fragment> listFragment = new ArrayList<>();
    private final List<String> listTitreFragment = new ArrayList<>();

    public SectionsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    public void ajouterFragment(Fragment page, String titre){
        listFragment.add(page);
        listTitreFragment.add(titre);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listTitreFragment.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }
}
