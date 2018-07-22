package compsevice.ua.app.activity.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import compsevice.ua.app.R
import compsevice.ua.app.viewmodel.PaymentsHistory
import java.text.SimpleDateFormat

class PaymentValuesAdapter(val items: List<PaymentsHistory>) : RecyclerView.Adapter<PaymentValuesAdapter.ViewHolder>() {

    private val formatter: SimpleDateFormat = SimpleDateFormat("dd.MM.yy")


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.payments_history_list_item, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items[position]
        holder.bind(data)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: PaymentsHistory) {

            //1. Period
            val periodEl = itemView.findViewById<TextView>(R.id.paymentsListPeriod)
            periodEl.text = formatter.format(data.period)


            //2. Counter
            val counterEl = itemView.findViewById<TextView>(R.id.paymentsListCounter)
            counterEl.text = data.counter

            //3. Accrued
            val accruedEl = itemView.findViewById<TextView>(R.id.paymentsListAccrued)
            accruedEl.text = "%.2f".format(data.accrued)

            //4. Paid for
            val paidForEl = itemView.findViewById<TextView>(R.id.paymentsListPaidFor)
            paidForEl.text = "%.2f".format(data.paid)
        }

    }

}