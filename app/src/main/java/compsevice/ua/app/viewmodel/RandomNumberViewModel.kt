package compsevice.ua.app.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import compsevice.ua.app.rest.RandomNumber
import compsevice.ua.app.rest.RandomService
import javax.inject.Inject

class RandomNumberViewModel @Inject constructor(private var service: RandomService): ViewModel() {

    lateinit var random: MutableLiveData<RandomNumber>

    fun getRandom(): LiveData<RandomNumber> {

        val body = service.randomNumber(10, 15).execute()

        random = MutableLiveData<RandomNumber>().apply {
            postValue(body.body())
        }

        return random
    }





}