package compsevice.ua.app.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.preference.DialogPreference
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import compsevice.ua.app.R
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("NewApi")
class DatePickerPreference(context: Context, attrs: AttributeSet) : DialogPreference(context, attrs), CalendarView.OnDateChangeListener {
    private var date: String = ""

    private lateinit var calendarView: CalendarView

    private val calendar: Calendar = Calendar.getInstance()

    init {

        dialogLayoutResource = R.layout.datepicker_dialog
        positiveButtonText = context.resources.getString(R.string.pref_begin_date_ok_button)
        negativeButtonText = context.resources.getString(R.string.pref_begin_date_cancel_button)
        dialogIcon = null

    }

    override fun onSelectedDayChange(view: CalendarView?, year: Int, month: Int, dayOfMonth: Int) {
        Log.i(TAG, "($dayOfMonth, $month, $year)")
        calendar.set(year, month, dayOfMonth)
    }

    override fun onBindDialogView(view: View?) {
        Log.i(TAG, "OnBindDialogView")
        calendarView = view?.findViewById(R.id.calendarView) ?: CalendarView(context)
        calendarView.setOnDateChangeListener(this)
        super.onBindDialogView(view)
    }

    override fun onDialogClosed(positiveResult: Boolean) {
        if (positiveResult) {
            val formatter = SimpleDateFormat("dd/MM/yyyy")
            date = formatter.format(calendar.time)
            persistString(date)
        }
    }

    override fun onSetInitialValue(restorePersistedValue: Boolean, defaultValue: Any?) {
        if (restorePersistedValue) {
            date = getPersistedString(DEFAULT_VALUE)
        } else {
            date = defaultValue as String
            persistString(date)
        }
    }

    override fun onGetDefaultValue(a: TypedArray?, index: Int): Any = a?.getString(index) ?: ""

    companion object {
        val DEFAULT_VALUE = ""
        val TAG = DatePickerPreference::class.java.simpleName
    }

}