package compsevice.ua.app

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import compsevice.ua.app.di.Injector

val Activity.app: EMVApplication get() = application as EMVApplication

fun AppCompatActivity.getAppInjector(): Injector = (app).injector