package compsevice.ua.app.rest

import android.content.Context
import android.preference.PreferenceManager
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

data class RandomNumber(val random: Long)

class BasicAuthInterceptor(val user: String, val password: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response {
        val credentials = Credentials.basic(user, password)
        val originalRequest = chain?.request()

        val requestWithAuth = chain?.
                request()?.newBuilder()?.header("Authorization", credentials)?.build()

        return chain?.proceed(requestWithAuth)!!

    }

}


interface RandomService {

    @GET("test")
    fun randomNumber(@Query("from") from: Int, @Query("to") to: Int): Call<RandomNumber>

    companion object {
        fun create(context: Context): RandomService {

            var url = PreferenceManager.getDefaultSharedPreferences(context).getString("pref_url", "http://194.44.128.140:9090/demo/hs/")
            var user = PreferenceManager.getDefaultSharedPreferences(context).getString("prer_user", "unload")
            var password = PreferenceManager.getDefaultSharedPreferences(context).getString("pref_password","mbuh")

            val client = OkHttpClient.Builder()
                    .addInterceptor(BasicAuthInterceptor(user!!, password!!))
                    .build()

            val retrofit = Retrofit.Builder()
                    .addConverterFactory(MoshiConverterFactory.create())
                    .baseUrl(url) //TODO: default values....
                    .client(client)
                    .build()
            return retrofit.create(RandomService::class.java)

        }
    }
}

