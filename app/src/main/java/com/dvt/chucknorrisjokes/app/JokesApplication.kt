package com.dvt.chucknorrisjokes.app

import android.app.Application
import com.dvt.chucknorrisjokes.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Use Hilt Dependency Injection
 *
 * Also, sets up Timber in the DEBUG BuildConfig.
 */
@HiltAndroidApp
class JokesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}