package io.hudepohl.gitjobs.util

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import io.hudepohl.gitjobs.R
import io.hudepohl.gitjobs.data.githubJobs.model.GitHubJob
import kotlinx.android.synthetic.main.job_list_item.view.*

class GitHubJobListAdaptor(private var context: Context,
                           initialJobs: ArrayList<GitHubJob>) : BaseAdapter() {

    private var jobList: ArrayList<GitHubJob> = initialJobs

    fun addJobs(jobs: List<GitHubJob>) {
        jobList.addAll(jobs)
        this.notifyDataSetChanged()
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val retView: View = when (view) {
            null -> LayoutInflater.from(context).inflate(R.layout.job_list_item, parent, false)
            else -> view
        }

        val (_, _, title, location, _, _, _, company, _, companyLogo) = jobList[position]

        Glide.with(retView.jobListItemCompanyLogoImage.context)
                .load(companyLogo)
                .into(retView.jobListItemCompanyLogoImage)

        retView.jobListItemCompanyNameText.text = company
        retView.jobListItemPositionTitleText.text = title
        retView.jobListItemPositionLocationText.text = location

        return retView
    }

    override fun getItemId(position: Int): Long {
        // GitHubJob objects have String IDs...
        Log.e(this.javaClass.simpleName, "Don't use getItemId() inside of GitHubJobListAdaptor!")
        return -1
    }

    override fun getItem(position: Int): GitHubJob {
        return jobList[position]
    }

    override fun getCount(): Int {
        return jobList.size
    }
}