package compsevice.ua.app.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.format.DateFormat
import compsevice.ua.app.R
import compsevice.ua.app.adapter.ContractInfoDetailCounterAdapter
import kotlinx.android.synthetic.main.activity_contract_info_detail.*
import java.util.*

val COUNTER_THRESHHOLD: Int = 10
val TAG: String? = ContractInfoDetailActivity::class.simpleName

val data: MutableList<Counter> = (1..COUNTER_THRESHHOLD).map { n ->
                Counter(Calendar.getInstance().apply {
                    set(2018, Calendar.MAY, n)
            }, n, 1)
        }.toMutableList()

data class Counter(val period: Calendar, val value: Int, val delta: Int) {
    fun periodToString(): String = DateFormat.format("dd.MM.yyyy", period).toString()
}

class ContractInfoDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contract_info_detail)
        recyclerViewByPeriod.layoutManager = LinearLayoutManager(applicationContext)
        recyclerViewByPeriod.adapter = ContractInfoDetailCounterAdapter(applicationContext, data)

    }

}

