package compsevice.ua.app.activity.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import compsevice.ua.app.model.ContractInfo

class CurrentContractInfoViewModel : ViewModel() {

    private var contractInfo: MutableLiveData<ContractInfo> = MutableLiveData<ContractInfo>()


    fun updateContractInfo(ci: ContractInfo) {
        contractInfo.value = ci
    }


    fun getContractInfo(): LiveData<ContractInfo> = contractInfo

}