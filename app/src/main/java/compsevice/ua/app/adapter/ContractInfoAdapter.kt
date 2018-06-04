package compsevice.ua.app.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView

import java.lang.ref.WeakReference
import java.util.ArrayList

import compsevice.ua.app.R
import compsevice.ua.app.adapter.ContractInfoAdapter.ViewHolder
import compsevice.ua.app.model.ContractInfo
import compsevice.ua.app.model.Counter
import compsevice.ua.app.model.ServiceType

class ContractInfoAdapter(private val context: Context, private var contracts: List<ContractInfo>?) : RecyclerView.Adapter<ContractInfoAdapter.ViewHolder>(), Filterable {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(context)
        return ViewHolder(inflater.inflate(R.layout.list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val contractInfo = contracts!![position]
        holder.tvSectorNumber.text = context.resources.getString(R.string.text_sector_number, contractInfo.sectorNumber)
        holder.tvContractNumber.text = context.resources.getString(R.string.text_contract_number,
                contractInfo.number)

        holder.tvOwner.text = contractInfo.owner

        val sb = StringBuilder()

        for ((_, factoryNumber, value) in contractInfo.counters) {

            sb.append(context.resources.getString(R.string.text_counters_factory_number))
                    .append(factoryNumber)
                    .append("    ")
                    .append(String.format("%s %d", context.resources.getString(R.string.text_counters_value), value))
                    .append("\n")
        }

        sb.deleteCharAt(sb.toString().length - 1)


        holder.tvCounters.text = sb.toString()

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
        return contracts!!.size
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


    class ViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {

        var tvSectorNumber: TextView
        var tvContractNumber: TextView
        var tvOwner: TextView
        var tvCounters: TextView
        var tvCreditElectricity: TextView
        var tvCreditVideo: TextView
        var tvCreditService: TextView

        init {
            tvSectorNumber = parent.findViewById(R.id.text_sector_number)
            tvContractNumber = parent.findViewById(R.id.text_number)
            tvOwner = parent.findViewById(R.id.text_owner)
            tvCounters = parent.findViewById(R.id.text_counters)
            tvCreditElectricity = parent.findViewById(R.id.text_credit_electricity)
            tvCreditVideo = parent.findViewById(R.id.text_credit_video)
            tvCreditService = parent.findViewById(R.id.text_credit_serivice)
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
            adapter.get()?.update(results.values as List<ContractInfo>)
        }


        fun update(newInfos: List<ContractInfo>?) {
            this.originalInfos = ArrayList(newInfos)
        }

        companion object {

            private val TAG = ContractInfoFilter::class.java.simpleName
        }

    }

    class RecyclerTouchListener(context: Context, recyclerView: RecyclerView?, private val clickListener: ClickListener?) : RecyclerView.OnItemTouchListener {

        private val gestureDetector: GestureDetector

        init {
            this.gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {

                override fun onSingleTapUp(e: MotionEvent): Boolean {
                    return true
                }

                override fun onLongPress(e: MotionEvent) {
                    val child = recyclerView?.findChildViewUnder(e.x, e.y)

                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child))
                    }
                }
            })

        }

        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {

            val child = rv.findChildViewUnder(e.x, e.y)
            if (child != null && clickListener != null) {
                if (e.action == MotionEvent.ACTION_UP) {
                    clickListener.onClick(child, rv.getChildAdapterPosition(child))
                }
            }

            return false
        }

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}


        interface ClickListener {

            fun onClick(v: View, position: Int)

            fun onLongClick(v: View, position: Int)
        }

        companion object {

            private val TAG = RecyclerTouchListener::class.java.simpleName
        }
    }


}
