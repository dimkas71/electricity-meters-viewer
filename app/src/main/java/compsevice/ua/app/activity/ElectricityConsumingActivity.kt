package compsevice.ua.app.activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.DatePicker
import compsevice.ua.app.R
import compsevice.ua.app.activity.adapter.ElectricityConsumingAdapter
import compsevice.ua.app.activity.model.ConsumingBySectors
import compsevice.ua.app.rest.RestApi
import kotlinx.android.synthetic.main.activity_electricity_consuming.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.SimpleFormatter

class ElectricityConsumingActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    private lateinit var currentPeriod: Calendar

    private lateinit var datePicker: DatePickerDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_electricity_consuming)

        var cal = Calendar.getInstance()
        cal.set(Calendar.DAY_OF_MONTH, 1)
        cal.add(Calendar.MONTH, -1)

        currentPeriod = cal
        updatePeriodRepresentation()

        datePicker = DatePickerDialog(this, this, currentPeriod.get(Calendar.YEAR), currentPeriod.get(Calendar.MONTH), currentPeriod.get(Calendar.DAY_OF_MONTH))

        loadData()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        Log.i(ElectricityConsumingActivity::class.java.simpleName, "($year, $month, $dayOfMonth)")
        currentPeriod.set(Calendar.YEAR, year)
        currentPeriod.set(Calendar.MONTH, month)
        currentPeriod.set(Calendar.DAY_OF_MONTH, 1) //cast to the first day of month

        loadData()
        updatePeriodRepresentation()

    }

    fun onClick(view: View) {
        datePicker.show()
    }

    private fun updatePeriodRepresentation() {
        periodRepresentation.text = SimpleDateFormat("dd.MM.yyyy").format(currentPeriod.time)
    }

    private fun loadData() {

        val service = RestApi.service(this)
        val formatter = SimpleDateFormat("yyyyMMdd")
        val period = formatter.format(currentPeriod.time)

        progressBar.visibility = View.VISIBLE
        service.consuming(period).enqueue(object: Callback<List<ConsumingBySectors>> {

            @SuppressLint("LongLogTag")
            override fun onFailure(call: Call<List<ConsumingBySectors>>?, t: Throwable?) {
                progressBar.visibility = View.GONE
                Log.i("ElectricityConsumingActivity", "Error on getting consuming from net")
            }

            @SuppressLint("LongLogTag")
            override fun onResponse(call: Call<List<ConsumingBySectors>>?, response: Response<List<ConsumingBySectors>>?) {
                progressBar.visibility = View.GONE

                Log.i("ElectricityConsumingActivity", "${response?.body()}")

                val items = response?.body()?.sortedBy { it.sectorNumber } ?: emptyList()

                consumingBySectors.adapter = ElectricityConsumingAdapter(items)
                consumingBySectors.setLayoutManager(LinearLayoutManager(applicationContext))

            }

        })






    }
}
