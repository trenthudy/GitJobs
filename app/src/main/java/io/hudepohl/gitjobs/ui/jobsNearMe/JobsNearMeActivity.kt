package io.hudepohl.gitjobs.ui.jobsNearMe

import android.os.Bundle
import android.widget.Toast
import io.hudepohl.gitjobs.R
import io.hudepohl.gitjobs.data.githubJobs.model.GitHubJob
import io.hudepohl.gitjobs.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_jobs_near_me.*
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
        jobsNearMeLocationText.text = getString(R.string.lbl_your_location) + ":   $lat, $long"
    }

    override fun initJobsList(jobs: List<GitHubJob>) {

    }

    override fun showNoJobsNearMeMsg() {
        Toast.makeText(
                applicationContext,
                getString(R.string.lbl_no_jobs_nearby),
                Toast.LENGTH_LONG
        ).show()
    }

    override fun showLocationError() {
        Toast.makeText(
                applicationContext,
                getString(R.string.err_failed_to_determine_your_location),
                Toast.LENGTH_LONG
        ).show()
    }

    override fun showNoLocationPermissionError() {
        Toast.makeText(
                applicationContext,
                getString(R.string.err_no_location_permission),
                Toast.LENGTH_LONG
        ).show()
    }
}