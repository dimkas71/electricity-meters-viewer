package compsevice.ua.app.rest

import android.content.Context
import android.preference.PreferenceManager
import com.squareup.moshi.Moshi
import compsevice.ua.app.model.ContractInfo
import compsevice.ua.app.model.ServiceTypeAdapter
import compsevice.ua.app.viewmodel.Client
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestApi {

    @GET("infos")
    fun contracts(@Query("query") number: String): Call<List<ContractInfo>>

    @GET("detail")
    fun contract(@Query("query") contractUUID: String): Call<Client>


    companion object {
        fun service(context: Context): RestApi {

            val sp = PreferenceManager.getDefaultSharedPreferences(context)

            val url = sp.getString("pref_url", "")
            val user = sp.getString("pref_user", "")
            val password = sp.getString("pref_password", "")

            val client = OkHttpClient.Builder()
                    .addInterceptor(BasicAuthInterceptor(user, password))
                    .build()

            val moshi = Moshi.Builder()
                    .add(ServiceTypeAdapter())
                    .build()

            val factory = MoshiConverterFactory.create(moshi)

            return Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(factory)
                    .client(client)
                    .build()
                    .create(RestApi::class.java)
        }

    }

}