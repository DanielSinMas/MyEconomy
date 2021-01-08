package com.danielgimenez.myeconomy.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.danielgimenez.myeconomy.R
import com.danielgimenez.myeconomy.domain.model.Expense
import com.danielgimenez.myeconomy.ui.adapter.ExpenseAdapter

class ExpensesComponent @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0,
        defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    private var recycler: RecyclerView
    private lateinit var expenseAdapter: ExpenseAdapter
    private lateinit var listener: ExpenseAdapter.ChangeMonthListener

    init{
        val view = LayoutInflater.from(context).inflate(R.layout.expenses_component_layout, this, true)
        orientation = VERTICAL
        recycler = view.findViewById(R.id.expense_recycler)

    }

    fun setListener(listener: ExpenseAdapter.ChangeMonthListener){
        this.listener = listener
        expenseAdapter = ExpenseAdapter(ArrayList(), ArrayList(), listener)
        recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = expenseAdapter
        }
    }

    fun addExpense(expense: Expense){
        (recycler?.adapter as ExpenseAdapter).addExpense(expense)
        (recycler?.adapter as ExpenseAdapter).notifyDataSetChanged()
        (recycler?.adapter as ExpenseAdapter).notifyItemChanged(0)
    }

    fun setList(list: ArrayList<Expense>){
        (recycler?.adapter as ExpenseAdapter).setList(list)
        (recycler?.adapter as ExpenseAdapter).notifyDataSetChanged()
        (recycler?.adapter as ExpenseAdapter).notifyItemChanged(0)
    }

    fun setTypes(types: List<String>){
        (recycler?.adapter as ExpenseAdapter).setTypes(types)
    }
}