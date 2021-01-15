package com.example.posts.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.posts.model.Movies;
import com.example.posts.retrofit.GetRetrofit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RepoMovieFilms extends ViewModel {

    MutableLiveData<Movies> liveMovie = new MutableLiveData<>() ;

    public void getDataFromServerMovie()
    {
        GetRetrofit.getConection().
                getMovies().
                observeOn(Schedulers.io()).
                subscribeOn(AndroidSchedulers.mainThread()).
                subscribe(new SingleObserver<Movies>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Movies movies) {
                        liveMovie.postValue(movies);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {


                    }
                });
    }
}
