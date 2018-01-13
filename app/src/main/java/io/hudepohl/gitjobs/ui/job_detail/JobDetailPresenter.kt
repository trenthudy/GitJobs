package io.hudepohl.gitjobs.ui.job_detail

import io.hudepohl.githubJobs.data.data.GitHubJobsInteractor
import io.hudepohl.githubJobs.data.data.model.GitHubJob

/**
 * Created by trent on 1/12/18.
 */

class JobDetailPresenter internal constructor(
        private val mView: JobDetailPresenter.View) : GitHubJobsInteractor.Presenter {

    private val mGitHubJobsModel: GitHubJobsInteractor = GitHubJobsInteractor(this)

    fun getJobInfo(jobId: String) {
        mGitHubJobsModel.getGitHubJob(jobId)
    }

    override fun onGetJobListSuccess(jobList: List<GitHubJob>) {

    }

    override fun onGetJobListFailure() {

    }

    override fun onGetJobSuccess(job: GitHubJob) {
        mView.configureJobInfo(job)
    }

    override fun onGetJobFailure() {
        mView.showFetchJobInfoError()
    }

    internal interface View {
        fun configureJobInfo(job: GitHubJob)
        fun showFetchJobInfoError()
    }
}
