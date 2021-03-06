package com.anb.finalmoviecatalogue.feature.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.anb.finalmoviecatalogue.R;
import com.anb.finalmoviecatalogue.data.model.Movie;
import com.anb.finalmoviecatalogue.utils.Utils;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.GridViewHolder> {
    private Context context;
    private ArrayList<Movie> listMovie;
    private ArrayList<Movie> listMovieFull;
    private final OnItemClickListener listener;

    public void filter(String textSearched) {
        textSearched = textSearched.toLowerCase();
        listMovie.clear();
        if (!listMovieFull.isEmpty()){
            if (textSearched.length() != 0){
                for (Movie movie: listMovieFull) {
                    if (movie.getTitle().toLowerCase().contains(textSearched)){
                        listMovie.add(movie);
                    }
                }
            } else {
                listMovie.addAll(listMovieFull);
            }
        }
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Movie movie);
    }

    public MovieAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        listMovie = new ArrayList<>();
        listMovieFull = new ArrayList<>();
    }

    private ArrayList<Movie> getListMovie() {
        return listMovie;
    }

    public void setListMovie(ArrayList<Movie> listMovie) {
        this.listMovie = listMovie;
        listMovieFull.clear();
        listMovieFull.addAll(listMovie);
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

        void bind(final Movie movie, final OnItemClickListener listener) {
            Utils.setImage(movie.getPoster_path(), imgPhoto);
            imgPhoto.setOnClickListener(v -> listener.onItemClick(movie));
        }
    }
}
