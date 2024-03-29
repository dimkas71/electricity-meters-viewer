package compsevice.ua.app.adapter

import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import android.support.v7.widget.RecyclerView
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.fasterxml.jackson.core.util.TextBuffer
import compsevice.ua.app.R
import compsevice.ua.app.activity.ContractInfoDetailActivity
import compsevice.ua.app.activity.SettingsActivity
import compsevice.ua.app.adapter.ContractInfoAdapter.ViewHolder
import compsevice.ua.app.model.ContractInfo
import compsevice.ua.app.model.ServiceType
import compsevice.ua.app.monthBetween
import java.lang.ref.WeakReference
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue

val KEY_CONTRACT = "KEY_CONTRACT"

class ContractInfoAdapter(private val context: Context, private var contracts: List<ContractInfo>?) : RecyclerView.Adapter<ContractInfoAdapter.ViewHolder>(), Filterable {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(context)
        return ViewHolder(inflater.inflate(R.layout.list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val contractInfo = contracts!![position]
        holder.tvSectorNumber.text = context.resources.getString(R.string.text_sector_number, contractInfo.sector)
        holder.tvContractNumber.text = context.resources.getString(R.string.text_contract_number,
                contractInfo.number)
        holder.tvCheckDate.text = context.resources.getString(R.string.check_date_Checked,
                SimpleDateFormat("dd.MM.yyyy").format(contractInfo.checkDate))

        val pm = PreferenceManager.getDefaultSharedPreferences(context)
        val accepted = pm.getString(SettingsActivity.SettingsFragment.CHECK_DATE_MONTH_KEY, "12")!!.toInt().absoluteValue

        val actual = monthBetween(contractInfo.checkDate, Date()).absoluteValue
        if (actual > accepted) {
            holder.tvCheckDate.setTextColor(context.resources.getColor(R.color.colorCheckDate))
        } else {
            //
            holder.tvCheckDate.setTextColor(context.resources.getColor(R.color.colorCheckDateStandard))
        }

        holder.tvOwner.text = contractInfo.owner

        val ssb = SpannableStringBuilder()

        for ((_, factoryNumber, value, contractNumber, isOff) in contractInfo.counters) {
            val sb = StringBuilder()
            sb.append(context.resources.getString(R.string.text_counters_factory_number))
                .append(factoryNumber)
                .append("    ")
                .append(String.format("%s %d", context.resources.getString(R.string.text_counters_value), value))
                .append("    ")
                .append(context.resources.getString(R.string.text_counters_contract_number))
                .append(contractNumber)
                .append("\n")
            val counterInfo = sb.toString()
            val ss = SpannableString(counterInfo)
            if (isOff) {
                ss.setSpan(ForegroundColorSpan(context.resources.getColor(R.color.colorCounterIsOff)), 0, ss.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            } else {
                ss.setSpan(ForegroundColorSpan(context.resources.getColor(R.color.colorCounters)), 0, ss.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            ssb.append(ss)
        }

        ssb.delete(ssb.toString().length - 1, ssb.toString().length)


        holder.tvCounters.setText(ssb, TextView.BufferType.SPANNABLE)

        val creditElectricity = contractInfo.creditByService(ServiceType.Electricity)

        holder.tvCreditElectricity.setTextColor(context.resources.getColor(R.color.colorCredit))


        if (creditElectricity > 0.0) {
            holder.tvCreditElectricity.setTextColor(context.resources.getColor(R.color.colorCreditRed))
        }

        holder.tvCreditElectricity.text = String.format("%.2f", creditElectricity)

        val creditVideo = contractInfo.creditByService(ServiceType.Video)
        holder.tvCreditVideo.setTextColor(context.resources.getColor(R.color.colorCredit))

        if (creditVideo > 0.0) {
            holder.tvCreditVideo.setTextColor(context.resources.getColor(R.color.colorCreditRed))
        }

        holder.tvCreditVideo.text = String.format("%.2f", creditVideo)

        val creditService = contractInfo.creditByService(ServiceType.Service)
        holder.tvCreditService.setTextColor(context.resources.getColor(R.color.colorCredit))

        if (creditService > 0.0) {
            holder.tvCreditService.setTextColor(context.resources.getColor(R.color.colorCreditRed))
        }

        holder.tvCreditService.text = String.format("%.2f", creditService)


    }

    override fun getItemCount(): Int {
        return contracts?.size ?: 0
    }

    override fun getFilter(): Filter {
        return ContractInfoFilter(this, this.contracts)
    }

    fun update(updated: List<ContractInfo>?) {
        this.contracts = updated
        notifyDataSetChanged()
    }

    fun getDataAt(position: Int): ContractInfo {
        return contracts!![position]
    }

    inner class ViewHolder(parent: View) : RecyclerView.ViewHolder(parent), View.OnClickListener, View.OnLongClickListener {

        override fun onLongClick(v: View?): Boolean {
            return true
        }

        override fun onClick(v: View?) {
            Log.i(ViewHolder::class.java.simpleName, "OnClick clicked $this@ViewHolder.adapterPosition")
            val intent = Intent(context, ContractInfoDetailActivity::class.java)

            val contract = contracts?.get(this@ViewHolder.adapterPosition)


            intent.putExtra(KEY_CONTRACT, contract)

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

        var tvSectorNumber: TextView
        var tvContractNumber: TextView
        var tvOwner: TextView
        var tvCounters: TextView
        var tvCreditElectricity: TextView
        var tvCreditVideo: TextView
        var tvCreditService: TextView
        var tvCheckDate: TextView

        init {
            tvSectorNumber = parent.findViewById(R.id.text_sector_number)
            tvContractNumber = parent.findViewById(R.id.text_number)
            tvOwner = parent.findViewById(R.id.text_owner)
            tvCounters = parent.findViewById(R.id.text_counters)
            tvCreditElectricity = parent.findViewById(R.id.text_credit_electricity)
            tvCreditVideo = parent.findViewById(R.id.text_credit_video)
            tvCreditService = parent.findViewById(R.id.text_credit_serivice)
            tvCheckDate = parent.findViewById(R.id.checkDate)

            parent.setOnClickListener(this)
            parent.setOnLongClickListener(this)

        }
    }

    class ContractInfoFilter internal constructor(adapter: ContractInfoAdapter, newInfos: List<ContractInfo>?) : Filter() {

        internal var originalInfos: List<ContractInfo>?= ArrayList()

        internal var adapter: WeakReference<ContractInfoAdapter>

        init {
            this.originalInfos = newInfos
            this.adapter = WeakReference(adapter)

        }


        override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
            Log.i(TAG, "Filtering on a constraint " + constraint!!)
            val results = Filter.FilterResults()

            if (constraint == null) {
                results.values = ArrayList(this.originalInfos)
                results.count = this.originalInfos?.size ?: 0
            } else {

                val filteredInfos = originalInfos?.filter { it?.matchesQuery(constraint) }?.toList()

                results.values = ArrayList(filteredInfos)
                results.count = filteredInfos?.size ?: 0

            }

            return results
        }

        override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
            Log.i(TAG, "Publishing results on the Main thread. All count is: " + results.count)
            adapter.get()?.update(results.values as List<ContractInfo>?)
        }

        fun update(newInfos: List<ContractInfo>?) {
            this.originalInfos = newInfos?.let { ArrayList(it) }
        }

        companion object {
            private val TAG = ContractInfoFilter::class.java.simpleName
        }

    }

    companion object {
        private val TAG = ContractInfoAdapter::class.java.simpleName;
    }
}
