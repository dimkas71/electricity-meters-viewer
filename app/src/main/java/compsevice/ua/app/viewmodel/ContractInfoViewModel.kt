package compsevice.ua.app.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel


data class Client(val uuid: String, val fullName: String, val address: String, val telephone: String, val individualNumber: String)

class ContractInfoViewModel : ViewModel() {

    private var client: MutableLiveData<Client> = MutableLiveData<Client>()


    fun retreiveClient(): LiveData<Client> = client

    fun updateClient(newClient: Client) {
        client.postValue(newClient)
    }

}