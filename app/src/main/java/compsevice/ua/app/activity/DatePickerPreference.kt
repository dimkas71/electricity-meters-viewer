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
import compsevice.ua.app.toDate
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("NewApi")
class DatePickerPreference(context: Context, attrs: AttributeSet) : DialogPreference(context, attrs), CalendarView.OnDateChangeListener {

    private var date: Long = 0

    private lateinit var calendarView: CalendarView

    private val calendar: Calendar = Calendar.getInstance()

    init {

        dialogLayoutResource = R.layout.datepicker_dialog
        positiveButtonText = context.resources.getString(R.string.pref_begin_date_ok_button)
        negativeButtonText = context.resources.getString(R.string.pref_begin_date_cancel_button)
        dialogIcon = null

    }

    override fun onSelectedDayChange(view: CalendarView, year: Int, month: Int, dayOfMonth: Int) {
        Log.i(TAG, "($dayOfMonth, $month, $year)")
        calendar.set(year, month, dayOfMonth)
    }

    @Deprecated("Deprecated in Java")
    override fun onBindDialogView(view: View?) {
        Log.i(TAG, "OnBindDialogView")
        calendarView = view?.findViewById(R.id.calendarView) ?: CalendarView(context)
        calendarView.setOnDateChangeListener(this)
        calendarView.date = date
        super.onBindDialogView(view)
    }

    @Deprecated("Deprecated in Java")
    override fun onDialogClosed(positiveResult: Boolean) {
        if (positiveResult) {
            date = calendar.timeInMillis
            persistLong(date)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onSetInitialValue(restorePersistedValue: Boolean, defaultValue: Any?) {
        if (restorePersistedValue) {
            date = getPersistedLong(0)
        } else {
            persistLong(date)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onGetDefaultValue(a: TypedArray?, index: Int): Any {
       val defaultValue = a?.getString(index) ?: "01/01/1970"
       date = SimpleDateFormat("dd/MM/yyyy").parse(defaultValue).time
        return date
    }

    companion object {
        val DEFAULT_VALUE = ""
        val TAG = DatePickerPreference::class.java.simpleName
    }

}