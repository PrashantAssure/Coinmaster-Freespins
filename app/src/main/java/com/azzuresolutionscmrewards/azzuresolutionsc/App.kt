package com.azzuresolutionscmrewards.azzuresolutionsc

import android.app.Application
import com.azzuresolutionscmrewards.azzuresolutionsc.di.networkModule
import com.azzuresolutionscmrewards.azzuresolutionsc.di.repositoryModule
import com.azzuresolutionscmrewards.azzuresolutionsc.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

public class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(
                listOf(repositoryModule, viewModelModule, networkModule)
            )
        }
    }


}