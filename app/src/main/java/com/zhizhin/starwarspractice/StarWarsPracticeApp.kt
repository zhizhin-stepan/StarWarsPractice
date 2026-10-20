
package com.zhizhin.starwarspractice

import android.app.Application
import com.zhizhin.starwarspractice.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class StarWarsPracticeApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@StarWarsPracticeApp)
            modules(appModule)
        }
    }
}
