package compsevice.ua.app.activity

import android.content.Intent
import android.os.Bundle
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

class ContractInfoDetailActivity : AppCompatActivity() {

    lateinit var contractUUID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contract_info_detail_new)


        val adapter = ViewPagerAdapter(applicationContext, supportFragmentManager)

        val pager = findViewById<ViewPager>(R.id.pager)

        pager.adapter = adapter

        val tabs = findViewById<TabLayout>(R.id.tabs)

        tabs.setupWithViewPager(pager)

        val contract: ContractInfo = intent.extras[KEY_CONTRACT] as ContractInfo

        contractUUID = contract.uuid

        Log.i(ContractInfoDetailActivity::class.java.simpleName, "Contract $contract")

        val fab = findViewById<FloatingActionButton>(R.id.fab)

        fab.setOnClickListener {
           Snackbar.make(it, "Contractinfo uuid: $contractUUID", Snackbar.LENGTH_LONG)
        }




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