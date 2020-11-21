package com.danielgimenez.myeconomy.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.danielgimenez.myeconomy.R
import com.danielgimenez.myeconomy.domain.model.Expense

class ExpenseAdapter(private var list: ArrayList<Expense>): RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    class ExpenseViewHolder(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder(inflater.inflate(
        R.layout.expense_recycler_item, parent, false)){
        private var amount: TextView? = null
        private var date: TextView? = null
        private var type: TextView? = null
        private var layout: LinearLayout? = null

        init {
            amount = itemView.findViewById(R.id.expense_item_amount)
            date = itemView.findViewById(R.id.expense_item_date)
            type = itemView.findViewById(R.id.expense_item_type)
            layout = itemView.findViewById(R.id.expense_item_layout)
        }

        fun bind(expense: Expense, position: Int){
            amount?.text = expense.amount.toString()
            date?.text = expense.date
            type?.text = expense.type.toString()

            if(position % 2 == 1){
                layout?.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.recyclerBackgroundColor))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ExpenseViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = list[position]
        holder.bind(expense, position)
    }

    override fun getItemCount() = list.size

    fun setList(list: ArrayList<Expense>){
        this.list = list
    }

    fun addExpense(expense: Expense){
        list.add(expense)
    }
}