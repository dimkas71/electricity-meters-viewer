package compsevice.ua.app.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import compsevice.ua.app.R
import compsevice.ua.app.activity.Counter
import kotlinx.android.synthetic.main.item_contract_info_detail.view.*


class ContractInfoDetailCounterAdapter(val context: Context, var list: MutableList<Counter>) : RecyclerView.Adapter<ContractInfoDetailCounterAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_contract_info_detail, parent,false))


    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val counter = list[position]
        holder.itemView.textViewPeriod.text = counter.periodToString()
        holder.itemView.textViewValue.text = counter.value.toString()
        holder.itemView.textViewDelta.text = counter.delta.toString()
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {}

}