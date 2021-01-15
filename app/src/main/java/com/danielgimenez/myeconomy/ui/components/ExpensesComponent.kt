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
import com.danielgimenez.myeconomy.ui.adapter.ExpenseTypeGroup
import com.danielgimenez.myeconomy.ui.adapter.NewExpenseAdapter

class ExpensesComponent @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0,
        defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    private var recycler: RecyclerView
    private lateinit var listener: ExpenseAdapter.ChangeMonthListener
    private var types: List<String>? = ArrayList<String>()

    init{
        val view = LayoutInflater.from(context).inflate(R.layout.expenses_component_layout, this, true)
        orientation = VERTICAL
        recycler = view.findViewById(R.id.expense_recycler)
        setList(ArrayList())
    }

    fun setListener(listener: ExpenseAdapter.ChangeMonthListener){
        this.listener = listener
    }

    fun addExpense(expense: Expense){
        (recycler.adapter as NewExpenseAdapter).addExpense(expense)
    }

    fun setList(list: ArrayList<Expense>){
        val newList = ArrayList<ExpenseTypeGroup>()
        if(types != null){
            for(type in types!!){
                newList.add(ExpenseTypeGroup(type, list.filter { it.type-1 == types?.indexOf(type) } as ArrayList<Expense>))
            }
        }
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = NewExpenseAdapter(newList)

    }

    fun setTypes(types: List<String>){
        this.types = types
    }
}