package compsevice.ua.app.activity

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import compsevice.ua.app.R
import compsevice.ua.app.adapter.KEY_CONTRACT
import compsevice.ua.app.model.ContractInfo
import compsevice.ua.app.rest.RestApi
import compsevice.ua.app.viewmodel.Client
import compsevice.ua.app.viewmodel.ContractInfoViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class ContractInfoDetailActivity : AppCompatActivity() {

    private lateinit var model: ContractInfoViewModel

    lateinit var contractUUID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contract_info_detail_new)


        val contract: ContractInfo = intent.extras[KEY_CONTRACT] as ContractInfo
        contractUUID = contract.uuid
        Log.i(ContractInfoDetailActivity::class.java.simpleName, "Contract $contract")

        model = ViewModelProviders.of(this).get(ContractInfoViewModel::class.java)
        updateDataAsync()

        val adapter = ViewPagerAdapter(applicationContext, supportFragmentManager)

        val pager = findViewById<ViewPager>(R.id.pager)

        pager.adapter = adapter

        val tabs = findViewById<TabLayout>(R.id.tabs)

        tabs.setupWithViewPager(pager)

        val fab = findViewById<FloatingActionButton>(R.id.fab)

        fab.setOnClickListener {
           updateDataAsync()
        }


    }

    private fun updateDataAsync() {

        val service = RestApi.service(this)

        val beginDate = Date(PreferenceManager.getDefaultSharedPreferences(applicationContext).getLong("pref_begin_date", 0))

        val formattedDate = SimpleDateFormat("yyyyMMdd").format(beginDate)

        Log.i(ContractInfoDetailActivity::class.java.simpleName, "Begin date: $formattedDate")


        service.contract(contractUUID, formattedDate).enqueue(object: Callback<compsevice.ua.app.viewmodel.ContractInfo> {
            override fun onFailure(call: Call<compsevice.ua.app.viewmodel.ContractInfo>?, t: Throwable?) {
                Log.i(ContractInfoDetailActivity::class.java.simpleName, "Error happend: ${t?.message}")
            }

            override fun onResponse(call: Call<compsevice.ua.app.viewmodel.ContractInfo>?, response: Response<compsevice.ua.app.viewmodel.ContractInfo>?) {
                response?.body()?.let {
                    Log.i(ContractInfoDetailActivity::class.java.simpleName, "Contract info: $it")
                    model.updateDate(it)
                }
            }


        })



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(applicationContext, SettingsActivity::class.java))
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        val TAG: String = ContractInfoDetailActivity::class.java.simpleName
    }

}