package com.anb.favoriteapp.feature.favorite;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.anb.favoriteapp.R;
import com.anb.favoriteapp.feature.favorite.adapter.FavoritePagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteActivity extends AppCompatActivity {

    @BindView(R.id.vp_fav)
    ViewPager vp_fav;
    @BindView(R.id.tl_fav)
    TabLayout tl_fav;
    FavoritePagerAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        pagerAdapter = new FavoritePagerAdapter(getSupportFragmentManager(), this);
        vp_fav.setAdapter(pagerAdapter);
        vp_fav.setOffscreenPageLimit(2);
        tl_fav.setupWithViewPager(vp_fav);
    }
}
