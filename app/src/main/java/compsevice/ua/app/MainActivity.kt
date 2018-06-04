package compsevice.ua.app

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
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

import compsevice.ua.app.activity.ContractInfoDetailActivity
import compsevice.ua.app.activity.SettingsActivity
import compsevice.ua.app.adapter.ContractInfoAdapter
import compsevice.ua.app.model.ContractInfo
import compsevice.ua.app.rest.RestApi


class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener, ContractInfoAdapter.RecyclerTouchListener.ClickListener {
    override fun onLongClick(v: View, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

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

        filter = contractInfoAdapter!!.filter

        recylcerView!!.addOnItemTouchListener(ContractInfoAdapter.RecyclerTouchListener(applicationContext, recylcerView, this))

        recylcerView!!.setHasFixedSize(true)


    }


    private fun fromJson(): List<ContractInfo> {

        val mapper = ObjectMapper()

        val infos = ArrayList<ContractInfo>()

        var `is`: InputStream? = null

        try {

            `is` = resources.openRawResource(R.raw.data)

            val cis = mapper.readValue(`is`, Array<ContractInfo>::class.java)
            infos.addAll(Arrays.asList(*cis))
            Log.i("Info", infos.toString())
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


        return infos

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
            R.id.action_run -> {
                val intent = Intent(applicationContext, ContractInfoDetailActivity::class.java)
                startActivity(intent)
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
        filter!!.filter(newText)
        return false
    }

    override fun onClick(v: View, position: Int) {
        val info = contractInfoAdapter!!.getDataAt(position)
        Log.i(TAG, "Data for the position$info")

        val intent = Intent(applicationContext, ContractInfoDetailActivity::class.java)

        startActivity(intent)

    }

    private class Downloader(activity: MainActivity, progressBar: ProgressBar?) : AsyncTask<String, Void, List<ContractInfo>?>() {

        private val progressBar: WeakReference<ProgressBar?>
        private val activity: WeakReference<MainActivity>

        init {
            this.progressBar = WeakReference(progressBar)
            this.activity = WeakReference(activity)
        }

        override fun doInBackground(vararg queries: String): List<ContractInfo>? {

            val service = RestApi.service(RestApi.BASE_URL)

            var cis: List<ContractInfo>? = ArrayList()
            try {
                val response = service.contracts(queries[0]).execute()
                cis = response.body()
            } catch (e: IOException) {
                e.printStackTrace()
            }


            return cis
        }

        override fun onPreExecute() {
            progressBar.get()?.setVisibility(View.VISIBLE)
            super.onPreExecute()
        }

        override fun onPostExecute(cis: List<ContractInfo>?) {
            progressBar.get()?.setVisibility(View.GONE)

            val adapter = activity.get()?.contractInfoAdapter
            adapter!!.update(cis)

            val filter = activity.get()?.filter as ContractInfoAdapter.ContractInfoFilter?

            filter!!.update(cis)

        }

    }

    companion object {


        private val TAG = MainActivity::class.java.simpleName
    }


}
