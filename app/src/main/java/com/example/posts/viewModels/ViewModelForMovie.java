package com.example.posts.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.posts.model.Movies;

public class ViewModelForMovie extends ViewModel  {
    RepoMovieFilms repo ;

    public  LiveData<Movies> live  ;

    public ViewModelForMovie() {

        repo = new RepoMovieFilms();
        live = repo.liveMovie ;
    }

    public void getDataMovies()
    {
        repo.getDataFromServerMovie();
    }
}
