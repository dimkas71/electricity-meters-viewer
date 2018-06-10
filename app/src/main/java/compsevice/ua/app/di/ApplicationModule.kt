package compsevice.ua.app.di

import compsevice.ua.app.EMVApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(val app: EMVApplication) {

    @Provides @Singleton
    fun provideApp(): EMVApplication = app

}