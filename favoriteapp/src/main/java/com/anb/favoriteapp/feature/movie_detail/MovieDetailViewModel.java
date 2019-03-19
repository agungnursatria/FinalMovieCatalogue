package com.anb.favoriteapp.feature.movie_detail;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import com.anb.favoriteapp.data.api.RetroServer;
import com.anb.favoriteapp.data.model.MovieDetail;
import com.anb.favoriteapp.data.model.ReviewResponse;
import com.anb.favoriteapp.data.model.VideoResponse;
import com.anb.favoriteapp.utils.Constant;

import java.lang.ref.WeakReference;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.anb.favoriteapp.data.db.DatabaseContract.FavoriteColumns.CONTENT_URI_MOVIE;
import static com.anb.favoriteapp.data.db.DatabaseContract.FavoriteColumns.NAME;
import static com.anb.favoriteapp.data.db.DatabaseContract.FavoriteColumns.POSTER;
import static com.anb.favoriteapp.data.db.DatabaseContract.FavoriteColumns.TYPE;
import static com.anb.favoriteapp.data.db.DatabaseContract.FavoriteColumns._ID;

public class MovieDetailViewModel extends ViewModel {

    private final CompositeDisposable disposable = new CompositeDisposable();
    private final MutableLiveData<MovieDetail> movieResponse = new MutableLiveData<>();
    private final MutableLiveData<ReviewResponse> reviewResponse = new MutableLiveData<>();
    private final MutableLiveData<VideoResponse> videoResponse = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isFavorite = new MutableLiveData<>();
    private Uri mUri;
    @SuppressLint("StaticFieldLeak") private Context context;

    MovieDetailViewModel(Context context, String id){
        this.context = context;
        loadMovie(id);
        loadReviews(id);
        loadVideos(id);
    }

    @Override
    protected void onCleared() {
        disposable.clear();
    }

    MutableLiveData<MovieDetail> getMovieResponse() {
        return movieResponse;
    }

    MutableLiveData<ReviewResponse> getReviewResponse() {
        return reviewResponse;
    }

    MutableLiveData<VideoResponse> getVideoResponse() {
        return videoResponse;
    }

    MutableLiveData<Boolean> getIsFavorite() {
        return isFavorite;
    }

    void changeFavState(){
        MovieDetail movieDetail = movieResponse.getValue();
        if (movieDetail != null && isFavorite.getValue() != null){
            if (!isFavorite.getValue()) {
                ContentValues values = new ContentValues();
                values.put(_ID, movieDetail.getId());
                values.put(NAME, movieDetail.getTitle());
                values.put(POSTER, movieDetail.getPoster_path());
                values.put(TYPE, "movie");
                context.getContentResolver().insert(CONTENT_URI_MOVIE, values);
            } else {
                mUri = CONTENT_URI_MOVIE
                        .buildUpon()
                        .appendPath(String.valueOf(movieDetail.getId()))
                        .build();
                context.getContentResolver().delete(mUri, null, null);
            }
            isFavorite.setValue(!isFavorite.getValue());
        }
    }

    private void onErrorMovie(Throwable e) {
        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        movieResponse.setValue(null);
    }
    private void setDataMovie(MovieDetail movieDetail) {
        movieResponse.setValue(movieDetail);
        loadFavoriteMovieWithId(context, movieDetail.getId());
    }

    void loadFavoriteMovieWithId(Context context, String id){
        new AsyncFavMovieWithID(context, id).execute();
    }


    private void onErrorVideos(Throwable e) {
        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        videoResponse.setValue(null);
    }
    private void setDataVideos(VideoResponse videoResponse) {
        this.videoResponse.setValue(videoResponse);
    }
    private void onErrorReviews(Throwable e) {
        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        reviewResponse.setValue(null);
    }
    private void setDataReviews(ReviewResponse reviews) {
        this.reviewResponse.setValue(reviews);
    }

    private void loadMovie(String id){
        disposable.add(
                RetroServer
                        .getRequestService()
                        .getMovieDetail(id, Constant.API_KEY)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::setDataMovie, this::onErrorMovie)
        );
    }

    private void loadVideos(String id){
        disposable.add(
                RetroServer
                        .getRequestService()
                        .getMovieVideos(id, Constant.API_KEY)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::setDataVideos, this::onErrorVideos)
        );
    }

    private void loadReviews(String id){
        disposable.add(
                RetroServer
                        .getRequestService()
                        .getMovieReviews(id, Constant.API_KEY)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::setDataReviews, this::onErrorReviews)
        );
    }

    public class AsyncFavMovieWithID extends AsyncTask<Void, Void, Cursor> {
        private WeakReference<Context> weakContext;
        String id;

        AsyncFavMovieWithID(Context context, String id) {
            this.weakContext = new WeakReference<>(context);
            this.id = id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Context context = weakContext.get();
            mUri = CONTENT_URI_MOVIE
                    .buildUpon()
                    .appendPath(String.valueOf(id))
                    .build();
            return context.getContentResolver().query(mUri, null,null,null,null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            isFavorite.setValue(cursor.getCount()>0);
        }
    }
}
