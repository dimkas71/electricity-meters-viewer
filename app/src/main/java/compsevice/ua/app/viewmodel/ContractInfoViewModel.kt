package compsevice.ua.app.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import java.util.*


data class Client(val uuid: String, val fullName: String, val address: String, val telephone: String, val individualNumber: String)

data class CounterValuesHistory(val period: Date, val counter: String, val value: Long, val consumption: Long)

data class PaymentsHistory(val period: Date, val counter: String, val accured: Double, val paid: Double)

data class ContractInfo(val contractNumber: String, val sectorNumber: Int, val paymentDay: Int,
                        val client: Client, val countersHistory: List<CounterValuesHistory>,
                        val paymentsHistory: List<PaymentsHistory>)


class ContractInfoViewModel : ViewModel() {

    private var contractInfo: MutableLiveData<ContractInfo> = MutableLiveData<ContractInfo>()


    fun retreiveData(): LiveData<ContractInfo> = contractInfo

    fun updateDate(ci: ContractInfo) {
        contractInfo.postValue(ci)
    }

}