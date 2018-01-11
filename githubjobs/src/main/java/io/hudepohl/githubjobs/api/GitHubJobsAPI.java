package io.hudepohl.githubjobs.api;

import java.util.List;

import io.hudepohl.githubjobs.obj.Job;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.GET;

/**
 * Created by trent on 1/11/18.
 */

public interface GitHubJobsAPI {

    String BASE_URL = "https://jobs.github.com";

    @GET("/positions.json")
    Observable<List<Job>> getJobList();

    @GET("/positions/{job_id}.json")
    Observable<Job> getJob(
            @Field("job_id") String jobId
    );

}
