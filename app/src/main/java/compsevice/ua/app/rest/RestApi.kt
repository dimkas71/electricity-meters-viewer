package compsevice.ua.app.rest

import compsevice.ua.app.model.ContractInfo
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface RestApi {

    @GET("/contractinfo/{number}")
    fun contracts(@Path("number") number: String): Call<List<ContractInfo>>

    companion object {
        //TODO: add shared preferences initialization.....
        val BASE_URL = "http://192.168.1.58:9090"

        fun service(url: String): RestApi = Retrofit.Builder()
                                                .baseUrl(url)
                                                .addConverterFactory(JacksonConverterFactory.create())
                                                .build()
                                                .create(RestApi::class.java)

    }

}