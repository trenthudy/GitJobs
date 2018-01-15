package io.hudepohl.gitjobs.ui.jobsNearMe

import android.os.Bundle
import android.view.View
import android.widget.Toast
import io.hudepohl.gitjobs.R
import io.hudepohl.gitjobs.data.githubJobs.model.GitHubJob
import io.hudepohl.gitjobs.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_jobs_near_me.*
import kotlinx.android.synthetic.main.progress.*
import javax.inject.Inject

/**
 * Created by trent on 1/14/18.
 */

class JobsNearMeActivity : BaseActivity(), JobsNearMePresenter.View {

    @Inject lateinit var presenter: JobsNearMePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jobs_near_me)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.lbl_jobs_near_me)

        presenter.bind(this)
        presenter.getJobsNearMe()
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun displayLocation(lat: Double, long: Double) {
        jobsNearMeResultsLayout.visibility = View.VISIBLE

        val msg = getString(R.string.lbl_my_location) + ":   $lat, $long"
        jobsNearMeLocationText.text = msg
    }

    override fun initJobsList(jobs: List<GitHubJob>) {
        println("FOUND ${jobs.size} JOBS")
    }

    override fun showNoJobsNearMeMsg() {
        jobsNearMeNoJobsLayout.visibility = View.VISIBLE
    }

    override fun showWaitingForLocation() {
        jobsNearMeProgress.visibility = View.VISIBLE
        progressText.text = getString(R.string.lbl_finding_your_location)
    }

    override fun showLoadingProgress() {
        jobsNearMeProgress.visibility = View.VISIBLE
        progressText.text = getString(R.string.lbl_loading_more_jobs)
    }

    override fun hideLoadingProgress() {
        jobsNearMeProgress.visibility = View.GONE
    }

    override fun showLocationError() {
        jobsNearMeLocationProblemLayout.visibility = View.VISIBLE
    }

    override fun showAPIError() {
        Toast.makeText(
                applicationContext,
                getString(R.string.err_failed_to_load_jobs),
                Toast.LENGTH_LONG
        ).show()
    }
}