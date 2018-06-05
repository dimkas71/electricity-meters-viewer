package compsevice.ua.app

import android.app.Application
import compsevice.ua.app.di.DaggerNetworkComponent
import compsevice.ua.app.di.NetworkComponent
import compsevice.ua.app.di.NetworkModule

class EMVApplication : Application() {

    lateinit var networkComponent: NetworkComponent

    override fun onCreate() {
        super.onCreate()
        networkComponent = initDagger(this)
    }

    private fun initDagger(emvApplication: EMVApplication): NetworkComponent =
            DaggerNetworkComponent.builder()
                    .networkModule(NetworkModule(emvApplication))
                    .build()
}