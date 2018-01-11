package io.hudepohl.githubjobs.api;

import java.util.List;

import io.hudepohl.githubjobs.obj.GitHubJob;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by trent on 1/11/18.
 */

public interface GitHubJobsAPI {

    String BASE_URL = "https://jobs.github.com";

    @GET("/positions.json")
    Observable<List<GitHubJob>> getJobList(
            @Query("page") int page
    );

    @GET("/positions/{job_id}.json")
    Observable<GitHubJob> getJob(
            @Path("job_id") String jobId
    );

}
