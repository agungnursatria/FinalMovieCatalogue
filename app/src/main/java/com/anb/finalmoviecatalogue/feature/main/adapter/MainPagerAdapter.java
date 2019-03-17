package com.anb.finalmoviecatalogue.feature.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.anb.finalmoviecatalogue.feature.favorite.FavoriteFragment;
import com.anb.finalmoviecatalogue.feature.movie.MovieFragment;
import com.anb.finalmoviecatalogue.feature.tvshow.TVShowFragment;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;

public class MainPagerAdapter extends FragmentPagerAdapter {
    private AHBottomNavigation nav;
    private Fragment[] fragments = {new MovieFragment(), new TVShowFragment(), new FavoriteFragment()};

    public MainPagerAdapter(FragmentManager fm, AHBottomNavigation nav) {
        super(fm);
        this.nav = nav;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                return fragments[0];
            case 1:
                return fragments[1];
            case 2:
                return fragments[2];
        }
        return null;
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
