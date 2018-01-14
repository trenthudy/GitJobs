package io.hudepohl.gitjobs.ui.main

import io.hudepohl.gitjobs.data.githubJobs.GitHubJobsAPI
import io.hudepohl.gitjobs.data.githubJobs.model.GitHubJob
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by trent on 1/11/18.
 */

class MainPresenter @Inject constructor() {

    private var view: MainPresenter.View? = null
    @Inject lateinit var api: GitHubJobsAPI

    fun attachView(viewToAttach: MainPresenter.View) {
        view = viewToAttach
    }

    fun detachView() {
        view = null
    }

    fun init() {

        api.getJobList(1)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<List<GitHubJob>> {

                    override fun onSubscribe(d: Disposable)     { view?.showPageLoadingDialog() }
                    override fun onNext(jobs: List<GitHubJob>)  { view?.initializeJobList(jobs) }
                    override fun onError(e: Throwable)          { view?.showGetJobListError()   }
                    override fun onComplete()                   { view?.hidePageLoadingDialog() }
                })
    }

    fun refresh() {

        api.getJobList(1)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<List<GitHubJob>> {

                    override fun onSubscribe(d: Disposable)     {  } // <-- no dialog on refresh
                    override fun onNext(jobs: List<GitHubJob>)  { view?.initializeJobList(jobs) }
                    override fun onError(e: Throwable)          { view?.showGetJobListError()   }
                    override fun onComplete()                   {  }
                })
    }

    fun nextPage(page: Int) {

        api.getJobList(page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<List<GitHubJob>> {

                    override fun onSubscribe(d: Disposable)     { view?.showPageLoadingDialog() }
                    override fun onNext(jobs: List<GitHubJob>)  { view?.addJobsToList(jobs)     }
                    override fun onError(e: Throwable)          { view?.showGetJobListError()   }
                    override fun onComplete()                   { view?.hidePageLoadingDialog() }
                })
    }

    interface View {
        fun initializeJobList(jobs: List<GitHubJob>)
        fun addJobsToList(jobs: List<GitHubJob>)
        fun showPageLoadingDialog()
        fun hidePageLoadingDialog()
        fun showGetJobListError()
    }
}
