package com.opl.pharmavector.pmdVector.adapter;

import android.annotation.SuppressLint;
import android.content.Context;


import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.PhotoView;
import com.opl.pharmavector.R;
import com.opl.pharmavector.pmdVector.model.RXModel;


import java.io.ByteArrayOutputStream;
import java.text.MessageFormat;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public final int TYPE_MOVIE = 0;
    public final int TYPE_LOAD = 1;
    @SuppressLint("StaticFieldLeak")
    static Context context;
    List<RXModel> movies;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;


    public MoviesAdapter(Context context, List<RXModel> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == TYPE_MOVIE) {
            return new MovieHolder(inflater.inflate(R.layout.adapter_pmd_rx_load, parent, false));
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

    static class MovieHolder extends RecyclerView.ViewHolder {
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

        void bindData(RXModel movieModel) {
            if (movieModel.title.length()>19){
                tvTitle.setText(MessageFormat.format("Image source:\t\t{0}", movieModel.title));
                tvTitle.setTextColor(Color.rgb(153, 0, 0));
            } else {
                tvTitle.setText(MessageFormat.format("Capture time:\t\t\t{0}", movieModel.title));
                tvTitle.setTextColor(Color.rgb(0, 0, 153));
                tvTitle.setTypeface(null, Typeface.BOLD);
            }
            tvRating.setText(MessageFormat.format("Mpo code:\t {0}", movieModel.rating));
            tvbrandcount.setText(MessageFormat.format(" Brand:\t {0}", movieModel.brandcount));

            String doctor_image = (movieModel.picture);
            doctor_image = doctor_image.replaceAll(" ", "");

            RequestOptions requestOptions = new RequestOptions();
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

    @SuppressLint("NotifyDataSetChanged")
    public void notifyDataChanged() {
        notifyDataSetChanged();
        isLoading = false;
    }

    public void loadDestroy() {
        isLoading = false;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }
}
