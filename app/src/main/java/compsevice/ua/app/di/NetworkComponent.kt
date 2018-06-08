package compsevice.ua.app.di

import compsevice.ua.app.viewmodel.RandomNumberViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface NetworkComponent {
    fun inject(target: RandomNumberViewModel)
}