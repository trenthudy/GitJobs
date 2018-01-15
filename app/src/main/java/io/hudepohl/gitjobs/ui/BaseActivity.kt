package io.hudepohl.gitjobs.ui

import android.support.v7.app.AppCompatActivity
import io.hudepohl.gitjobs.app.ApplicationComponent
import io.hudepohl.gitjobs.app.BaseApplication

open class BaseActivity : AppCompatActivity() {

    protected val appComponent: ApplicationComponent
        get() = (application as BaseApplication).component
}
