package com.danielgimenez.myeconomy.ui.adapter.viewholders

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.danielgimenez.myeconomy.R
import com.danielgimenez.myeconomy.domain.model.Expense
import com.danielgimenez.myeconomy.utils.DateFunctions
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder

class ExpenseViewHolder(view: View, var context: Context): ChildViewHolder(view) {

    private var amount: TextView = itemView.findViewById(R.id.expense_item_amount)
    private var date: TextView = itemView.findViewById(R.id.expense_item_date)
    private var description: TextView = itemView.findViewById(R.id.expense_item_description)
    private var layout: LinearLayout = itemView.findViewById(R.id.expense_item_layout)

    fun setExpense(expense: Expense, position: Int){
        if(expense.amount % 1.0 != 0.0) amount.text = expense.amount.toString().plus("€")
        else amount.text = expense.amount.toInt().toString().plus("€")
        date.text = DateFunctions.getDateToShow(expense.date.toString())
        if(expense.description.isNotEmpty())
            description.text = expense.description
        else description.setTextColor(context.getColor(R.color.type_header_background))
        if(position % 2 == 1) layout.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.primaryColorBright))
        else layout.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.white))
    }
}