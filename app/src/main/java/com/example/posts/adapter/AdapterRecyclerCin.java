package com.example.posts.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posts.R;

import java.util.List;

public class AdapterRecyclerCin extends RecyclerView.Adapter<AdapterRecyclerCin.Holder> {
    List<String> list ;

    public void setList(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cinema_item , parent , false);
        return new Holder(v);


    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if (list == null)
        {
            return list.size() ;
        }
        else
        {
            return  list.size();
        }
    }

    class Holder extends RecyclerView.ViewHolder
    {
        TextView cinma , cdrress ;

        public Holder(@NonNull View itemView) {
            super(itemView);
            cinma = itemView.findViewById(R.id.cinma_name);
            cdrress = itemView.findViewById(R.id.cinma_address);
        }
    }
}
