package compsevice.ua.app.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import compsevice.ua.app.rest.RandomNumber
import compsevice.ua.app.rest.RandomService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RandomNumberViewModel @Inject constructor(private var service: RandomService): ViewModel() {

    lateinit var random: MutableLiveData<RandomNumber>

    fun getRandom(): LiveData<RandomNumber> {

        Log.i(RandomNumberViewModel::class.java.simpleName, "service $service")

        random = MutableLiveData<RandomNumber>()

        update()

        return random
    }

    fun update(value: Int = 10) {
        service.randomNumber(0, value).enqueue(object: Callback<RandomNumber> {
            override fun onFailure(call: Call<RandomNumber>?, t: Throwable?) {
                Log.e(call?.javaClass?.simpleName, t?.message!!)
            }

            override fun onResponse(call: Call<RandomNumber>?, response: Response<RandomNumber>?) {
                val number = response?.body()
                random.postValue(number)

                Log.i(call?.javaClass?.simpleName, "Random number : ${number}")

            }
        })
    }





}