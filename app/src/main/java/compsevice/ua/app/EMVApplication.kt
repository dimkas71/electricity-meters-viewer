package compsevice.ua.app

import android.app.Application
import compsevice.ua.app.di.*

class EMVApplication : Application() {

    lateinit var injector: Injector private set

    override fun onCreate() {
        super.onCreate()
        injector = initDagger()
    }

    private fun initDagger(): Injector =
            DaggerInjector.builder()
                    .applicationModule(ApplicationModule(this))
                    .networkModule(NetworkModule(this))
                    .build()
}