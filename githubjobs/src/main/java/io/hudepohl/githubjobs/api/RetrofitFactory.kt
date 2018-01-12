package io.hudepohl.githubjobs.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by trent on 1/11/18.
 */

object RetrofitFactory {

    fun <T> getRetrofitService(clazz: Class<T>, baseURL: String): T {

        return Retrofit.Builder()
                .baseUrl(baseURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(clazz)
    }

}
