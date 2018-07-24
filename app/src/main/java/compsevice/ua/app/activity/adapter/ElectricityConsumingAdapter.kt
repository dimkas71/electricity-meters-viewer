package compsevice.ua.app.activity.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import compsevice.ua.app.R
import compsevice.ua.app.activity.model.ConsumingBySectors

class ElectricityConsumingAdapter(val items: List<ConsumingBySectors>) : RecyclerView.Adapter<ElectricityConsumingAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.electricity_consuming_item, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items[position]
        holder.bind(data)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(data: ConsumingBySectors) {

            //1. sectorNumber number
            val sectorNumberEl = itemView.findViewById<TextView>(R.id.sectorNumber)
            sectorNumberEl.text = itemView.context.getString(R.string.text_sector_number, data.sectorNumber)

            //2. electricity consuming
            val consumingEl = itemView.findViewById<TextView>(R.id.consumption)
            consumingEl.text = itemView.context.getString(R.string.text_consuming_value, "%.2f".format(data.consuming))

        }

    }

}