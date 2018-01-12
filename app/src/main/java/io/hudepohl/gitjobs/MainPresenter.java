package io.hudepohl.gitjobs;

import java.util.List;

import io.hudepohl.githubjobs.model.GitHubJobsModel;
import io.hudepohl.githubjobs.obj.GitHubJob;

/**
 * Created by trent on 1/11/18.
 */

public class MainPresenter implements GitHubJobsModel.Presenter {

    private MainPresenter.View mView;
    private GitHubJobsModel mGitHubJobsModel;

    private int page = 1;

    MainPresenter(MainPresenter.View view) {
        mView = view;
        mGitHubJobsModel = new GitHubJobsModel(this);
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

        // TODO: Add validation to job list returned...

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
