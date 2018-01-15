package io.hudepohl.gitjobs.ui.jobsNearMe

import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import io.hudepohl.gitjobs.data.githubJobs.GitHubJobsAPI
import io.hudepohl.gitjobs.data.githubJobs.model.GitHubJob
import io.hudepohl.gitjobs.ui.BasePresenter
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by trent on 1/14/18.
 */

class JobsNearMePresenter @Inject constructor() : BasePresenter<JobsNearMePresenter.View>() {

    @Inject lateinit var api: GitHubJobsAPI
    @Inject lateinit var locationManager: LocationManager

    private lateinit var locationListener: LocationListener

    override fun detach() {
        super.detach()
        removeLocationSubscriptions()
    }

    fun getJobsNearMe() {
        getLocationSnapshot()
    }

    private fun getLocationSnapshot() {

        try {

            locationListener = object : LocationListener {
                var foundValidLocation = false
                override fun onLocationChanged(location: Location?) {
                    if (location != null && !foundValidLocation) {
                        foundValidLocation = true
                        view?.displayLocation(location.latitude, location.longitude)
                        fetchJobsByLatLong(location.latitude, location.longitude)
                        removeLocationSubscriptions()
                    }
                }
                override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {}
                override fun onProviderEnabled(p0: String?) {}
                override fun onProviderDisabled(p0: String?) {}
            }

            val providers = locationManager.allProviders
            var hasProvider = false
            for (provider in providers) {
                if (locationManager.isProviderEnabled(provider)) {
                    locationManager.requestLocationUpdates(provider, 0, 0f, locationListener)
                    hasProvider = true
                }
            }

            if (hasProvider) {
                view?.showWaitingForLocation()
            } else {
                view?.showProviderError()
            }

        } catch (e: SecurityException) {
            view?.showPermissionError()
        }
    }

    private fun removeLocationSubscriptions() {
        locationManager.removeUpdates(locationListener)
    }

    private fun fetchJobsByLatLong(lat: Double, long: Double) {

        api.getJobsByLatLong(lat, long)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<List<GitHubJob>> {

                    override fun onSubscribe(d: Disposable) {
                        view?.showLoadingProgress()
                    }
                    override fun onNext(jobs: List<GitHubJob>) {
                        when (jobs.size) {
                            0 ->    { view?.showNoJobsNearMeMsg() }
                            else -> { view?.initJobsList(jobs)    }
                        }
                    }
                    override fun onError(e: Throwable) {
                        view?.showAPIError()
                    }
                    override fun onComplete() {
                        view?.hideLoadingProgress()
                    }
                })
    }

    interface View {
        fun displayLocation(lat: Double, long: Double)
        fun initJobsList(jobs: List<GitHubJob>)
        fun showNoJobsNearMeMsg()

        fun showWaitingForLocation()
        fun showLoadingProgress()
        fun hideLoadingProgress()

        fun showPermissionError()
        fun showProviderError()
        fun showAPIError()
    }
}
