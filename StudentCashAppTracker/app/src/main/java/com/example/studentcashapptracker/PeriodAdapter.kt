package com.example.studentcashapptracker

import android.content.Context
import android.location.Location
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import java.util.ArrayList

class PeriodAdapter (mContext: Context) : BaseAdapter() {
    private val list = ArrayList<Period>()

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var newView = convertView
        val holder: ViewHolder
        val curr = list[position]
        if (null == convertView) {
            holder = ViewHolder()
            newView = inflater!!.inflate(R.layout.previous_periods, parent, false)
            holder.startDate = newView.findViewById<View>(R.id.startDateText) as TextView
            holder.endDate = newView.findViewById<View>(R.id.endDateText) as TextView
            holder.totalSpending = newView.findViewById<View>(R.id.totalExpenseText) as TextView
            newView.tag = holder
        } else {
            holder = newView?.tag as ViewHolder
        }
        holder.startDate?.text = curr.startDate
        holder.endDate?.text = curr.endDate
        holder.totalSpending?.text = curr.totalSpending.toString()
        return newView
    }

    internal class ViewHolder {
        var startDate: TextView? = null
        var endDate: TextView? = null
        var totalSpending: TextView? = null
    }

    fun add(listItem: Period) {
        list.add(listItem)
        notifyDataSetChanged()
    }

    fun removeAllViews() {
        list.clear()
        notifyDataSetChanged()
    }

    companion object {
        private var inflater: LayoutInflater? = null
    }

    init {
        inflater = LayoutInflater.from(mContext)
    }

}