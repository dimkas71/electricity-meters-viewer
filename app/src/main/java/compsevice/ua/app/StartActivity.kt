package compsevice.ua.app

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import compsevice.ua.app.R.id.change
import compsevice.ua.app.R.id.textView
import compsevice.ua.app.activity.SettingsActivity
import compsevice.ua.app.rest.RandomNumber
import compsevice.ua.app.viewmodel.RandomNumberViewModel
import kotlinx.android.synthetic.main.activity_start.*
import java.util.*
import javax.inject.Inject

class StartActivity : AppCompatActivity() {

    lateinit var model: RandomNumberViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        getAppInjector().inject(this)

        model = ViewModelProviders.of(this, viewModelFactory).get(RandomNumberViewModel::class.java)
        model.getRandom().observe(this, Observer<RandomNumber> { t -> textView.text = "Random number: ${t ?: -1}" })

        change.setOnClickListener {
            model.random.postValue(RandomNumber(random = Random().nextInt(100).toLong()))
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            R.id.action_run -> {
                Log.i(StartActivity::class.java.simpleName, PreferenceManager.getDefaultSharedPreferences(this).getString("pref_user", ""))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
