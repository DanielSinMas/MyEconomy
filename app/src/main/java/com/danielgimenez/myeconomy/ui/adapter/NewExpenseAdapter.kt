package com.danielgimenez.myeconomy.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.danielgimenez.myeconomy.R
import com.danielgimenez.myeconomy.domain.model.Expense
import com.danielgimenez.myeconomy.ui.adapter.viewholders.ExpenseViewHolder
import com.danielgimenez.myeconomy.ui.adapter.viewholders.TypeViewHolder
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

class NewExpenseAdapter(var group: ArrayList<ExpenseTypeGroup>): ExpandableRecyclerViewAdapter<TypeViewHolder, ExpenseViewHolder>(group) {

    override fun onCreateGroupViewHolder(parent: ViewGroup?, viewType: Int): TypeViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val view = inflater.inflate(R.layout.type_expense_header_layout, parent, false)
        return TypeViewHolder(view)
    }

    override fun onCreateChildViewHolder(parent: ViewGroup?, viewType: Int): ExpenseViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val view = inflater.inflate(R.layout.expense_recycler_item, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindChildViewHolder(
        holder: ExpenseViewHolder?,
        flatPosition: Int,
        group: ExpandableGroup<*>?,
        childIndex: Int
    ) {
        val expense = (group as ExpenseTypeGroup).items[childIndex]
        holder?.setExpense(expense, childIndex)
    }

    override fun onBindGroupViewHolder(
        holder: TypeViewHolder?,
        flatPosition: Int,
        group: ExpandableGroup<*>?
    ) {
        holder?.setType(group as ExpandableGroup<Expense>)
    }

    fun addExpense(expense: Expense){
        if(expense.date.month.value == ExpenseAdapter.monthSelected +1) {
            for(expenseType in group){
                if(expenseType.type.localId == expense.type){
                    expenseType.items.add(expense)
                }
                /*for (type in FormularyViewModel.types){
                    if(type.id == expense.type && expenseType.type.equals(type.name)){
                        expenseType.items.add(expense)
                    }
                }*/
            }
        }
        notifyDataSetChanged()
        notifyItemChanged(0)
    }
}