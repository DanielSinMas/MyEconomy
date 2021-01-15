package com.danielgimenez.myeconomy.ui.adapter.viewholders

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.danielgimenez.myeconomy.R
import com.danielgimenez.myeconomy.domain.model.Expense
import com.danielgimenez.myeconomy.ui.viewmodel.FormularyViewModel
import com.danielgimenez.myeconomy.utils.DateFunctions
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder

class ExpenseViewHolder(view: View): ChildViewHolder(view) {

    private var amount: TextView = itemView.findViewById(R.id.expense_item_amount)
    private var date: TextView = itemView.findViewById(R.id.expense_item_date)
    private var type: TextView = itemView.findViewById(R.id.expense_item_type)
    private var layout: LinearLayout = itemView.findViewById(R.id.expense_item_layout)

    fun setExpense(expense: Expense, position: Int){
        amount.text = expense.amount.toString()
        date.text = DateFunctions.getDateToShow(expense.date.toString())
        type.text = FormularyViewModel.types[expense.type-1].name
        if(position % 2 == 1) layout.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.recyclerBackgroundColor))
        else layout.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.white))
    }
}