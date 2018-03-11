package com.example.ibrahim.popularmoviesstage2.api;

import android.support.annotation.NonNull;

import com.example.ibrahim.popularmoviesstage2.BuildConfig;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.ibrahim.popularmoviesstage2.api.MovieDbApiClient.API_BASE_URL;

/**
 * Created by ibrahim on 3/12/18.
 */

public class MovieDbApiClientSingleton {
    private static MovieDbApiClient apiClient;

    public static MovieDbApiClient getApiClient() {
        if (apiClient == null) {
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
                            Request original = chain.request();
                            HttpUrl originalHttpUrl = original.url();

                            HttpUrl url = originalHttpUrl.newBuilder()
                                    .addQueryParameter("api_key", BuildConfig.MOVIES_DB_API_KEY)
                                    .build();

                            // Request customization: add request headers
                            Request.Builder requestBuilder = original.newBuilder()
                                    .url(url);

                            Request request = requestBuilder.build();
                            return chain.proceed(request);
                        }
                    }).addInterceptor(new StethoInterceptor());

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClientBuilder.build())
                    .build();

            apiClient = retrofit.create(MovieDbApiClient.class);
        }
        return apiClient;
    }
}