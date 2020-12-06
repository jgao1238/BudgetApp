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

class ExpenseAdapter(mContext: Context) : BaseAdapter() {
    private val list = ArrayList<Expense>()

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    //Reference: Lab 11 from CMSC436
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var newView = convertView
        val holder: ViewHolder
        val curr = list[position]
        if (null == convertView) {
            holder = ViewHolder()
            newView = inflater!!.inflate(R.layout.expense_menu_item, parent, false)
            holder.cost = newView.findViewById<View>(R.id.menuCostText) as TextView
            holder.date = newView.findViewById<View>(R.id.menuDateText) as TextView
            holder.description = newView.findViewById<View>(R.id.menuDescriptionText) as TextView
            newView.tag = holder
        } else {
            holder = newView?.tag as ViewHolder
        }
        holder.cost?.text = curr.cost.toString()
        holder.date?.text = curr.date
        holder.description?.text = curr.description
        return newView
    }

    internal class ViewHolder {
        var cost: TextView? = null
        var date: TextView? = null
        var description: TextView? = null
    }

    fun add(listItem: Expense) {
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