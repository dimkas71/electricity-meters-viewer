package compsevice.ua.app.activity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val fragments: MutableList<Fragment> = mutableListOf()
    private val fragmentTitles: MutableList<String> = mutableListOf()

    fun addFragment(f: Fragment, title: String) {
        fragments.add(f)
        fragmentTitles.add(title)

    }

    override fun getPageTitle(position: Int): CharSequence? = fragmentTitles[position]

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size
}