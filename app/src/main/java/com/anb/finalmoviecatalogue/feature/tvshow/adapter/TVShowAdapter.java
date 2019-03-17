package com.anb.finalmoviecatalogue.feature.tvshow.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.anb.finalmoviecatalogue.R;
import com.anb.finalmoviecatalogue.data.model.TVShow;
import com.anb.finalmoviecatalogue.utils.Utils;

import java.util.ArrayList;

public class TVShowAdapter extends RecyclerView.Adapter<TVShowAdapter.GridViewHolder> {
    private Context context;
    private ArrayList<TVShow> listMovie;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(TVShow tvShow);
    }

    public TVShowAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        listMovie = new ArrayList<TVShow>();
    }

    public ArrayList<TVShow> getListMovie() {
        return listMovie;
    }

    public void setListMovie(ArrayList<TVShow> listMovie) {
        this.listMovie = listMovie;
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_grid, viewGroup, false);
        return new GridViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int i) {
        holder.bind(listMovie.get(i), listener);
    }
    @Override
    public int getItemCount() {
        return getListMovie().size();
    }

    class GridViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;

        GridViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
        }

        void bind(final TVShow tvShow, final OnItemClickListener listener) {
            Utils.setImage(tvShow.getPoster_path(), imgPhoto);
            imgPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(tvShow);
                }
            });
        }
    }
}
