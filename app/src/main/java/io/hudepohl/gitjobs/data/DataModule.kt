package io.hudepohl.gitjobs.data

import dagger.Module
import dagger.Provides
import io.hudepohl.gitjobs.data.githubJobs.GitHubJobsAPI
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideGitHubJobsAPI(): GitHubJobsAPI {

        return Retrofit.Builder()
                .baseUrl(GitHubJobsAPI.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GitHubJobsAPI::class.java)
    }
}
