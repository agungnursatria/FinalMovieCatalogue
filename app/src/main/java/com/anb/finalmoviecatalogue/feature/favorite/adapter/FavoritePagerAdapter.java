package com.anb.finalmoviecatalogue.feature.favorite.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.anb.finalmoviecatalogue.R;
import com.anb.finalmoviecatalogue.feature.favorite_movie.FavoriteMovieFragment;
import com.anb.finalmoviecatalogue.feature.favorite_tvshow.FavoriteTVShowFragment;

public class FavoritePagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private static Fragment[] fragments = {new FavoriteMovieFragment(), new FavoriteTVShowFragment()};

    public FavoritePagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    public static Fragment[] getFragments() {
        return fragments;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0 :
                return fragments[0];
            case 1 :
                return fragments[1];
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return context.getString(R.string.nav_movie);
            case 1:
                return context.getString(R.string.nav_tv_show);
            default:
                return "";
        }
    }
}
