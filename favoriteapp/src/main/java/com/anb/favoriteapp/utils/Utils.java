package com.anb.favoriteapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Utils {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public static void setImage(String url, ImageView iv){
        Picasso.get()
                .load(Constant.BASE_POSTER_URL + url)
                .fit()
                .centerCrop()
                .into(iv);
    }

    public static void setBackdropImage(String url, ImageView iv){
        Picasso.get()
                .load(Constant.BASE_BACKDROP_URL + url)
                .fit()
                .centerCrop()
                .into(iv);
    }

    public static void setImageYoutube(String url, ImageView iv){
        Picasso.get()
                .load(url)
                .fit()
                .centerCrop()
                .into(iv);
    }
}
