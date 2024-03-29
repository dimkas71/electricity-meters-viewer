package compsevice.ua.app.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.SimpleDateFormat
import java.util.*

enum class ServiceType(val alias: String)  {

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

data class Credit(val uuid: String, val service: ServiceType, val counter: String, val credit: Double) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
            ServiceType.toServiceType(parcel.readString()!!),
        parcel.readString()!!,
            parcel.readDouble()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uuid)
        parcel.writeString(service.alias)
        parcel.writeString(counter ?: "")
        parcel.writeDouble(credit)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Credit> {
        override fun createFromParcel(parcel: Parcel): Credit {
            return Credit(parcel)
        }

        override fun newArray(size: Int): Array<Credit?> {
            return arrayOfNulls(size)
        }
    }
}

data class Counter(val uuid: String, val factory: String, val value: Long, val contractNumber: String, val isOff: Boolean = false) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
            parcel.readLong(),
            parcel.readString()!!,
    parcel.readInt()!! == 1) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uuid)
        parcel.writeString(factory ?: "")
        parcel.writeLong(value)
        parcel.writeString(contractNumber ?: "")
        parcel.writeInt(if (isOff) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Counter> {
        override fun createFromParcel(parcel: Parcel): Counter {
            return Counter(parcel)
        }

        override fun newArray(size: Int): Array<Counter?> {
            return arrayOfNulls(size)
        }
    }
}

data class ContractInfo(val uuid: String, val number: String, val owner: String, val sector: Int, val checkDate: Date, val counters: List<Counter>, val credits: List<Credit>, val isOff: Boolean = false) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
            parcel.readInt(),
            SimpleDateFormat("dd.MM.yyyy").parse(parcel.readString()),
        parcel.createTypedArrayList(Counter)!!,
        parcel.createTypedArrayList(Credit)!!,
        parcel.readInt()!! == 1
    ) {
    }

    fun creditByService(service: ServiceType): Double =
        credits.filter { it.service == service }
                .map { it.credit }
                .sum()

    fun matchesQuery(constraint: CharSequence): Boolean  = when {
        number.contains(other = constraint, ignoreCase = true) -> true
        owner.contains(other = constraint, ignoreCase = true) -> true
        else -> {
            counters.filter { it.factory.contains(ignoreCase = true, other = constraint) }
                    .firstOrNull() != null
        }

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uuid)
        parcel.writeString(number)
        parcel.writeString(owner)
        parcel.writeInt(sector)
        parcel.writeString(SimpleDateFormat("dd.MM.yyyy").format(checkDate))
        parcel.writeTypedList(counters)
        parcel.writeTypedList(credits)
        parcel.writeInt(if (isOff) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ContractInfo> {
        override fun createFromParcel(parcel: Parcel): ContractInfo {
            return ContractInfo(parcel)
        }

        override fun newArray(size: Int): Array<ContractInfo?> {
            return arrayOfNulls(size)
        }
    }

}
