package compsevice.ua.app.activity

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import compsevice.ua.app.R

class ViewPagerAdapter(val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> context.getString(R.string.contract_info_detail_tab_common)
            1 -> context.getString(R.string.contract_info_detail_tab_counter_history)
            2 -> context.getString(R.string.contract_info_detail_tab_payment_history)
            else -> null
        }

    }

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> CommonInfoFragment()
            1 -> CounterValuesFragment()
            2 -> PaymentValuesFragment()
            else -> null

        }
    }

    override fun getCount(): Int = 3
}