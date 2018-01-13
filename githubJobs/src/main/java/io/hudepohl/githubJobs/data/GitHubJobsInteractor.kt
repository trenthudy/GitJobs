package io.hudepohl.githubJobs.data

import io.hudepohl.githubJobs.http.RetrofitFactory
import io.hudepohl.githubJobs.data.model.GitHubJob
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by trent on 1/11/18.
 */

class GitHubJobsInteractor(private val mPresenter: Presenter) {

    fun getGitHubJobsList(page: Int) {
        RetrofitFactory.getRetrofitService(GitHubJobsAPIService::class.java, GitHubJobsAPIService.BASE_URL)
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
        RetrofitFactory.getRetrofitService(GitHubJobsAPIService::class.java,GitHubJobsAPIService.BASE_URL)
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
