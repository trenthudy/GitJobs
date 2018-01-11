package io.hudepohl.githubjobs.model;

import java.util.List;

import io.hudepohl.githubjobs.api.GitHubJobsAPI;
import io.hudepohl.githubjobs.api.RetrofitFactory;
import io.hudepohl.githubjobs.obj.Job;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by trent on 1/11/18.
 */

public class GitHubJobsModel {

    public void getGitHubJobsList(int page) {

        RetrofitFactory.getRetrofitService(GitHubJobsAPI.class, GitHubJobsAPI.BASE_URL)
                .getJobList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Job>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Job> jobs) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getGitHubJob(String gitHubJobId) {

        RetrofitFactory.getRetrofitService(GitHubJobsAPI.class, GitHubJobsAPI.BASE_URL)
                .getJob(gitHubJobId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Job>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Job job) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public interface Presenter {

        void onGetJobListSuccess(List<Job> jobList);
        void onGetJobListFailure();
        void onGetJobSuccess(Job job);
        void onGetJobFailure();

    }

}
