package compsevice.ua.app

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import compsevice.ua.app.di.Injector
import java.text.SimpleDateFormat
import java.util.*

val Activity.app: EMVApplication get() = application as EMVApplication

fun AppCompatActivity.getAppInjector(): Injector = (app).injector


fun Long.asDate() = SimpleDateFormat("dd/MM/yyyy").format(this)

fun String.toDate() = SimpleDateFormat("dd/MM/yyyy").parse(this).time

fun monthBetween(from: Date, to: Date): Int {
    val start = Calendar.getInstance()
    start.time = from

    val end = Calendar.getInstance()
    end.time = to

    var monthsBetween = 0
    var dateDiff = end[Calendar.DAY_OF_MONTH] - start[Calendar.DAY_OF_MONTH]

    if (dateDiff < 0) {
        val borrow = end.getActualMaximum(Calendar.DAY_OF_MONTH)

        dateDiff = end[Calendar.DAY_OF_MONTH] + borrow - start[Calendar.DAY_OF_MONTH]

        monthsBetween--
        if (dateDiff > 0) {
            monthsBetween++
        }
    } else {
        monthsBetween++
    }
    monthsBetween += end[Calendar.MONTH] - start[Calendar.MONTH]
    monthsBetween += (end[Calendar.YEAR] - start[Calendar.YEAR]) * 12
    return monthsBetween
}