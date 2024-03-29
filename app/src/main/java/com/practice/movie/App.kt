package com.practice.movie

import android.app.Application
import com.practice.core.common.di.dbModule
import com.practice.core.common.di.networkModule
import com.practice.feature.auth_impl.di.authModule
import com.practice.feature.detail.di.detailModule
import com.practice.feature.home_impl.di.homeModule
import com.practice.movie.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(
                appModule,
                networkModule,
                dbModule,
                authModule,
                homeModule,
                detailModule,
            )
        }
    }
}
