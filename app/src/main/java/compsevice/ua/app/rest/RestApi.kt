package compsevice.ua.app.rest

import com.squareup.moshi.Moshi
import compsevice.ua.app.model.ContractInfo
import compsevice.ua.app.model.ServiceTypeAdapter
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface RestApi {

    @GET("/contractinfo/{number}")
    fun contracts(@Path("number") number: String): Call<List<ContractInfo>>

    companion object {
        //TODO: add shared preferences initialization.....
        val BASE_URL = "http://192.168.1.58:9090"

        fun service(url: String): RestApi {

            val moshi = Moshi.Builder()
                    .add(ServiceTypeAdapter())
                    .build()

            val factory = MoshiConverterFactory.create(moshi)

            return Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(factory)
                    .build()
                    .create(RestApi::class.java)
        }

    }

}