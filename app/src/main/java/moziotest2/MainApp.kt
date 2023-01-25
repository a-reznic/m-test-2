package moziotest2

import android.app.Application
import moziotest2.adapter.di.KoinModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            allowOverride(true)
//            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@MainApp)
            modules(KoinModule.main())
        }
    }
}