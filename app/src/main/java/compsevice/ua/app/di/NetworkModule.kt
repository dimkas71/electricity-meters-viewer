package compsevice.ua.app.di

import android.app.Application
import android.content.Context
import compsevice.ua.app.EMVApplication
import compsevice.ua.app.rest.RandomService
import compsevice.ua.app.rest.RestApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule(private var context: EMVApplication) {

    @Provides
    @Singleton
    fun provideContext(): Context = context


    @Provides
    @Singleton
    fun provideRandomService(): RandomService {
        return RandomService.create(provideContext())
    }

}