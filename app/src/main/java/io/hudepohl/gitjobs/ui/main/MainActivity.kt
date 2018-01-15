package io.hudepohl.gitjobs.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast

import io.hudepohl.gitjobs.R
import io.hudepohl.gitjobs.data.githubJobs.model.GitHubJob
import io.hudepohl.gitjobs.ui.BaseActivity
import io.hudepohl.gitjobs.ui.jobDetail.JobDetailActivity
import io.hudepohl.gitjobs.ui.jobsNearMe.JobsNearMeActivity
import io.hudepohl.gitjobs.util.Const
import io.hudepohl.gitjobs.util.EndlessScrollListener
import io.hudepohl.gitjobs.util.GitHubJobListAdaptor
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.progress.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class MainActivity : BaseActivity(), MainPresenter.View {

    @Inject lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.bind(this)
        presenter.init()

        supportActionBar?.title = getString(R.string.toolbar_welcome_msg)
        jobListRefreshLayout.setOnRefreshListener({ presenter.refresh() })
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.mainMenuJobsNearMe -> {
                val jobsNearMeIntent = Intent(this, JobsNearMeActivity::class.java)
                startActivity(jobsNearMeIntent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initializeJobList(jobs: List<GitHubJob>) {
        jobListRefreshLayout.isRefreshing = false

        jobListView.adapter = GitHubJobListAdaptor(this, jobs as ArrayList<GitHubJob>)

        jobListView.setOnItemClickListener({ _, _, position, _ ->

            val job = (jobListView.adapter as GitHubJobListAdaptor).getItem(position)

            val jobDetailsActivity = Intent(this, JobDetailActivity::class.java)
            val bundle = Bundle()
            bundle.putString(Const.GITHUB_JOB_ID, job.id)
            jobDetailsActivity.putExtras(bundle)
            startActivity(jobDetailsActivity)
        })

        jobListView.setOnScrollListener(object : EndlessScrollListener(50, 5) {
            override fun onLoadMore(page: Int, totalItemsCount: Int): Boolean {
                presenter.nextPage(page)
                return true
            }
        })
    }

    override fun addJobsToList(jobs: List<GitHubJob>) {
        (jobListView.adapter as GitHubJobListAdaptor).addJobs(jobs)
    }

    override fun showPageLoadingDialog() {
        progressText.text = getString(R.string.lbl_loading_more_jobs)
        paginationLoadingProgress.visibility = View.VISIBLE
    }

    override fun hidePageLoadingDialog() {
        paginationLoadingProgress.visibility = View.GONE
    }

    override fun showGetJobListError() {
        Toast.makeText(
                applicationContext,
                getString(R.string.err_failed_to_load_jobs),
                Toast.LENGTH_LONG
        ).show()
    }
}
