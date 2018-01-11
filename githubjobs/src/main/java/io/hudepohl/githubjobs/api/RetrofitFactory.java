package io.hudepohl.githubjobs.api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by trent on 1/11/18.
 */

public class RetrofitFactory {

    public static <T> T getRetrofitService(final Class<T> clazz, String baseURL) {

        return new Retrofit.Builder()
                .baseUrl(baseURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(clazz);
    }

}
