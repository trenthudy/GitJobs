package io.hudepohl.gitjobs.app

import android.app.Application
import android.content.Context
import android.location.LocationManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by trent on 1/13/18.
 */

@Module
class AndroidModule(private val app: Application) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = app

    @Provides
    @Singleton
    fun provideLocationManager(): LocationManager =
            app.getSystemService(Context.LOCATION_SERVICE) as LocationManager
}
