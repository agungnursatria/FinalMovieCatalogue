package com.anb.favoriteapp.feature.movie_detail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.anb.favoriteapp.R;
import com.anb.favoriteapp.data.model.MovieDetail;
import com.anb.favoriteapp.data.model.ReviewResponse;
import com.anb.favoriteapp.data.model.Video;
import com.anb.favoriteapp.data.model.VideoResponse;
import com.anb.favoriteapp.feature.movie_detail.adapter.ReviewAdapter;
import com.anb.favoriteapp.feature.movie_detail.adapter.VideoAdapter;
import com.anb.favoriteapp.utils.Constant;
import com.anb.favoriteapp.utils.Utils;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity implements VideoAdapter.OnItemClickListener {
    public static final String EXTRA_MOVIE_ID = "extra_movie_id";

    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.toolbar_layout) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.iv_poster) ImageView iv_poster;
    @BindView(R.id.iv_backdrop_poster) ImageView iv_backdrop_poster;
    @BindView(R.id.tv_title) TextView tv_title;
    @BindView(R.id.tv_genres) TextView tv_genres;
    @BindView(R.id.tv_overview) TextView tv_overview;
    @BindView(R.id.tv_release_date) TextView tv_release_date;
    @BindView(R.id.tv_vote_average) TextView tv_vote_average;
    @BindView(R.id.rv_trailers) RecyclerView rv_trailers;
    @BindView(R.id.rv_review) RecyclerView rv_review;
    @BindView(R.id.fab_fav) FloatingActionButton fab_fav;

    private MovieDetailViewModelFactory viewModelFactory;
    private MovieDetailViewModel viewModel;
    private Observer<MovieDetail> observerMovie;
    private Observer<ReviewResponse> observerReview;
    private Observer<VideoResponse> observerVideo;
    private Observer<Boolean> observerFav;
    private String id;
    private ReviewAdapter reviewAdapter;
    private VideoAdapter videoAdapter;
    private MovieDetail movieDetail = null;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        progressBar.setVisibility(View.VISIBLE);

        reviewAdapter = new ReviewAdapter();
        videoAdapter = new VideoAdapter(this);
        LinearLayoutManager videoLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager reviewLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv_review.setLayoutManager(reviewLayoutManager);
        rv_trailers.setLayoutManager(videoLayoutManager);

        id = Objects.requireNonNull(getIntent().getExtras()).getString(EXTRA_MOVIE_ID);

        viewModelFactory = new MovieDetailViewModelFactory(this, id);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieDetailViewModel.class);
        setObserver();
        viewModel.getMovieResponse().observe(this, observerMovie);
        viewModel.getReviewResponse().observe(this, observerReview);
        viewModel.getVideoResponse().observe(this, observerVideo);
        viewModel.getIsFavorite().observe(this, observerFav);

        fab_fav.setOnClickListener(v -> viewModel.changeFavState());
    }

    void setObserver(){
        observerMovie = movieDetail -> {
            progressBar.setVisibility(View.GONE);
            if (movieDetail != null){
                this.movieDetail = movieDetail;
                collapsingToolbarLayout.setTitle(movieDetail.getTitle());
                tv_title.setText(movieDetail.getTitle());
                tv_genres.setText(movieDetail.genreToString());
                tv_release_date.setText(movieDetail.getRelease_date());
                tv_overview.setText(movieDetail.getOverview());
                tv_vote_average.setText("Rating " + movieDetail.getVote_average());
                Utils.setBackdropImage(movieDetail.getBackdrop_path(), iv_backdrop_poster);
                Utils.setImage(movieDetail.getPoster_path(), iv_poster);
            }
        };

        observerFav = fav -> {
            fab_fav.setImageResource((fav)? R.drawable.ic_added_to_favorites: R.drawable.ic_add_to_favorites);
        };

        observerReview = reviewResponse -> {
            progressBar.setVisibility(View.GONE);
            if (reviewResponse != null){
                reviewAdapter.setReviewList(reviewResponse.getResults());
                rv_review.setAdapter(reviewAdapter);
            }
        };

        observerVideo = videoResponse -> {
            progressBar.setVisibility(View.GONE);
            if (videoResponse != null){
                videoAdapter.setVideoList(videoResponse.getResults());
                rv_trailers.setAdapter(videoAdapter);
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                setResult(Activity.RESULT_OK, new Intent());
                finish();
                break;
//                super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onVideoClick(Video video) {
        try {
            String videoUrl = Constant.YOUTUBE_WATCH + video.getKey();
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
            startActivity(myIntent);
        } catch (Exception e) {
            Toast.makeText(this, "No application can handle this request."
                    + " Please install a Web browser", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_OK, new Intent());
//        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
