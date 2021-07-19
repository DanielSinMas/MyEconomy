package com.danielgimenez.myeconomy.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.danielgimenez.myeconomy.R
import com.danielgimenez.myeconomy.domain.model.Expense
import com.danielgimenez.myeconomy.domain.model.Type
import com.danielgimenez.myeconomy.utils.DateFunctions

class ExpenseTypeAdapter(val list: ArrayList<Expense>, val type: Type): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1

    class ViewHolder(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder(inflater.inflate(
        R.layout.expense_recycler_item, parent, false)){

        var amount: TextView? = null
        var date: TextView? = null
        var description: TextView? = null
        var layout: LinearLayout? = null
        var separator: View? = null

        init {
            amount = itemView.findViewById(R.id.expense_item_amount)
            date = itemView.findViewById(R.id.expense_item_date)
            description = itemView.findViewById(R.id.expense_item_description)
            layout = itemView.findViewById(R.id.expense_item_layout)
            separator = itemView.findViewById(R.id.expense_item_separator)
        }
    }

    class HeaderViewHolder(inflater: LayoutInflater, parent: ViewGroup, val list: ArrayList<Expense>): RecyclerView.ViewHolder(
            inflater.inflate(R.layout.expense_recycler_item_header, parent, false)){

        var total_tv: TextView? = null

        init {
            total_tv = itemView.findViewById(R.id.expense_recycler_item_header_total_tv)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        if(viewType == TYPE_ITEM) return ViewHolder(inflater, parent)
        else return HeaderViewHolder(inflater, parent, list)
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == 0) TYPE_HEADER
        else TYPE_ITEM
    }

    override fun getItemCount(): Int {
        return list.size +1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ViewHolder) {
            if (list[position-1].amount % 1 > 0) holder.amount?.text = list[position-1].amount.toString().plus("€")
            else holder.amount?.text = list[position-1].amount.toInt().toString().plus("€")
            holder.date?.text = DateFunctions.getDateToShow(list[position-1].date.toString())
            if (list[position-1].description.isEmpty()) holder.description?.text = "No description"
            else holder.description?.text = list[position-1].description

            if (position == list.size) holder.separator?.visibility = View.GONE
        }
        else if(holder is HeaderViewHolder){
            var total = 0f
            list.map{ total += it.amount}
            if(total % 1 > 0) holder.total_tv?.text = total.toString().plus("€")
            else holder.total_tv?.text = total.toInt().toString().plus("€")
        }
    }
}