package compsevice.ua.app.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import compsevice.ua.app.R
import compsevice.ua.app.activity.adapter.CounterInfoAdapter
import compsevice.ua.app.activity.adapter.Item
import compsevice.ua.app.activity.viewmodel.CurrentContractInfoViewModel
import compsevice.ua.app.model.ServiceType
import compsevice.ua.app.viewmodel.ContractInfo
import compsevice.ua.app.viewmodel.ContractInfoViewModel
import kotlinx.android.synthetic.main.contract_info_detail_common.*


class CommonInfoFragment : Fragment() {

    private lateinit var model: ContractInfoViewModel
    private lateinit var currentContractInfoModel: CurrentContractInfoViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(CommonInfoFragment::class.java.simpleName, "On create called")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.i(CommonInfoFragment::class.java.simpleName, "On create View called...")
        return inflater.inflate(R.layout.contract_info_detail_common, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Log.i(CommonInfoFragment::class.java.simpleName, "On Activity Created")

        currentContractInfoModel = activity?.let {
            ViewModelProviders.of(it).get(CurrentContractInfoViewModel::class.java)
        } ?: CurrentContractInfoViewModel()


        model = activity?.let {
            ViewModelProviders.of(it).get(ContractInfoViewModel::class.java)
        } ?: ContractInfoViewModel()

        model?.retreiveData()?.observe(this , Observer<ContractInfo> {


            contractNumber.text = context?.resources?.getString(R.string.text_contract_number, it?.contractNumber)

            with(it?.client) {
                fullName.text = this?.fullName
                address.text = context?.resources?.getString(R.string.text_common_info_address, this?.address)
                telephone.text = context?.resources?.getString(R.string.text_common_info_telephone, this?.telephone)
                individualNumber.text = context?.resources?.getString(R.string.text_common_info_individual_number,this?.individualNumber)
            }


        })


        currentContractInfoModel?.getContractInfo().observe(this, Observer<compsevice.ua.app.model.ContractInfo> {
            Log.i(CommonInfoFragment::class.java.simpleName, "Contract info: $it")

            val adapter = it?.counters?.map { Item(it.factory, it.value) }?.toList()?.let { it1 -> CounterInfoAdapter(it1) }

            currentCountersValues.adapter = adapter
            currentCountersValues.layoutManager = LinearLayoutManager(context)

            //Setup credit's fields....
            updateCreditField(it, textCreditElectricity, ServiceType.Electricity)
            updateCreditField(it, textCreditVideo, ServiceType.Video)
            updateCreditField(it, textCreditService, ServiceType.Service)

        })

    }


    private fun updateCreditField(ci: compsevice.ua.app.model.ContractInfo?, field: TextView, serviceType: ServiceType) {

        val creditEl = ci?.creditByService(serviceType) ?: 0.0

        field.setTextColor(context?.resources?.getColor(R.color.colorCredit) ?: R.color.colorCredit)

        if (creditEl > 0.0) {
            field.setTextColor(context?.resources?.getColor(R.color.colorCreditRed) ?: R.color.colorCredit)
        }

        field.text = String.format("%.2f", creditEl)
    }

}
