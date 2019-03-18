package com.anb.finalmoviecatalogue.feature.favorite;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.anb.finalmoviecatalogue.R;
import com.anb.finalmoviecatalogue.feature.favorite.adapter.FavoritePagerAdapter;
import com.anb.finalmoviecatalogue.feature.favorite_movie.FavoriteMovieFragment;
import com.anb.finalmoviecatalogue.feature.favorite_tvshow.FavoriteTVShowFragment;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    @BindView(R.id.vp_fav) ViewPager vp_fav;
    @BindView(R.id.tl_fav) TabLayout tl_fav;
    private Context context;
    SearchView searchView;
    FavoritePagerAdapter pagerAdapter;

    public FavoriteFragment() {}


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        context = view.getContext();
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pagerAdapter = new FavoritePagerAdapter(getChildFragmentManager(), view.getContext());
        vp_fav.setAdapter(pagerAdapter);
        vp_fav.setOffscreenPageLimit(2);
        tl_fav.setupWithViewPager(vp_fav);

        vp_fav.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                filterMovieandTVShow(i, "");
                setSearchQueryListener(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        SearchManager searchManager = (SearchManager) context.getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(Objects.requireNonNull(getActivity()).getComponentName()));
            setSearchQueryListener(0);
        }
    }

    void setSearchQueryListener(int i){
        searchView.setQueryHint(getResources().getString((i == 0)? R.string.movie_hint: R.string.tvshow_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                InputMethodManager inputManager = (InputMethodManager)
                        Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputManager != null) {
                    inputManager.hideSoftInputFromWindow(Objects.requireNonNull(getActivity().getCurrentFocus()).getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filterMovieandTVShow(i, newText);
                return false;
            }
        });
    }

    void filterMovieandTVShow(int i, String newText){
        Fragment fragment = FavoritePagerAdapter.getFragments()[i];
        if (fragment instanceof FavoriteMovieFragment){
            ((FavoriteMovieFragment) fragment).getAdapter().filter(newText);
        } else if (fragment instanceof FavoriteTVShowFragment){
            ((FavoriteTVShowFragment) fragment).getAdapter().filter(newText);
        } else {
            Toast.makeText(context, "Unknown Fragment", Toast.LENGTH_SHORT).show();
        }
    }
}
