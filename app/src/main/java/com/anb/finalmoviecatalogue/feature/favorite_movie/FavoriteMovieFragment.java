package com.anb.finalmoviecatalogue.feature.favorite_movie;


import android.app.Activity;
import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.anb.finalmoviecatalogue.R;
import com.anb.finalmoviecatalogue.data.model.Favorite;
import com.anb.finalmoviecatalogue.feature.favorite_movie.adapter.FavoriteMovieAdapter;
import com.anb.finalmoviecatalogue.feature.movie_detail.MovieDetailActivity;
import com.anb.finalmoviecatalogue.utils.Constant;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment implements FavoriteMovieAdapter.OnItemClickListener {

    @BindView(R.id.rv) RecyclerView rv;
    @BindView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tv_empty) TextView tv_empty;

    private Context context;
    private FavoriteMovieAdapter adapter;
    private FavoriteMovieViewModelFactory viewModelFactory;
    private FavoriteMovieViewModel viewModel;
    private Observer<ArrayList<Favorite>> observer;

    public FavoriteMovieFragment() {}

    public FavoriteMovieAdapter getAdapter() {
        return adapter;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_movie, container, false);
        context = view.getContext();
        viewModelFactory = new FavoriteMovieViewModelFactory(context);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout.setRefreshing(true);
        adapter = new FavoriteMovieAdapter(context, this);
        setLayoutManager(context);
        rv.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(() -> viewModel.loadFavoriteMovie());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FavoriteMovieViewModel.class);
        observer = movieList -> {
            if (movieList != null){
                swipeRefreshLayout.setRefreshing(false);
                adapter.setListFavorite(movieList);
                adapter.notifyDataSetChanged();
                tv_empty.setVisibility((movieList.size()>0)? View.INVISIBLE : View.VISIBLE);
            }
        };
        viewModel.getResponse().observe(getViewLifecycleOwner(), observer);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.getResponse().removeObserver(observer);
    }

    @Override
    public void onItemClick(Favorite favorite) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.EXTRA_MOVIE_ID, favorite.getId());
        startActivityForResult(intent, Constant.REQUEST_REFRESH_FAVORITE_ON_BACK);
    }

    public void setLayoutManager(Context context) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context,
                (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) ? 2:4);
        rv.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        setLayoutManager(context);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            viewModel.loadFavoriteMovie();
        }
    }
}
