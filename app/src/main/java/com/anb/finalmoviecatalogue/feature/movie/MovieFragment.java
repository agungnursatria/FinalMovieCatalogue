package com.anb.finalmoviecatalogue.feature.movie;


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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;

import com.anb.finalmoviecatalogue.R;
import com.anb.finalmoviecatalogue.data.model.Movie;
import com.anb.finalmoviecatalogue.data.model.MovieResponse;
import com.anb.finalmoviecatalogue.feature.favorite.adapter.FavoritePagerAdapter;
import com.anb.finalmoviecatalogue.feature.main.MainActivity;
import com.anb.finalmoviecatalogue.feature.movie.adapter.MovieAdapter;
import com.anb.finalmoviecatalogue.feature.movie_detail.MovieDetailActivity;
import com.anb.finalmoviecatalogue.utils.Constant;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment implements MovieAdapter.OnItemClickListener {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private Context context;
    private MovieAdapter adapter;
    private MovieViewModelFactory viewModelFactory;
    private MovieViewModel viewModel;
    private Observer<MovieResponse> observer;

    public MovieFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        context = view.getContext();
        viewModelFactory = new MovieViewModelFactory(context);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new MovieAdapter(context, this);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        SearchManager searchManager = (SearchManager) context.getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(Objects.requireNonNull(getActivity()).getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.movie_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    InputMethodManager inputManager = (InputMethodManager)
                            Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (inputManager != null) {
                        inputManager.hideSoftInputFromWindow(Objects.requireNonNull(getActivity().getCurrentFocus()).getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    progressBar.setVisibility(View.VISIBLE);
                    viewModel.searchMovie(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
//                    adapter.filter(newText);
                    return false;
                }
            });
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieViewModel.class);
        observer = movieResponse -> {
            if (movieResponse != null) {
                progressBar.setVisibility(View.GONE);
                setLayoutManager(context);
                adapter.setListMovie(movieResponse.getResults());
                rv.setAdapter(adapter);
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
    public void onItemClick(Movie movie) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.EXTRA_MOVIE_ID, movie.getId());
        this.startActivityForResult(intent, Constant.REQUEST_REFRESH_FAVORITE_ON_BACK);
    }

    public void setLayoutManager(Context context) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                context,
                (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) ? 2 : 4
        );
        rv.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (MainActivity.isFavViewLoaded) {
            FavoritePagerAdapter.getFragments()[0].onActivityResult(requestCode, resultCode, data);
            FavoritePagerAdapter.getFragments()[1].onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        setLayoutManager(context);
        super.onConfigurationChanged(newConfig);
    }
}
