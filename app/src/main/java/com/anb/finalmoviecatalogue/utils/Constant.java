package com.anb.finalmoviecatalogue.utils;

import com.anb.finalmoviecatalogue.BuildConfig;

public class Constant {
    public final static String API_KEY = BuildConfig.MOVIE_API_KEY;
    public final static String BASE_URL = "http://api.themoviedb.org/3/";
    public final static String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w185";
    public final static String BASE_BACKDROP_URL = "http://image.tmdb.org/t/p/w500";
    public final static String YOUTUBE_WATCH = "https://www.youtube.com/watch?v=";

    public final static int REQUEST_REFRESH_FAVORITE_ON_BACK = 0;

    public final static String LANGUAGE = "en-US";
}
