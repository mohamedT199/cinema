package com.example.posts.retrofit;

import com.example.posts.model.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Url;

public class GetRetrofit {

    private static Retrofit retrofit ;

    private static Retrofit getRetrofitInstanse()
    {

        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient();
            client.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {

                    Request request = chain.request();
                    HttpUrl url = request.url().newBuilder().addQueryParameter("api_key", Utils.API_KEY).build();

                    Response response = chain.proceed(request);
                    return null;
                }
            });
            retrofit = new Retrofit.Builder().
                    baseUrl(Utils.BASE_URL).
                    addConverterFactory(GsonConverterFactory.create()).
                    client(client).
                    build();
        }
        return retrofit ;
    }

    public static  RequestsToServer getConection()
    {
        RequestsToServer con = getRetrofitInstanse().create(RequestsToServer.class);
        return con ;
    }
}
