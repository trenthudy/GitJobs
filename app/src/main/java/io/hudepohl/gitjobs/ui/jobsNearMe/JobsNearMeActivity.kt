package io.hudepohl.gitjobs.ui.jobsNearMe

import android.os.Bundle
import io.hudepohl.gitjobs.data.githubJobs.model.GitHubJob
import io.hudepohl.gitjobs.ui.BaseActivity
import javax.inject.Inject

/**
 * Created by trent on 1/14/18.
 */

class JobsNearMeActivity : BaseActivity(), JobsNearMePresenter.View {

    @Inject lateinit var presenter: JobsNearMePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)

        super.onCreate(savedInstanceState)

        presenter.bind(this)
        presenter.getJobsNearMe()
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }

    override fun initJobsList(jobs: List<GitHubJob>) {

    }

    override fun showNoJobsNearMeMsg() {

    }

    override fun showLocationError() {

    }
}