package compsevice.ua.app.activity

import android.app.ActionBar
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.TextView
import compsevice.ua.app.R
import kotlinx.android.synthetic.main.activity_contract_info_detail.*

class ContractInfoDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contract_info_detail)


        val adapter = ViewPagerAdapter(supportFragmentManager)

        adapter.addFragment(CommonInfoFragment(), getString(R.string.contract_info_detail_tab_common))
        adapter.addFragment(CounterValuesFragment(), getString(R.string.contract_info_detail_tab_counter_history))
        adapter.addFragment(PaymentValuesFragment(), getString(R.string.contract_info_detail_tab_payment_history))


        pager.adapter = adapter

        tabs.setupWithViewPager(pager)

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