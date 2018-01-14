package io.hudepohl.gitjobs.ui.jobsNearMe

import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
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
        try {

            val locationListener = object : LocationListener {
                override fun onLocationChanged(p0: Location?) {
                    view?.displayLocation(p0?.latitude ?: -1.0, p0?.longitude ?: -2.0)
                }

                override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {}
                override fun onProviderEnabled(p0: String?) {}
                override fun onProviderDisabled(p0: String?) {}
            }

            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    2000,
                    10f,
                    locationListener
            )
        } catch (e: SecurityException) {
            view?.showNoLocationPermissionError()
            e.printStackTrace()
        }
    }

    interface View {
        fun displayLocation(lat: Double, long: Double)
        fun initJobsList(jobs: List<GitHubJob>)
        fun showNoJobsNearMeMsg()
        fun showLocationError()
        fun showNoLocationPermissionError()
    }
}