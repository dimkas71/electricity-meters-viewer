package compsevice.ua.app.activity.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import compsevice.ua.app.R
import kotlinx.android.synthetic.main.counter_info_list_item.view.counterInfoSerialNumber
import kotlinx.android.synthetic.main.counter_info_list_item.view.counterInfoValue
import kotlinx.android.synthetic.main.counter_info_list_item.view.serialNumberDescription

class CounterInfoAdapter(private val items: List<Item>) : RecyclerView.Adapter<CounterInfoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(parent.context, LayoutInflater.from(parent.context).inflate(R.layout.counter_info_list_item, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }


    class ViewHolder(val context: Context, itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Item) {
            itemView.counterInfoSerialNumber.text = item.serialNumber
            itemView.counterInfoValue.text = item.value.toString()

            if (item.isOff) {

                val color = this.context.resources.getColor(R.color.colorCounterIsOff)
                itemView.counterInfoSerialNumber.setTextColor(color)
                itemView.counterInfoValue.setTextColor(color)
                itemView.serialNumberDescription.setTextColor(color)

            }

        }

    }

}


data class Item(val serialNumber: String, val value: Long, val isOff: Boolean = false)