package compsevice.ua.app.activity.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import compsevice.ua.app.R
import compsevice.ua.app.viewmodel.CounterValuesHistory
import java.text.SimpleDateFormat


class CounterValuesAdapter(val items: List<CounterValuesHistory>) : BaseExpandableListAdapter() {

    private val formatter: SimpleDateFormat = SimpleDateFormat("dd.MM.yy")

    val map: Map<String, List<CounterValuesHistory>>
            get() = items.groupBy { it.counter }


    val groups: Array<String>
        get() = items.groupBy { it.counter }.keys.toTypedArray()


    override fun getGroup(groupPosition: Int): Any =
        groups[groupPosition]

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean = true

    override fun hasStableIds(): Boolean = true

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(parent?.context).inflate(R.layout.contact_info_detail_counter_list_group, parent, false)

        val group = groups[groupPosition]

        val groupTv = view.findViewById<TextView>(R.id.counterItemTitle)

        groupTv.text = group

        return view
    }

    override fun getChildrenCount(groupPosition: Int): Int = map[groups[groupPosition]]?.size!!

    override fun getChild(groupPosition: Int, childPosition: Int): Any = map[groups[groupPosition]]?.get(childPosition)!!

    override fun getGroupId(groupPosition: Int): Long = groupPosition.toLong()

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {

        val view = convertView ?: LayoutInflater.from(parent?.context).inflate(R.layout.contact_info_detail_counter_list_child_item, parent, false)

        val periodTv = view.findViewById<TextView>(R.id.counterHistPeriod)
        val valueTv = view.findViewById<TextView>(R.id.counterHistValue)
        val consumtionTv = view.findViewById<TextView>(R.id.counterHistConsumption)


        val item = map[groups[groupPosition]]?.get(childPosition)

        item?.let {
            periodTv.text = formatter.format(it.period)
            valueTv.text = it.value.toString()

            val textKvt = parent?.context?.getString(R.string.text_kvt)

            consumtionTv.text = "${it.consumption.toString()} ($textKvt)"
        }

        return view

    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long = (groupPosition * 1024 + childPosition).toLong()

    override fun getGroupCount(): Int = groups.size
}