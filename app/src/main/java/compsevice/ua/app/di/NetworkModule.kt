package compsevice.ua.app.di

import android.app.Application
import android.content.Context
import compsevice.ua.app.rest.RandomService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule(private val app: Application) {

    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    fun provideRandomService(): RandomService {
        return RandomService.create(provideContext())
    }

}