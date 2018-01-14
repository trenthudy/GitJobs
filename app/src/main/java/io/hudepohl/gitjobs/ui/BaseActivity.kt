package io.hudepohl.gitjobs.ui

import android.support.v7.app.AppCompatActivity
import io.hudepohl.gitjobs.app.ApplicationComponent
import io.hudepohl.gitjobs.app.BaseApplication

/**
 * Created by trent on 1/12/18.
 */

open class BaseActivity : AppCompatActivity() {

    protected val appComponent: ApplicationComponent
        get() = (application as BaseApplication).component
}
