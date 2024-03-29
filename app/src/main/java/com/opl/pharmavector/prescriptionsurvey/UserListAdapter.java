package com.opl.pharmavector.prescriptionsurvey;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.opl.pharmavector.R;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.opl.pharmavector.R;
import com.opl.pharmavector.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.CustomViewHolder> {
    private List<UserModel> imageModelsList;
    private Context mContext;
    private Activity act;

    public UserListAdapter(Context context, ArrayList<UserModel> imageList, Activity activity) {
        this.imageModelsList = imageList;
        this.mContext = context;
        this.act = activity;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.rx_search_row, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
        final UserModel get = imageModelsList.get(position);
        holder.name.setText(get.getName());
        final String image = get.getImage();

        RequestOptions options = new RequestOptions();
        Glide.with(this.mContext)
                .load(image)
                .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(10)))
                .into(holder.image);
        holder.name.setText(get.getName());
    }

    @Override
    public int getItemCount() {
        return (null != imageModelsList ? imageModelsList.size() : 0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;

        public CustomViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            name = (TextView) itemView.findViewById(R.id.name);
        }
    }
}
