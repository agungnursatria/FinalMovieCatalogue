package com.anb.finalmoviecatalogue.feature.movie;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.anb.finalmoviecatalogue.R;
import com.anb.finalmoviecatalogue.data.model.Movie;
import com.anb.finalmoviecatalogue.data.model.MovieResponse;
import com.anb.finalmoviecatalogue.feature.favorite.adapter.FavoritePagerAdapter;
import com.anb.finalmoviecatalogue.feature.main.MainActivity;
import com.anb.finalmoviecatalogue.feature.movie.adapter.MovieAdapter;
import com.anb.finalmoviecatalogue.feature.movie_detail.MovieDetailActivity;
import com.anb.finalmoviecatalogue.utils.Constant;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment implements MovieAdapter.OnItemClickListener {

    @BindView(R.id.rv) RecyclerView rv;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    private Context context;
    private MovieAdapter adapter;
    private ArrayList<Movie> list = new ArrayList<>();
    private GridLayoutManager gridLayoutManager;
    private MovieViewModelFactory viewModelFactory;
    private MovieViewModel viewModel;
    private Observer<MovieResponse> observer;

    public MovieFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        context = view.getContext();
        viewModelFactory = new MovieViewModelFactory(context);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new MovieAdapter(context, this);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieViewModel.class);
        observer = movieResponse -> {
            progressBar.setVisibility(View.GONE);
            list = movieResponse.getResults();
            setLayoutManager(context);
            adapter.setListMovie(list);
            rv.setAdapter(adapter);
        };
        viewModel.getResponse().observe(getViewLifecycleOwner(), observer);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.getResponse().removeObserver(observer);
    }

    @Override
    public void onItemClick(Movie movie) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.EXTRA_MOVIE_ID, movie.getId());
        this.startActivityForResult(intent, Constant.REQUEST_REFRESH_FAVORITE_ON_BACK);
    }

    public void setLayoutManager(Context context) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            gridLayoutManager = new GridLayoutManager(context, 2);
        } else {
            gridLayoutManager = new GridLayoutManager(context, 4);
        }
        rv.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (MainActivity.isFavViewLoaded){
            FavoritePagerAdapter.getFragments()[0].onActivityResult(requestCode,resultCode,data);
            FavoritePagerAdapter.getFragments()[1].onActivityResult(requestCode,resultCode,data);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        setLayoutManager(context);
        super.onConfigurationChanged(newConfig);
    }
}
