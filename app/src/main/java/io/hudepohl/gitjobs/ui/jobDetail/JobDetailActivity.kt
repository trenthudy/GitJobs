package io.hudepohl.gitjobs.ui.jobDetail

import android.os.Bundle
import android.text.Html
import android.widget.Toast

import com.bumptech.glide.Glide

import io.hudepohl.githubJobs.data.model.GitHubJob
import io.hudepohl.gitjobs.R
import io.hudepohl.gitjobs.ui.BaseActivity
import io.hudepohl.gitjobs.util.ConstKey
import kotlinx.android.synthetic.main.activity_job_detail.*

/**
 * Created by trent on 1/12/18.
 */

class JobDetailActivity : BaseActivity(), JobDetailPresenter.View {

    private var mPresenter: JobDetailPresenter = JobDetailPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_detail)

        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        val id = intent?.extras?.getString(ConstKey.JOB_ID) ?: ConstKey.NO_ID_FOUND
        if (id == ConstKey.NO_ID_FOUND) {
            showFetchJobInfoError()
        } else {
            mPresenter.getJobInfo(id)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun configureJobInfo(job: GitHubJob) {
        supportActionBar?.title = job.title

        Glide.with(job_detail_company_logo_iv.context)
                .load(job.company_logo)
                .into(job_detail_company_logo_iv)

        job_detail_company_name_tv.text         = job.company
        job_detail_position_title_tv.text       = job.title
        job_detail_position_location_tv.text    = job.location
        job_detail_desc_tv.text                 = Html.fromHtml(job.description)
        job_detail_how_to_apply_tv.text         = Html.fromHtml(job.how_to_apply)
        job_detail_position_type_tv.text        = job.type
        job_detail_company_website_tv.text      = job.company_url
        job_detail_github_source_tv.text        = job.url
        job_detail_date_of_listing_tv.text      = job.created_at
    }

    override fun showFetchJobInfoError() {
        Toast.makeText(
                this.applicationContext,
                getString(R.string.err_failed_to_load_job_details),
                Toast.LENGTH_SHORT
        ).show()
    }
}
