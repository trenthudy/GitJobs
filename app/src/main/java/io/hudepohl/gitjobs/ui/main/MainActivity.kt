package io.hudepohl.gitjobs.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast

import com.bumptech.glide.Glide

import java.util.ArrayList

import io.hudepohl.gitjobs.R
import io.hudepohl.gitjobs.data.githubJobs.model.GitHubJob
import io.hudepohl.gitjobs.ui.BaseActivity
import io.hudepohl.gitjobs.ui.jobDetail.JobDetailActivity
import io.hudepohl.gitjobs.util.Const
import io.hudepohl.gitjobs.util.EndlessScrollListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.job_list_item.view.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainPresenter.View {

    @Inject lateinit var presenter: MainPresenter

    private var mJobList: ArrayList<GitHubJob> = ArrayList()

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

    override fun initializeJobList(jobs: List<GitHubJob>) {
        jobListRefreshLayout.isRefreshing = false

        mJobList = ArrayList()
        mJobList.addAll(jobs)

        jobsListView.adapter = GitHubJobAdaptor()

        jobsListView.setOnItemClickListener({ _, _, position, _ ->
            val jobDetailsActivity = Intent(this, JobDetailActivity::class.java)
            val bundle = Bundle()
            bundle.putString(Const.GITHUB_JOB_ID, mJobList[position].id)
            jobDetailsActivity.putExtras(bundle)
            startActivity(jobDetailsActivity)
        })

        jobsListView.setOnScrollListener(object : EndlessScrollListener() {
            override fun onLoadMore(page: Int, totalItemsCount: Int): Boolean {
                presenter.nextPage(page)
                return true
            }
        })
    }

    override fun addJobsToList(jobs: List<GitHubJob>) {
        mJobList.addAll(jobs)
        (jobsListView.adapter as GitHubJobAdaptor).notifyDataSetChanged()
    }

    override fun showPageLoadingDialog() {
        pagination_loading_progress.visibility = View.VISIBLE
    }

    override fun hidePageLoadingDialog() {
        pagination_loading_progress.visibility = View.GONE
    }

    override fun showGetJobListError() {
        Toast.makeText(
                applicationContext,
                getString(R.string.err_failed_to_load_jobs),
                Toast.LENGTH_LONG
        ).show()
    }

    internal inner class GitHubJobAdaptor :
            ArrayAdapter<GitHubJob>(this@MainActivity, R.layout.job_list_item, mJobList) {

        override fun getView(position: Int, view: View?, parent: ViewGroup): View {
            val retView: View = when (view) {
                null -> LayoutInflater.from(context).inflate(R.layout.job_list_item, parent, false)
                else -> view
            }

            val (_, _, title, location, _, _, _, company, _, company_logo) = mJobList[position]

            Glide.with(retView.job_list_item_company_logo_iv.context)
                    .load(company_logo)
                    .into(retView.job_list_item_company_logo_iv)

            retView.job_list_item_company_name_tv.text          = company
            retView.job_list_item_position_title_tv.text        = title
            retView.job_list_item_position_location_tv.text     = location

            return retView
        }
    }
}
