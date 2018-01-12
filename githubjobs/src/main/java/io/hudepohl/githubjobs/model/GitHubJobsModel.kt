package io.hudepohl.githubjobs.model

import io.hudepohl.githubjobs.api.GitHubJobsAPI
import io.hudepohl.githubjobs.api.RetrofitFactory
import io.hudepohl.githubjobs.obj.GitHubJob
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by trent on 1/11/18.
 */

class GitHubJobsModel(private val mPresenter: GitHubJobsModel.Presenter) {

    fun getGitHubJobsList(page: Int) {

        RetrofitFactory.getRetrofitService<GitHubJobsAPI>(GitHubJobsAPI::class.java, GitHubJobsAPI.BASE_URL)
                .getJobList(page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<List<GitHubJob>> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(jobs: List<GitHubJob>) {
                        mPresenter.onGetJobListSuccess(jobs)
                    }

                    override fun onError(e: Throwable) {
                        mPresenter.onGetJobListFailure()
                    }

                    override fun onComplete() {

                    }
                })
    }

    fun getGitHubJob(gitHubJobId: String) {

        RetrofitFactory.getRetrofitService<GitHubJobsAPI>(GitHubJobsAPI::class.java, GitHubJobsAPI.BASE_URL)
                .getJob(gitHubJobId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<GitHubJob> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(job: GitHubJob) {
                        mPresenter.onGetJobSuccess(job)
                    }

                    override fun onError(e: Throwable) {
                        mPresenter.onGetJobFailure()
                    }

                    override fun onComplete() {

                    }
                })
    }

    interface Presenter {

        fun onGetJobListSuccess(jobList: List<GitHubJob>)
        fun onGetJobListFailure()
        fun onGetJobSuccess(job: GitHubJob)
        fun onGetJobFailure()

    }

}
