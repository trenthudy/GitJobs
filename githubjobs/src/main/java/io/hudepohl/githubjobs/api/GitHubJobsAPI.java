package io.hudepohl.githubjobs.api;

import java.util.List;

import io.hudepohl.githubjobs.obj.GitHubJob;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.GET;

/**
 * Created by trent on 1/11/18.
 */

public interface GitHubJobsAPI {

    String BASE_URL = "https://jobs.github.com";

    @GET("/positions.json")
    Observable<List<GitHubJob>> getJobList();

    @GET("/positions/{job_id}.json")
    Observable<GitHubJob> getJob(
            @Field("job_id") String jobId
    );

}
