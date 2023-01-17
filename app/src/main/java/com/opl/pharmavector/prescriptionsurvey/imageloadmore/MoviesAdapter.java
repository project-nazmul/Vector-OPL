package com.opl.pharmavector.prescriptionsurvey.imageloadmore;

import android.content.Context;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.PhotoView;
import com.opl.pharmavector.R;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final int TYPE_MOVIE = 0;
    public final int TYPE_LOAD = 1;
    static Context context;
    List<MovieModel> movies;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;


    public MoviesAdapter(Context context, List<MovieModel> movies) {

        this.context = context;
        this.movies = movies;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == TYPE_MOVIE) {
            return new MovieHolder(inflater.inflate(R.layout.row_movie, parent, false));
        } else {
            return new LoadHolder(inflater.inflate(R.layout.row_load, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (position >= getItemCount() - 1 && isMoreDataAvailable && !isLoading && loadMoreListener != null) {
            isLoading = true;
            loadMoreListener.onLoadMore();
        }

        if (getItemViewType(position) == TYPE_MOVIE) {
            ((MovieHolder) holder).bindData(movies.get(position));
        }
        //No else part needed as load holder doesn't bind any data
    }

    @Override
    public int getItemViewType(int position) {
        if (movies.get(position).type.equals("movie")) {
            return TYPE_MOVIE;
        } else {
            return TYPE_LOAD;
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


    class MovieHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvRating,tvbrandcount;
        PhotoView mPicture;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] imageBytes = baos.toByteArray();

        public MovieHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.title);
            tvRating = itemView.findViewById(R.id.rating);
            tvbrandcount = itemView.findViewById(R.id.tvbrandcount);
            mPicture = itemView.findViewById(R.id.mPicture);
        }
        void bindData(MovieModel movieModel) {

            tvTitle.setText(movieModel.title);
            tvRating.setText( movieModel.rating);
            tvbrandcount.setText("No of Brand:\t " + movieModel.brandcount);

            String doctor_image = (movieModel.picture);
            doctor_image = doctor_image.replaceAll(" ", "");
            
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.skipMemoryCache(true);
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
            requestOptions.error(R.drawable.ic_imageload);
            Glide.with(context)
                    .load(doctor_image)
                    .apply(requestOptions)
                    .into(mPicture);


        }
    }

    static class LoadHolder extends RecyclerView.ViewHolder {
        public LoadHolder(View itemView) {
            super(itemView);
        }
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }


    public void notifyDataChanged() {
        notifyDataSetChanged();
        isLoading = false;
    }

    public void loadDestroy() {
        isLoading = false;
    }


    interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }
}
