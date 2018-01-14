package io.hudepohl.gitjobs.app

import dagger.Component
import io.hudepohl.gitjobs.data.DataModule
import io.hudepohl.gitjobs.ui.jobDetail.JobDetailActivity
import io.hudepohl.gitjobs.ui.main.MainActivity
import javax.inject.Singleton

/**
 * Created by trent on 1/13/18.
 */

@Singleton
@Component(modules = [
    AndroidModule::class,
    DataModule::class
])
interface ApplicationComponent {
    fun inject(app: BaseApplication)
    fun inject(mainActivity: MainActivity)
    fun inject(jobDetailActivity: JobDetailActivity)
}
