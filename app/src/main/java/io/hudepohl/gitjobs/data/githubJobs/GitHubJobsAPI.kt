package io.hudepohl.gitjobs.data.githubJobs

import io.hudepohl.gitjobs.data.githubJobs.model.GitHubJob
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by trent on 1/11/18.
 */

interface GitHubJobsAPI {

    companion object {
        const val BASE_URL = "https://jobs.github.com"
    }

    @GET("/positions.json")
    fun getJobList(
            @Query("page") page: Int
    ): Observable<List<GitHubJob>>

    @GET("/positions/{job_id}.json")
    fun getJob(
            @Path("job_id") jobId: String
    ): Observable<GitHubJob>
}
