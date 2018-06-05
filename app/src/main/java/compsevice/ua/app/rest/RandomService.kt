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
import java.util.*

data class RandomNumber(val createdAt: Date = Date(), val random: Long)

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
    fun randomNumber(@Path("from") from: Int, @Path("to") to: Int): Call<RandomNumber>

    companion object {
        fun create(context: Context): RandomService {

            val url = PreferenceManager.getDefaultSharedPreferences(context).getString("pref_url", "")
            val user = PreferenceManager.getDefaultSharedPreferences(context).getString("prer_user", "")
            val password = PreferenceManager.getDefaultSharedPreferences(context).getString("pref_password","")

            val client = OkHttpClient.Builder()
                    .addInterceptor(BasicAuthInterceptor(user, password))
                    .build()

            val retrofit = Retrofit.Builder()
                    .addConverterFactory(MoshiConverterFactory.create())
                    .baseUrl(url)
                    .client(client)
                    .build()
            return retrofit.create(RandomService::class.java)

        }
    }
}

