package com.superstock.app

import android.app.Application
import com.superstock.app.di.AppContainer

class SuperStockApplication : Application() {

    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}