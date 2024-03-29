package compsevice.ua.app.rest

import android.content.Context
import android.preference.PreferenceManager
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import compsevice.ua.app.activity.model.ConsumingBySectors
import compsevice.ua.app.model.ContractInfo
import compsevice.ua.app.model.ServiceTypeAdapter
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.util.*

class DateJsonAdapter {

    private val formatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

    @ToJson
    fun toJson(date: Date): String = formatter.format(date)

    @FromJson
    fun fromJson(json: String): Date = formatter.parse(json)

}

interface RestApi {

    @GET("infos")
    fun contracts(@Query("query") number: String, @Query("qtype") type: Int): Call<List<ContractInfo>>

    @GET("detail")
    fun contract(@Query("uuid") contractUUID: String, @Query("beginDate") beginDate: String): Call<compsevice.ua.app.viewmodel.ContractInfo>

    @GET("consuming")
    fun consuming(@Query("period") period: String): Call<List<ConsumingBySectors>>

    companion object {
        fun service(context: Context): RestApi {

            val sp = PreferenceManager.getDefaultSharedPreferences(context)

            val url = sp.getString("pref_url", "")
            val user = sp.getString("pref_user", "")
            val password = sp.getString("pref_password", "")

            val client = OkHttpClient.Builder()
                    .addInterceptor(BasicAuthInterceptor(user!!, password!!))
                    .build()

            val moshi = Moshi.Builder()
                    .add(ServiceTypeAdapter())
                    .add(DateJsonAdapter())
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