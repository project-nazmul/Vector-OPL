package com.opl.pharmavector.mpoMenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.opl.pharmavector.R;
import com.opl.pharmavector.achieve.AchieveEarningList;
import com.opl.pharmavector.remote.ApiClient;

import java.util.ArrayList;

import retrofit2.Callback;

public class MPOMenuAdapter extends RecyclerView.Adapter<MPOMenuAdapter.MPOMenuViewHolder> {
    public ArrayList<MPOMenuList> mpoMenuLists;
    //private ArrayList<AchieveEarningList> checkAchieveLists = new ArrayList<>();
    Context context;
    String pmdImageUrl = ApiClient.BASE_URL+"vector_ff_image/sales/", profileImage, userRole, designationType;
    MenuItemCallback mpoMenuModel;

    public MPOMenuAdapter(Context context, ArrayList<MPOMenuList> mpoMenuLists, MenuItemCallback mpoMenuModel) {
        this.context = context;
        this.mpoMenuLists = mpoMenuLists;
        this.mpoMenuModel = mpoMenuModel;
    }

    @NonNull
    @Override
    public MPOMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mpo_menu_row, parent, false);
        return new MPOMenuViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MPOMenuViewHolder holder, int position) {
        MPOMenuList mpoMenuList = mpoMenuLists.get(position);

        holder.tv_menu.setText(mpoMenuList.getMenuDesc());
        //holder.tvEmployeeName.setText(achieveModel.getEmpName());
        //holder.tvFFName.setText(achieveModel.getFfName());
        //profileImage = pmdImageUrl+achieveModel.getEmpCode()+"."+"jpg" ;
        //Picasso.get().load(profileImage).into(holder.imgPmdContact);

//        holder.card_menu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                contactCallback.onContactPhoneCall(achieveModel);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mpoMenuLists.size();
    }

    public class MPOMenuViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_menu;
        public ImageButton img_menu;
        public Button btn_menu;
        public CardView card_menu;

        public MPOMenuViewHolder(View view) {
            super(view);
            tv_menu = (TextView)itemView.findViewById(R.id.tv_menu);
            img_menu = (ImageButton) itemView.findViewById(R.id.img_menu);
            btn_menu = (Button) itemView.findViewById(R.id.btn_menu);
            card_menu = (CardView) itemView.findViewById(R.id.card_menu);
        }
    }

    public interface MenuItemCallback {
        void onMenuItemList(MPOMenuModel mpoMenuModel);
    }
}
