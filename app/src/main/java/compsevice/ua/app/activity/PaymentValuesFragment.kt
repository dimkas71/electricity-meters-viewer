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
import compsevice.ua.app.R
import compsevice.ua.app.activity.adapter.PaymentValuesAdapter
import compsevice.ua.app.viewmodel.ContractInfoViewModel
import kotlinx.android.synthetic.main.contract_info_detail_payment_values.*

class PaymentValuesFragment : Fragment() {

    private lateinit var model: ContractInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.contract_info_detail_payment_values, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        model = activity?.let {
            ViewModelProviders.of(it).get(ContractInfoViewModel::class.java)
        } ?: ContractInfoViewModel()

        model.retreiveData().observe(this, Observer {
            it?.let {

            Log.i("PaymentValuesFragment", "$it")

                paymentsHistoryList.adapter = PaymentValuesAdapter(it.paymentsHistory.sortedByDescending { it.period }.toList())

                paymentsHistoryList.setLayoutManager(LinearLayoutManager(context))


            }
        })

    }

}
