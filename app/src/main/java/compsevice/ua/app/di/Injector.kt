package compsevice.ua.app.di

import compsevice.ua.app.StartActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class, ViewModelModule::class, NetworkModule::class))
interface Injector {
    fun inject(activity: StartActivity)
}