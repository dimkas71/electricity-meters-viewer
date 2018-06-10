package compsevice.ua.app.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import compsevice.ua.app.ViewModelFactory
import compsevice.ua.app.ViewModelKey
import compsevice.ua.app.viewmodel.RandomNumberViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory


    @Binds
    @IntoMap
    @ViewModelKey(RandomNumberViewModel::class)
    internal abstract fun randomNumberViewModel(viewModel: RandomNumberViewModel): ViewModel

}