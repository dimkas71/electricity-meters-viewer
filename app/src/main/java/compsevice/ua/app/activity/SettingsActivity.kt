package compsevice.ua.app.activity

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceFragment
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import compsevice.ua.app.R

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fragmentManager.beginTransaction()
                .replace(android.R.id.content, SettingsFragment())
                .commit()

    }


    class SettingsFragment : PreferenceFragment(), SharedPreferences.OnSharedPreferenceChangeListener {
        override fun onSharedPreferenceChanged(sp: SharedPreferences?, key: String?) {
            Log.i(SettingsFragment::class.java.simpleName, sp?.getString(key, ""))

            when(key) {
                URL_KEY -> updateSummary(URL_KEY, R.string.pref_url_summary)
                USER_KEY -> updateSummary(USER_KEY, R.string.pref_user_summary)
                SEARCH_VARIANT_KEY -> updateSummary(SEARCH_VARIANT_KEY, R.string.pref_search_variant_summary)
                else -> ""
            }

        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.preferences)

            updateSummary(URL_KEY, R.string.pref_url_summary)
            updateSummary(USER_KEY, R.string.pref_user_summary)
            updateSummary(SEARCH_VARIANT_KEY, R.string.pref_search_variant_summary)

        }

        override fun onResume() {
            super.onResume()
            preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        }

        override fun onPause() {
            super.onPause()
            preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        }

        companion object {

            const val URL_KEY = "pref_url"
            const val URL_SUMMARY_KEY = "pref_url_summary"
            const val USER_KEY = "pref_user"
            const val USER_SUMMARY_KEY = "pref_user_summary"
            const val SEARCH_VARIANT_KEY = "pref_search_variant"
            const val SEARCH_VARIANT_SUMMARY_KEY = "pref_search_variant_summary"

        }

        private fun updateSummary(key: String, resId: Int) {
            findPreference(key)?.summary = getString(resId, preferenceScreen.sharedPreferences.getString(key, ""))
        }


    }

}
