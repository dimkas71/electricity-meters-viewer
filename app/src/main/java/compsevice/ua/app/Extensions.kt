package compsevice.ua.app

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import compsevice.ua.app.di.Injector
import java.text.SimpleDateFormat

val Activity.app: EMVApplication get() = application as EMVApplication

fun AppCompatActivity.getAppInjector(): Injector = (app).injector


fun Long.asDate() = SimpleDateFormat("dd/MM/yyyy").format(this)

fun String.toDate() = SimpleDateFormat("dd/MM/yyyy").parse(this).time