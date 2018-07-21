package compsevice.ua.app.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import compsevice.ua.app.R
import compsevice.ua.app.activity.adapter.CounterValuesAdapter
import compsevice.ua.app.viewmodel.ContractInfoViewModel
import kotlinx.android.synthetic.main.contract_info_detail_counter_values.*

class CounterValuesFragment : Fragment() {

    private lateinit var model: ContractInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.contract_info_detail_counter_values, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        model = activity?.let {
            ViewModelProviders.of(it).get(ContractInfoViewModel::class.java)
        } ?: ContractInfoViewModel()


        model.retreiveData().observe(this, Observer {
           it?.let {

               val items = it.countersHistory.sortedByDescending { it.period }
               val adapter = CounterValuesAdapter(items)
               countersHistoryList.setAdapter(adapter)
           }
        })



    }

}
