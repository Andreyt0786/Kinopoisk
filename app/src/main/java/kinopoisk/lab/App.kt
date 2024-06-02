package kinopoisk.lab

import android.app.Application
import kinopoisk.lab.di.component.AppComponent
import kinopoisk.lab.di.component.DaggerAppComponent
import kinopoisk.lab.di.module.DbModule


class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().dbModule(DbModule(this)).build()
    }
}