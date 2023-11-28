package com.opl.pharmavector;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.opl.pharmavector.R;
import com.opl.pharmavector.Contact1;
import com.squareup.picasso.Picasso;

import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> implements Filterable {
    private Context context;
    private List<com.opl.pharmavector.Contact1> contactList;
    private List<com.opl.pharmavector.Contact1> contactListFiltered;
    private ContactsAdapterListener listener;
    public static ArrayList<String> selectedphonenumber = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, phone, territory;
        public ImageView thumbnail;
        protected CheckBox chbContent;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            phone = view.findViewById(R.id.phone);
            territory = view.findViewById(R.id.territory);

            chbContent = view.findViewById(R.id.chbContent);
            thumbnail = view.findViewById(R.id.thumbnail);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //send selected contact in callback
                    listener.onContactSelected(contactListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }

    public ContactsAdapter(Context context, List<com.opl.pharmavector.Contact1> contactList, ContactsAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_row_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        //final Contact contact = contactListFiltered.get(position);
        holder.name.setText(contactListFiltered.get(position).getName());
        holder.phone.setText(contactListFiltered.get(position).getPhone());
        holder.territory.setText(contactListFiltered.get(position).getterritory());
        //holder.chbContent.setText("Checkbox " + position);
        holder.chbContent.setChecked(contactList.get(position).getSelected());
        holder.chbContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer pos = (Integer) holder.chbContent.getTag();
                //Toast.makeText(context, contactListFiltered.get(position).getPhone() + " clicked!", Toast.LENGTH_SHORT).show();
                if (contactListFiltered.get(position).getSelected()) {
                    contactListFiltered.get(position).setSelected(false);
                    selectedphonenumber.remove(contactListFiltered.get(position).getPhone());
                    Log.e("selectedphonenumber", String.valueOf(selectedphonenumber));
                } else {
                    contactListFiltered.get(position).setSelected(true);
                    selectedphonenumber.add(contactListFiltered.get(position).getPhone());
                    Log.e("selectedphonenumber", String.valueOf(selectedphonenumber));
                }
            }
        });
        Glide.with(context)
                .load("http://opsonin.com.bd/vector_opl_v1/demo_web/doctor.png")
                .apply(RequestOptions.circleCropTransform())
                .into(holder.thumbnail);
/*
        Picasso.get()
                .load("http://opsonin.com.bd/vector_opl_v1/demo_web/doctor.png")
                .centerCrop()
                .into(holder.thumbnail);

        */
    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    contactListFiltered = contactList;
                } else {
                    List<com.opl.pharmavector.Contact1> filteredList = new ArrayList<>();
                    for (com.opl.pharmavector.Contact1 row : contactList) {
                        //name match condition. this might differ depending on your requirement
                        //here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getPhone().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }
                    contactListFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<com.opl.pharmavector.Contact1>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactsAdapterListener {
        void onContactSelected(com.opl.pharmavector.Contact1 contact);
    }
}
