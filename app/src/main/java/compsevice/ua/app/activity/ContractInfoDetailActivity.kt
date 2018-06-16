package compsevice.ua.app.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.MenuCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import compsevice.ua.app.R

class ContractInfoDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contract_info_detail)

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

