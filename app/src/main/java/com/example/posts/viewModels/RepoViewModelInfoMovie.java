package com.example.posts.viewModels;

import androidx.annotation.MainThread;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.posts.model.MovieInfo;
import com.example.posts.retrofit.GetRetrofit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RepoViewModelInfoMovie extends ViewModel {

    public  MutableLiveData<MovieInfo> liveInfo = new MutableLiveData<>() ;

    public void getInfoMovie(int id )
    {
        GetRetrofit.getConection().
                getMovieInfo(id).
                observeOn(Schedulers.io()).
                subscribeOn(AndroidSchedulers.mainThread()).
                subscribe(new SingleObserver<MovieInfo>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull MovieInfo movieInfo) {

                        liveInfo.postValue(movieInfo);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}
