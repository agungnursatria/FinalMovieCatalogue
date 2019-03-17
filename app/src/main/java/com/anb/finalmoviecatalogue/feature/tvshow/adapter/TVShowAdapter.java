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
    private ArrayList<TVShow> listTVShow;
    private ArrayList<TVShow> listTVShowFull;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(TVShow tvShow);
    }

    public TVShowAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        listTVShow = new ArrayList<>();
        listTVShowFull = new ArrayList<>();
    }

    private ArrayList<TVShow> getListMovie() {
        return listTVShow;
    }

    public void setListMovie(ArrayList<TVShow> listMovie) {
        this.listTVShow = listMovie;
        listTVShowFull.clear();
        listTVShowFull.addAll(listMovie);
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_grid, viewGroup, false);
        return new GridViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int i) {
        holder.bind(listTVShow.get(i), listener);
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
            imgPhoto.setOnClickListener(v -> listener.onItemClick(tvShow));
        }
    }

    public void filter(String textSearched) {
        textSearched = textSearched.toLowerCase();
        listTVShow.clear();
        if (!listTVShowFull.isEmpty()){
            if (textSearched.length() != 0){
                for (TVShow tvShow: listTVShowFull) {
                    if (tvShow.getName().toLowerCase().contains(textSearched)){
                        listTVShow.add(tvShow);
                    }
                }
            } else {
                listTVShow.addAll(listTVShowFull);
            }
        }
        notifyDataSetChanged();
    }
}
