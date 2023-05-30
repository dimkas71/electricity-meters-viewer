package compsevice.ua.app.activity

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceFragment
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import compsevice.ua.app.R
import compsevice.ua.app.asDate

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
            when(key) {
                URL_KEY -> updateSummary(URL_KEY, String::class)
                USER_KEY -> updateSummary(USER_KEY, String::class)
                SEARCH_VARIANT_KEY -> updateSummary(SEARCH_VARIANT_KEY, String::class)
                BEGIN_DATE_KEY -> updateSummary(BEGIN_DATE_KEY, Long::class)
                CHECK_DATE_MONTH_KEY -> updateSummary(CHECK_DATE_MONTH_KEY, Int::class)
                else -> ""
            }

        }

        @Deprecated("Deprecated in Java")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.preferences)

            listOf(URL_KEY, USER_KEY, SEARCH_VARIANT_KEY)
                    .forEach{updateSummary(it, String::class)}

            updateSummary(BEGIN_DATE_KEY, Long::class)
            updateSummary(CHECK_DATE_MONTH_KEY, Int::class)

        }

        @Deprecated("Deprecated in Java")
        override fun onResume() {
            super.onResume()
            preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        }

        @Deprecated("Deprecated in Java")
        override fun onPause() {
            super.onPause()
            preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        }

        companion object {

            const val URL_KEY = "pref_url"
            const val USER_KEY = "pref_user"
            const val SEARCH_VARIANT_KEY = "pref_search_variant"
            const val BEGIN_DATE_KEY = "pref_begin_date"
            const val CHECK_DATE_MONTH_KEY = "pref_check_date_month"

            val MAP = mapOf(
                    URL_KEY to R.string.pref_url_summary,
                    USER_KEY to R.string.pref_user_summary,
                    SEARCH_VARIANT_KEY to R.string.pref_search_variant_summary,
                    BEGIN_DATE_KEY to R.string.pref_begin_date_summary,
                    CHECK_DATE_MONTH_KEY to R.string.pref_check_date_month_summary)
        }

        private fun <T> updateSummary(key: String, type: T) {

            val resId: Int = MAP[key] ?: R.string.pref_password_summary


            val sp = preferenceScreen.sharedPreferences

            when(type) {

                String::class -> findPreference(key)?.summary = getString(resId, sp.getString(key, ""))
                Long::class -> findPreference(key)?.summary = getString(resId, sp.getLong(key, 0).asDate())
                Int::class -> findPreference(key)?.summary = getString(resId, sp.getString(key, ""))
            }




        }


    }

}
