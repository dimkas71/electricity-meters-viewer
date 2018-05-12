package compsevice.ua.app.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import compsevice.ua.app.R
import java.util.*

class ContractInfoDetailActivity() : AppCompatActivity() {

    val data = mutableListOf<Counter>()

    companion object {
        val TAG: String? = ContractInfoDetailActivity::class.simpleName;
        val COUNTER_THRESHHOLD: Int = 10
    }

    init {
        data.addAll(Array<Counter>(COUNTER_THRESHHOLD, { n ->
            Counter(Calendar.getInstance().apply {
                set(2018, Calendar.MAY, n)
            }, n, 1)
        }))
    }


    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contract_info_detail)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerViewByPeriod)





    }
}

data class Counter(val period: Calendar, val value: Int, val delta: Int)

