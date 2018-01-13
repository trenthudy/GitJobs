package io.hudepohl.gitjobs.ui.job_detail;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.hudepohl.github_jobs.data.data.GitHubJobsInteractor;
import io.hudepohl.github_jobs.data.data.model.GitHubJob;

/**
 * Created by trent on 1/12/18.
 */

public class JobDetailPresenter implements GitHubJobsInteractor.Presenter {

    private JobDetailPresenter.View mView;
    private GitHubJobsInteractor mGitHubJobsModel;

    JobDetailPresenter(JobDetailPresenter.View view) {
        mView = view;
        mGitHubJobsModel = new GitHubJobsInteractor(this);
    }

    void getJobInfo(String jobId) {
        mGitHubJobsModel.getGitHubJob(jobId);
    }

    @Override
    public void onGetJobListSuccess(@NotNull List<GitHubJob> jobList) {

    }

    @Override
    public void onGetJobListFailure() {

    }

    @Override
    public void onGetJobSuccess(@NotNull GitHubJob job) {
        mView.configureJobInfo(job);
    }

    @Override
    public void onGetJobFailure() {
        mView.showFetchJobInfoError();
    }

    interface View {

        void configureJobInfo(GitHubJob job);
        void showFetchJobInfoError();

    }

}
