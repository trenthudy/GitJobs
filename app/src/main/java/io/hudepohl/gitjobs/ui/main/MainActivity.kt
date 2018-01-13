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

import io.hudepohl.githubJobs.data.model.GitHubJob
import io.hudepohl.gitjobs.R
import io.hudepohl.gitjobs.ui.BaseActivity
import io.hudepohl.gitjobs.ui.jobDetail.JobDetailActivity
import io.hudepohl.gitjobs.util.ConstKey
import io.hudepohl.gitjobs.util.EndlessScrollListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.job_list_item.view.*

class MainActivity : BaseActivity(), MainPresenter.View {

    private var mPresenter: MainPresenter = MainPresenter(this)

    private var mJobList: ArrayList<GitHubJob> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = getString(R.string.toolbar_welcome_msg)
        job_list_refresh_layout.setOnRefreshListener({ mPresenter.refresh() })
        mPresenter.init()
    }

    override fun initializeJobList(jobs: List<GitHubJob>) {
        if (job_list_refresh_layout.isRefreshing) { job_list_refresh_layout.isRefreshing = false }

        mJobList = ArrayList()
        mJobList.addAll(jobs)

        job_list_lv.adapter = GitHubJobAdaptor()
        job_list_lv.setOnItemClickListener({ _, _, i, _ -> moveToJobDetails(mJobList[i]) })
        job_list_lv.setOnScrollListener(object : EndlessScrollListener() {
            override fun onLoadMore(page: Int, totalItemsCount: Int): Boolean {
                mPresenter.getNextPage()
                return true
            }
        })
    }

    override fun addJobsToList(jobs: List<GitHubJob>) {
        mJobList.addAll(jobs)
        (job_list_lv.adapter as GitHubJobAdaptor).notifyDataSetChanged()
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

    private fun moveToJobDetails(job: GitHubJob) {
        val bundle = Bundle()
        bundle.putString(ConstKey.JOB_ID, job.id)

        val jobDetailsActivity = Intent(this, JobDetailActivity::class.java)
        jobDetailsActivity.putExtras(bundle)
        startActivity(jobDetailsActivity)
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
