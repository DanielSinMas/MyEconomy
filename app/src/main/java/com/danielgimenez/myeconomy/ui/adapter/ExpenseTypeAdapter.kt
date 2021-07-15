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

class ExpenseTypeAdapter(val list: ArrayList<Expense>, val type: Type): RecyclerView.Adapter<ExpenseTypeAdapter.ViewHolder>() {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(list[position].amount % 1 > 0) holder.amount?.text = list[position].amount.toString().plus("€")
        else holder.amount?.text = list[position].amount.toInt().toString().plus("€")
        holder.date?.text = DateFunctions.getDateToShow(list[position].date.toString())
        if(list[position].description.isEmpty()) holder.description?.text = "No description"
        else holder.description?.text = list[position].description

        if(position == list.size -1) holder.separator?.visibility = View.GONE
    }

    override fun getItemCount(): Int {
        return list.size
    }
}