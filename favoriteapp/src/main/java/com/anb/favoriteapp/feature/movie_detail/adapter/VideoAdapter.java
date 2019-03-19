package com.anb.favoriteapp.feature.movie_detail.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anb.favoriteapp.data.model.Video;
import com.anb.favoriteapp.R;
import com.anb.favoriteapp.utils.Utils;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoHolder> {

    private ArrayList<Video> videoList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onVideoClick(Video video);
    }

    public VideoAdapter(OnItemClickListener listener) {
        this.videoList = new ArrayList<>();
        this.listener = listener;
    }

    public ArrayList<Video> getVideoList() {
        return videoList;
    }

    public void setVideoList(ArrayList<Video> videoList) {
        this.videoList = videoList;
    }

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_row, parent, false);
        return new VideoHolder(v);
    }

    @Override
    public void onBindViewHolder(VideoHolder holder, int position) {
        holder.bind(videoList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class VideoHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        ImageView imgThumb;
        View view;

        public VideoHolder(View itemView) {
            super(itemView);
            view = itemView;
            txtName = itemView.findViewById(R.id.name);
            imgThumb = itemView.findViewById(R.id.imgThumb);
        }

        public void bind(Video video, OnItemClickListener listener) {
            txtName.setText(video.getName());
            String url = "https://img.youtube.com/vi/" + video.getKey() + "/default.jpg";
            Utils.setImageYoutube(url,imgThumb);
            view.setOnClickListener(v -> listener.onVideoClick(video));
        }
    }
}
