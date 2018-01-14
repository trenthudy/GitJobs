package io.hudepohl.gitjobs.app

import android.app.Application

/**
 * Created by trent on 1/13/18.
 */

class BaseApplication : Application() {

    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
        component = DaggerApplicationComponent.builder().androidModule(AndroidModule(this)).build()
    }
}
