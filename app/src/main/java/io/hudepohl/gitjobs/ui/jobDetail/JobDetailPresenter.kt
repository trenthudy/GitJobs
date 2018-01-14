package io.hudepohl.gitjobs.ui.jobDetail

import io.hudepohl.gitjobs.data.githubJobs.GitHubJobsAPI
import io.hudepohl.gitjobs.data.githubJobs.model.GitHubJob
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by trent on 1/12/18.
 */

class JobDetailPresenter @Inject constructor() {

    private var view: JobDetailPresenter.View? = null
    @Inject lateinit var api: GitHubJobsAPI

    fun attachView(viewToAttach: JobDetailPresenter.View) {
        view = viewToAttach
    }

    fun detachView() {
        view = null
    }

    fun getJobInfo(jobId: String) {

        api.getJob(jobId)
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
