package com.example.posts.retrofit;

import com.example.posts.model.MovieInfo;
import com.example.posts.model.Movies;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RequestsToServer {

    @GET("discover/movie")
    Single<Movies> getMovies() ;

    @GET("movie/{id}")
    Single<MovieInfo> getMovieInfo(@Path("id") int id ) ;
}
