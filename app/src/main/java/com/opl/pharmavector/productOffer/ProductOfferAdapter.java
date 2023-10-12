package com.opl.pharmavector.productOffer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.opl.pharmavector.R;

import java.util.List;

public class ProductOfferAdapter extends RecyclerView.Adapter<ProductOfferAdapter.ProductOfferViewHolder> {
    public List<ProductOfferList> productOfferLists;

    public ProductOfferAdapter(Context context, List<ProductOfferList> productOfferList) {
        this.productOfferLists = productOfferList;
    }

    @NonNull
    @Override
    public ProductOfferViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_offer_row, viewGroup, false);
        return new ProductOfferViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductOfferViewHolder holder, int position) {
        ProductOfferList offerModel = productOfferLists.get(position);
        holder.productOfferSl.setText(String.valueOf(offerModel.getSl()));
        holder.productOfferCode.setText(offerModel.getId());
        holder.productOfferName.setText(offerModel.getName());
        holder.productDiscount.setText(offerModel.getProdVat());
    }

    @Override
    public int getItemCount() {
        return productOfferLists.size();
    }

    public static class ProductOfferViewHolder extends RecyclerView.ViewHolder {
        public TextView productOfferSl, productOfferCode, productOfferName, productDiscount;

        public ProductOfferViewHolder(View view) {
            super(view);
            productOfferSl = view.findViewById(R.id.productOfferSl);
            productOfferCode = view.findViewById(R.id.productOfferCode);
            productOfferName = view.findViewById(R.id.productOfferName);
            productDiscount = view.findViewById(R.id.productDiscount);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void searchOfferList(List<ProductOfferList> offerList) {
        productOfferLists = offerList;
        notifyDataSetChanged();
    }
}
