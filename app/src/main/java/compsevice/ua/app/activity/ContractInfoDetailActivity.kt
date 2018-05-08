package compsevice.ua.app.activity

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.DatePicker
import compsevice.ua.app.R
import java.util.*

class ContractInfoDetailActivity() : AppCompatActivity(), OnDateSetListener {

    companion object {
        val TAG: String? = ContractInfoDetailActivity::class.simpleName;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contract_info_detail)

        val fabFrom = findViewById<FloatingActionButton>(R.id.fabFrom)

        fabFrom.setOnClickListener {
            val calendar = Calendar.getInstance()

            val dpd = DatePickerDialog(applicationContext, this,  calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_WEEK))
            dpd.show()

        }

        val fabTo = findViewById<FloatingActionButton>(R.id.fabTo)
        fabTo.setOnClickListener {

        }

    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        Log.i(TAG, year.toString())
    }

}
