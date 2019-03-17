package com.anb.finalmoviecatalogue.feature.favorite;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anb.finalmoviecatalogue.R;
import com.anb.finalmoviecatalogue.feature.favorite.adapter.FavoritePagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    @BindView(R.id.vp_fav) ViewPager vp_fav;
    @BindView(R.id.tl_fav) TabLayout tl_fav;

    public FavoriteFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FavoritePagerAdapter mSectionsPagerAdapter = new FavoritePagerAdapter(getChildFragmentManager(), view.getContext());
        vp_fav.setAdapter(mSectionsPagerAdapter);
        tl_fav.setupWithViewPager(vp_fav);
    }
}
