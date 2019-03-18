package com.anb.finalmoviecatalogue.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.anb.finalmoviecatalogue.R;
import com.anb.finalmoviecatalogue.data.db.DatabaseContract;
import com.anb.finalmoviecatalogue.data.model.Favorite;
import com.anb.finalmoviecatalogue.utils.Constant;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import static com.anb.finalmoviecatalogue.data.db.DatabaseContract.FavoriteColumns.CONTENT_URI_MOVIE;
import static com.anb.finalmoviecatalogue.data.db.DatabaseContract.FavoriteColumns.CONTENT_URI_TVSHOW;
import static com.anb.finalmoviecatalogue.data.db.DatabaseContract.getColumnString;

public class StackRemoteFactory implements RemoteViewsService.RemoteViewsFactory {

    private ArrayList<Favorite> mWidgetItems = new ArrayList<>();
    private final Context mContext;
    private Cursor mCursor;


    public StackRemoteFactory(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {}

    @Override
    public void onDataSetChanged() {
        mWidgetItems.clear();
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                setFavoriteFromCursor(CONTENT_URI_MOVIE);
                setFavoriteFromCursor(CONTENT_URI_TVSHOW);
            }
        };

        thread.start();

        try {
            thread.join();
        }catch (InterruptedException e){

        }
    }

    @Override
    public void onDestroy() {
        if (mCursor != null){
            mCursor.close();
        }
    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        try {
            Bitmap b = Picasso.get().load(Constant.BASE_POSTER_URL + mWidgetItems.get(position).getPoster_path()).get();
            rv.setImageViewBitmap(R.id.imageView, b);
        }catch (IOException e){
            e.printStackTrace();
        }

        Bundle extras = new Bundle();
        extras.putInt(ImageBannerWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private void setFavoriteFromCursor(Uri uri){
        mCursor = mContext.getContentResolver().query(uri, null,null,null,null);
        if (mCursor != null){
            try {
                if (mCursor.moveToFirst()){
                    do {
                        Favorite fav = new Favorite(
                                getColumnString(mCursor, DatabaseContract.FavoriteColumns._ID),
                                getColumnString(mCursor, DatabaseContract.FavoriteColumns.NAME),
                                getColumnString(mCursor, DatabaseContract.FavoriteColumns.POSTER),
                                getColumnString(mCursor, DatabaseContract.FavoriteColumns.TYPE)
                        );
                        mWidgetItems.add(fav);
                    } while (mCursor.moveToNext());
                }
            } catch (Exception e){
                mCursor.close();
            } finally {
                mCursor.close();
            }
        }
    }
}
