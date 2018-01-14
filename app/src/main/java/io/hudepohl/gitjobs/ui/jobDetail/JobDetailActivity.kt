package io.hudepohl.gitjobs.ui.jobDetail

import android.os.Bundle
import android.text.Html
import android.widget.Toast

import com.bumptech.glide.Glide

import io.hudepohl.gitjobs.R
import io.hudepohl.gitjobs.data.githubJobs.model.GitHubJob
import io.hudepohl.gitjobs.ui.BaseActivity
import io.hudepohl.gitjobs.util.Const
import kotlinx.android.synthetic.main.activity_job_detail.*
import javax.inject.Inject

/**
 * Created by trent on 1/12/18.
 */

class JobDetailActivity : BaseActivity(), JobDetailPresenter.View {

    @Inject lateinit var presenter: JobDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter.bind(this)

        val jobId = intent?.extras?.getString(Const.GITHUB_JOB_ID)
        when (jobId) {
            null -> { showFetchJobInfoError()  }
            else -> { presenter.jobInfo(jobId) }
        }
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun configureJobInfo(job: GitHubJob) {
        supportActionBar?.title = job.title

        Glide.with(jobDetailCompanyLogoImage.context)
                .load(job.company_logo)
                .into(jobDetailCompanyLogoImage)

        jobDetailCompanyNameText.text = job.company
        jobDetailPositionTitleText.text = job.title
        jobDetailPositionLocationText.text = job.location
        jobDetailDescriptionText.text = Html.fromHtml(job.description)
        jobDetailHowToApplyText.text = Html.fromHtml(job.how_to_apply)
        jobDetailPositionTypeText.text= job.type
        jobDetailCompanyWebsiteText.text= job.company_url
        jobDetailGitHubSourceText.text = job.url
        jobDetailDateOfListingText.text = job.created_at
    }

    override fun showFetchJobInfoError() {
        Toast.makeText(
                this.applicationContext,
                getString(R.string.err_failed_to_load_job_details),
                Toast.LENGTH_SHORT
        ).show()
    }
}
