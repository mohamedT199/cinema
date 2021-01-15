package com.example.posts.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.posts.model.MovieInfo;

public class ViewModelInfoMovie extends ViewModel {
    RepoViewModelInfoMovie repo ;
    public  LiveData<MovieInfo> liveData ;

    public ViewModelInfoMovie() {
        repo = new RepoViewModelInfoMovie();
        liveData = repo.liveInfo  ;
    }

    public void getInfo(int id )
    {
        repo.getInfoMovie(id);
    }
}
