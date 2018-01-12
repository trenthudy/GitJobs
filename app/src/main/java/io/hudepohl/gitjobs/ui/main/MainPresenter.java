package io.hudepohl.gitjobs.ui.main;

import java.util.List;

import io.hudepohl.github_jobs.data.api.GitHubJobsInteractor;
import io.hudepohl.github_jobs.data.api.model.GitHubJob;

/**
 * Created by trent on 1/11/18.
 */

public class MainPresenter implements GitHubJobsInteractor.Presenter {

    private MainPresenter.View mView;
    private GitHubJobsInteractor mGitHubJobsModel;

    private int page = 1;

    MainPresenter(MainPresenter.View view) {
        mView = view;
        mGitHubJobsModel = new GitHubJobsInteractor(this);
    }

    void init() {
        page = 1;
        mView.showPageLoadingDialog();
        mGitHubJobsModel.getGitHubJobsList(page);
    }

    void refresh() {
        page = 1;
        mGitHubJobsModel.getGitHubJobsList(page);
    }

    void getNextPage() {
        mView.showPageLoadingDialog();
        mGitHubJobsModel.getGitHubJobsList(++page);
    }

    @Override
    public void onGetJobListSuccess(List<GitHubJob> jobList) {
        if (page == 1) {
            mView.initializeJobList(jobList);
        } else {
            mView.addJobsToList(jobList);
        }

        mView.hidePageLoadingDialog();
    }

    @Override
    public void onGetJobListFailure() {
        mView.showGetJobListError();
    }

    @Override
    public void onGetJobSuccess(GitHubJob job) {

    }

    @Override
    public void onGetJobFailure() {

    }

    interface View {

        void initializeJobList(List<GitHubJob> jobs);
        void addJobsToList(List<GitHubJob> jobs);
        void showPageLoadingDialog();
        void hidePageLoadingDialog();
        void showGetJobListError();

    }

}
