package com.example.splitbuddy

import android.app.Application
import com.example.splitbuddy.di.DIContainer

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        DIContainer.main(this)
    }

}