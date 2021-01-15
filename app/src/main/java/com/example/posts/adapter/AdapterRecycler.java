package com.example.posts.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.posts.R;
import com.example.posts.model.Movies;
import com.example.posts.model.Utils;
import com.example.posts.ui.MovieInfo;

import java.util.List;

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.Holder> {
    Context con ;

    public void setOnCllick(onClickLis on)
    {
        onclick = on  ;
    }

    private onClickLis onclick ;
    public interface  onClickLis
    {
        public void onclick(int postion) ;
    }

    public AdapterRecycler(Context con ) {
        this.con = con ;
    }

    List<Movies> list ;

    public void setList(List<Movies> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_item , parent , false);
        return new Holder(v , onclick);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        holder.title.setText(list.get(0).getResults().get(position).getOriginal_title());
        Glide.with(con).
                load(Utils.BASE_IMAGE +list.get(0).getResults().get(position).getPoster_path() ).
                into(holder.img);


    }

    @Override
    public int getItemCount() {
        if (list == null)
        {
            return 0 ;
        }
        else
        {
            return  list.size();
        }

    }

    class Holder extends RecyclerView.ViewHolder
    {
        ImageView img ;
        TextView title ;

        public Holder(@NonNull final View itemView , final onClickLis liss) {
            super(itemView);
            img = itemView.findViewById(R.id.img_film);
            title = itemView.findViewById(R.id.title_film);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (liss!=null)
                    {
                        liss.onclick(getAdapterPosition());
                    }
                }
            });
        }
    }
}
