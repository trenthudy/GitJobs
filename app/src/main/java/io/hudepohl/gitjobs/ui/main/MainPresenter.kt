package io.hudepohl.gitjobs.ui.main

import io.hudepohl.githubJobs.data.data.GitHubJobsInteractor
import io.hudepohl.githubJobs.data.data.model.GitHubJob

/**
 * Created by trent on 1/11/18.
 */

class MainPresenter internal constructor(
        private val mView: MainPresenter.View) : GitHubJobsInteractor.Presenter {

    private val mGitHubJobsModel: GitHubJobsInteractor = GitHubJobsInteractor(this)

    private var page = 1

    fun init() {
        page = 1
        mView.showPageLoadingDialog()
        mGitHubJobsModel.getGitHubJobsList(page)
    }

    fun refresh() {
        page = 1
        mGitHubJobsModel.getGitHubJobsList(page)
    }

    fun getNextPage() {
        mView.showPageLoadingDialog()
        mGitHubJobsModel.getGitHubJobsList(++page)
    }

    override fun onGetJobListSuccess(jobList: List<GitHubJob>) {
        if (page == 1) {
            mView.initializeJobList(jobList)
        } else {
            mView.addJobsToList(jobList)
        }

        mView.hidePageLoadingDialog()
    }

    override fun onGetJobListFailure() {
        mView.showGetJobListError()
    }

    override fun onGetJobSuccess(job: GitHubJob) {

    }

    override fun onGetJobFailure() {

    }

    internal interface View {
        fun initializeJobList(jobs: List<GitHubJob>)
        fun addJobsToList(jobs: List<GitHubJob>)
        fun showPageLoadingDialog()
        fun hidePageLoadingDialog()
        fun showGetJobListError()
    }
}
