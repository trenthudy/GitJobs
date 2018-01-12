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

    public MainPresenter(MainPresenter.View view) {

        mView = view;
        mGitHubJobsModel = new GitHubJobsModel(this);
    }

    void init() {

    }

    @Override
    public void onGetJobListSuccess(List<GitHubJob> jobList) {

    }

    @Override
    public void onGetJobListFailure() {

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
