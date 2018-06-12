package compsevice.ua.app

import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.support.v7.widget.SearchView
import android.widget.Filter
import compsevice.ua.app.R.id.change
import compsevice.ua.app.R.id.textView
import compsevice.ua.app.activity.SettingsActivity
import compsevice.ua.app.rest.RandomNumber
import compsevice.ua.app.viewmodel.RandomNumberViewModel
import kotlinx.android.synthetic.main.activity_start.*
import java.util.*
import javax.inject.Inject

class StartActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    lateinit var model: RandomNumberViewModel

    lateinit var filter: Filter


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

        update.setOnClickListener {
            model.update(seekBar.progress)
        }


        //TODO: At this place we have a faked implementation of a Filter adapter class, change it for a Adapter implementation...

        filter = object: Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {

                Log.i(this::class.java.simpleName, "Constraint: $constraint")

                val res = FilterResults().apply {
                    count = 1
                    values = listOf(constraint)
                }
                return res
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                Log.i(this::class.java.simpleName, "Constraints: $constraint. Results: $results")
            }

        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val sv = menu.findItem(R.id.action_search).actionView as SearchView
        val sm = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        sv.setSearchableInfo(sm.getSearchableInfo(componentName))
        sv.setOnQueryTextListener(this)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        Log.i(TAG, "Query ${query}")
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Log.i(TAG, "query change ${newText}")
        filter.filter(newText)
        return true
    }

    companion object {
        val TAG = StartActivity::class.java.simpleName
    }

}
