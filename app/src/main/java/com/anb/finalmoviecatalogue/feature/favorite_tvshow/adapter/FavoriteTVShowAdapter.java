package com.anb.finalmoviecatalogue.feature.favorite_tvshow.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.anb.finalmoviecatalogue.R;
import com.anb.finalmoviecatalogue.data.model.Favorite;
import com.anb.finalmoviecatalogue.utils.Utils;

import java.util.ArrayList;

public class FavoriteTVShowAdapter extends RecyclerView.Adapter<FavoriteTVShowAdapter.GridViewHolder> {
    private Context context;
    private ArrayList<Favorite> listFavorite;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Favorite favorite);
    }

    public FavoriteTVShowAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        listFavorite = new ArrayList<Favorite>();
    }

    public ArrayList<Favorite> getListFavorite() {
        return listFavorite;
    }

    public void setListFavorite(ArrayList<Favorite> listFavorite) {
        this.listFavorite = listFavorite;
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_grid, viewGroup, false);
        return new GridViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int i) {
        holder.bind(listFavorite.get(i), listener);
    }
    @Override
    public int getItemCount() {
        return getListFavorite().size();
    }

    class GridViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;

        GridViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
        }

        void bind(final Favorite favorite, final OnItemClickListener listener) {
            Utils.setImage(favorite.getPoster_path(), imgPhoto);
            imgPhoto.setOnClickListener(v -> listener.onItemClick(favorite));
        }
    }
}
