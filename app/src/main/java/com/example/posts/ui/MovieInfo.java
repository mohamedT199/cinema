package com.example.posts.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.posts.R;
import com.example.posts.adapter.AdapterRecycler;
import com.example.posts.model.Movies;
import com.example.posts.model.Utils;
import com.example.posts.viewModels.RepoViewModelInfoMovie;
import com.example.posts.viewModels.ViewModelForMovie;
import com.example.posts.viewModels.ViewModelInfoMovie;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieInfo extends AppCompatActivity {
    int id_movie ;

    AdapterRecycler adapter ;
    RecyclerView rec_over_view ;
    TextView txTitle , txAdalt , txDes , txVote ;
    ViewModelInfoMovie model ;
    ViewModelForMovie modelMovies ;
    List<Movies> list  ;
    ConstraintLayout cons ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);
        Intent intent = new Intent();
        id_movie = intent.getIntExtra("id" , -1 );
        intiView();
        getDataToSeen();
        setRecTogo();



    }

    public void getDataToSeen()
    {
        if (id_movie != -1 )
        {
            model.getInfo(id_movie);
            model.liveData.observe(this, new Observer<com.example.posts.model.MovieInfo>() {
                @Override
                public void onChanged(com.example.posts.model.MovieInfo movieInfo) {
                    txTitle.setText(" Title " + movieInfo.getTitle());
                    txAdalt.setText(" Adult " + movieInfo.isAdult());
                    txDes.setText("Home Page : "+ movieInfo.getHomepage() +"\n"+movieInfo.getOverview());
                    txVote.setText("Vote : " + movieInfo.getVote_average());
                    Drawable dr = new BitmapDrawable(MovieInfo.this.getResources() ,
                            getBitmapFromURL(Utils.BASE_IMAGE+movieInfo.getPoster_path()));
                    cons.setBackground(dr);

                }
            });
        }


    }
    protected void intiView()
    {
        txAdalt = findViewById(R.id.text_adalt);
        txTitle = findViewById(R.id.title_film);
        txDes = findViewById(R.id.real_des) ;
        txVote = findViewById(R.id.vote_movie);
        model = new ViewModelProvider(this).get(ViewModelInfoMovie.class);
        modelMovies = new ViewModelProvider(this).get(ViewModelForMovie.class);
        rec_over_view = findViewById(R.id.rec_films_rec);
        adapter = new AdapterRecycler(this) ;
        list = new ArrayList<>() ;
        cons = findViewById(R.id.layout_info_movie);
        rec_over_view.setLayoutManager(new LinearLayoutManager(this , RecyclerView.HORIZONTAL , true));
    }
    protected void setRecTogo()
    {
        modelMovies.getDataMovies();
        modelMovies.live.observe(this, new Observer<Movies>() {
            @Override
            public void onChanged(Movies movies) {

                list.add(movies);
                adapter.setList(list);
                rec_over_view.setAdapter(adapter);
            }
        });
    }
    protected void oncliik()
    {
        adapter.setOnCllick(new AdapterRecycler.onClickLis() {
            @Override
            public void onclick(int postion) {
                if (postion >=0 )
                {
                    Intent intent = new Intent(MovieInfo.this , MovieInfo.class);
                    intent.putExtra("id", list.get(0).getResults().get(postion).getId());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public Bitmap getBitmapFromURL(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
