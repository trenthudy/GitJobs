package io.hudepohl.gitjobs.ui.jobsNearMe

import android.location.LocationManager
import io.hudepohl.gitjobs.data.githubJobs.GitHubJobsAPI
import io.hudepohl.gitjobs.data.githubJobs.model.GitHubJob
import io.hudepohl.gitjobs.ui.BasePresenter
import javax.inject.Inject

/**
 * Created by trent on 1/14/18.
 */
class JobsNearMePresenter @Inject constructor() : BasePresenter<JobsNearMePresenter.View>() {

    @Inject lateinit var api: GitHubJobsAPI
    @Inject lateinit var locationManager: LocationManager

    fun getJobsNearMe() {


    }

    interface View {
        fun initJobsList(jobs: List<GitHubJob>)
        fun showNoJobsNearMeMsg()
        fun showLocationError()
    }
}