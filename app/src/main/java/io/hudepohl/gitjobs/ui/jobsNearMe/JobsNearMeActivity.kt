package io.hudepohl.gitjobs.ui.jobsNearMe

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import io.hudepohl.gitjobs.R
import io.hudepohl.gitjobs.data.githubJobs.model.GitHubJob
import io.hudepohl.gitjobs.ui.BaseActivity
import io.hudepohl.gitjobs.ui.jobDetail.JobDetailActivity
import io.hudepohl.gitjobs.util.Const
import io.hudepohl.gitjobs.util.GitHubJobListAdaptor
import kotlinx.android.synthetic.main.activity_jobs_near_me.*
import kotlinx.android.synthetic.main.progress.*
import javax.inject.Inject

class JobsNearMeActivity : BaseActivity(), JobsNearMePresenter.View {

    @Inject lateinit var presenter: JobsNearMePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jobs_near_me)

        presenter.bind(this)
        presenter.getJobsNearMe()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.lbl_jobs_near_me)
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
        val msg = getString(R.string.lbl_my_location) + ":   $lat, $long"
        jobsNearMeLocationText.text = msg
    }

    override fun initJobsList(jobs: List<GitHubJob>) {
        jobsNearMeListView.adapter = GitHubJobListAdaptor(this, jobs as ArrayList<GitHubJob>)

        jobsNearMeListView.setOnItemClickListener({ _, _, position, _ ->

            val job = (jobsNearMeListView.adapter as GitHubJobListAdaptor).getItem(position)

            val jobDetailsActivity = Intent(this, JobDetailActivity::class.java)
            val bundle = Bundle()
            bundle.putString(Const.GITHUB_JOB_ID, job.id)
            jobDetailsActivity.putExtras(bundle)
            startActivity(jobDetailsActivity)
        })
    }

    override fun showNoJobsNearMeMsg() {
        jobsNearMeNoJobsLayout.visibility = View.VISIBLE
    }

    override fun showWaitingForLocation() {
        jobsNearMeResultsLayout.visibility = View.VISIBLE
        jobsNearMeProgress.visibility = View.VISIBLE
        progressText.text = getString(R.string.lbl_finding_your_location)
    }

    override fun showLoadingProgress() {
        progressText.text = getString(R.string.lbl_loading_more_jobs)
        jobsNearMeProgress.visibility = View.VISIBLE
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