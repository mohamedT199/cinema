package com.example.posts.ui;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.posts.R;
import com.example.posts.adapter.AdapterRecycler;
import com.example.posts.model.Movies;
import com.example.posts.viewModels.ViewModelForMovie;

import java.util.ArrayList;
import java.util.List;


public class MovieFrag extends Fragment {

    RecyclerView recyclerView ;
    AdapterRecycler adapterRecycler ;
    ViewModelForMovie viewModelForMovie ;
    List<Movies> list = new ArrayList<>();

    public MovieFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_movie, container, false) ;

        adapterRecycler = new AdapterRecycler(v.getContext());
        viewModelForMovie = new ViewModelProvider(requireActivity()).
                get(ViewModelForMovie.class);


        viewModelForMovie.getDataMovies() ;
        viewModelForMovie.live.observe(requireActivity(), new Observer<Movies>() {
            @Override
            public void onChanged(Movies movies) {
                list.add(movies);
                adapterRecycler.setList(list);

            }
        });
        return v ;

    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setAdapter(adapterRecycler);
        onclick();

    }

    public void IntiView(View v )
    {
        recyclerView = v.findViewById(R.id.rec_for_movies_screen);
        recyclerView.setLayoutManager(new GridLayoutManager( v.getContext(), 2 ));
    }
    public void onclick()
    {
        adapterRecycler.setOnCllick(new AdapterRecycler.onClickLis() {
            @Override
            public void onclick(int postion) {
                if (postion >= 0 ) {
                    Intent intent = new Intent(getContext(), MovieInfo.class);
                    intent.putExtra("id", list.get(0).getResults().get(postion).getId());
                    startActivity(intent);
                }
            }
        });
    }
}
