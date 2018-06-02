package compsevice.ua.app.model

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

enum class ServiceType(val alias: String) {

    Electricity("ELECTRICITY"),
    Service("SERVICE"),
    Video("VIDEO"),
    Unsupported("UNSUPPORTED");

    companion object {

        fun toServiceType(alias: String): ServiceType = when (alias) {
            "ELECTRICITY" -> Electricity
            "SERVICE" -> Service
            "VIDEO" -> Video
            else -> Unsupported

        }

    }

}

class ServiceTypeAdapter {

    @ToJson
    fun toJson(st: ServiceType): String = st.name.toUpperCase()

    @FromJson
    fun fromJson(json: String): ServiceType = ServiceType.toServiceType(json)

}

data class Credit(val id: Long, val service: ServiceType, val counter: String, val credit: Double)

data class Counter(val id: Long, val factoryNumber: String, val value: Long)

data class ContractInfo(val id: Long, val number: String, val owner: String, val sectorNumber: Int, val counters: List<Counter>, val credits: List<Credit>) {

    fun creditByService(service: ServiceType): Double =
        credits.filter { it.service == service }
                .map { it.credit }
                .sum()

    fun matchesQuery(constraint: CharSequence): Boolean  = when {
        number.contains(constraint) -> true
        owner.contains(constraint) -> true
        else -> {
            counters.filter { it.factoryNumber.contains(constraint) }
                    .first() != null
        }

    }
}
