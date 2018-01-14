package io.hudepohl.gitjobs.ui.jobDetail

import io.hudepohl.gitjobs.data.githubJobs.GitHubJobsAPI
import io.hudepohl.gitjobs.data.githubJobs.model.GitHubJob
import io.hudepohl.gitjobs.ui.BasePresenter
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by trent on 1/12/18.
 */

class JobDetailPresenter @Inject constructor() : BasePresenter<JobDetailPresenter.View>() {

    @Inject lateinit var api: GitHubJobsAPI

    fun jobInfo(jobId: String) {

        api.getJobById(jobId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<GitHubJob> {

                    override fun onSubscribe(d: Disposable) { }
                    override fun onNext(job: GitHubJob)     { view?.configureJobInfo(job)   }
                    override fun onError(e: Throwable)      { view?.showFetchJobInfoError() }
                    override fun onComplete()               { }
                })
    }

    interface View {
        fun configureJobInfo(job: GitHubJob)
        fun showFetchJobInfoError()
    }
}
