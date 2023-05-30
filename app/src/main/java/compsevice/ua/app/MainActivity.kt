package compsevice.ua.app

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Filter
import android.widget.ProgressBar

import com.fasterxml.jackson.databind.ObjectMapper

import java.io.IOException
import java.io.InputStream
import java.lang.ref.WeakReference
import java.util.ArrayList
import java.util.Arrays

import compsevice.ua.app.activity.ElectricityConsumingActivity
import compsevice.ua.app.activity.SettingsActivity
import compsevice.ua.app.adapter.ContractInfoAdapter
import compsevice.ua.app.model.ContractInfo
import compsevice.ua.app.rest.RestApi


class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val lastQuery = "NO_SUCH_ELEMENT"

    private var recylcerView: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    var contractInfoAdapter: ContractInfoAdapter? = null
        private set
    var filter: Filter? = null
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //1. ProgressBar
        progressBar = findViewById(R.id.progress_bar)
        recylcerView = findViewById(R.id.recycler_view)

        val layoutManager = LinearLayoutManager(applicationContext)

        recylcerView!!.layoutManager = layoutManager

        contractInfoAdapter = ContractInfoAdapter(applicationContext, ArrayList())

        recylcerView!!.adapter = contractInfoAdapter

        val itemDecoration = DividerItemDecoration(recylcerView!!.context, layoutManager.orientation)

        recylcerView!!.addItemDecoration(itemDecoration)

        filter = contractInfoAdapter?.filter

        recylcerView!!.setHasFixedSize(true)

    }


    private fun fromJson(): List<ContractInfo> {

        val mapper = ObjectMapper()

        val info = ArrayList<ContractInfo>()

        var `is`: InputStream? = null

        try {

            `is` = resources.openRawResource(R.raw.data)

            val cis = mapper.readValue(`is`, Array<ContractInfo>::class.java)
            info.addAll(Arrays.asList(*cis))
            Log.i("Info", info.toString())
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (`is` != null) {
                try {
                    `is`.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }

        return info

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        val sm = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchView.setSearchableInfo(sm.getSearchableInfo(componentName))

        searchView.setOnQueryTextListener(this)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                val i = Intent(applicationContext, SettingsActivity::class.java)
                startActivity(i)
                return true
            }
            R.id.action_consuming -> {
                startActivity(Intent(applicationContext, ElectricityConsumingActivity::class.java))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }

    override fun onQueryTextSubmit(query: String): Boolean {
        Log.i("SearchView", "Query:$query")

        if (!query.isEmpty()) {
            query(query)
        }

        return false
    }

    override fun onStart() {
        super.onStart()
    }

    private fun query(query: String) {
        val asyncTask = Downloader(this, progressBar)
        asyncTask.execute(query)
    }

    override fun onQueryTextChange(newText: String): Boolean {
        Log.i("SearchView", "Query a new text $newText")
        filter?.filter(newText)
        return false
    }

    private class Downloader(activity: MainActivity, progressBar: ProgressBar?) : AsyncTask<String, Void, List<ContractInfo>?>() {

        private val progressBar: WeakReference<ProgressBar?>
        private val activity: WeakReference<MainActivity>

        init {
            this.progressBar = WeakReference(progressBar)
            this.activity = WeakReference(activity)
        }

        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg queries: String): List<ContractInfo>? {

            val service = activity.get()?.let { RestApi.service(it) }

            var cis: List<ContractInfo>? = ArrayList()
            try {

                val queryType = activity.get()?.let {
                    val pm = PreferenceManager.getDefaultSharedPreferences(it)
                    val variantName = pm.getString(SettingsActivity.SettingsFragment.SEARCH_VARIANT_KEY, "a value")

                    val foundIndex: Int = it.resources.getStringArray(R.array.search_variant_titles).indexOfFirst { it.equals(variantName) }

                    if (foundIndex == -1) {
                        0
                    } else {
                        foundIndex
                    }
                }
                Log.i("Downloader", "Search variant: $queryType")
                val response = queryType?.let { service?.contracts(queries[0], it)?.execute() }
                cis = response?.body()
            } catch (e: IOException) {
                e.printStackTrace()
            }


            return cis
        }


        @Deprecated("Deprecated in Java")
        override fun onPreExecute() {
            progressBar.get()?.setVisibility(View.VISIBLE)
            super.onPreExecute()
        }

        @Deprecated("Deprecated in Java")
        override fun onPostExecute(cis: List<ContractInfo>?) {
            progressBar.get()?.setVisibility(View.GONE)

            val adapter = activity.get()?.contractInfoAdapter
            adapter?.update(cis)

            val filter = activity.get()?.filter as ContractInfoAdapter.ContractInfoFilter?

            filter?.update(cis)

        }

    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }


}
