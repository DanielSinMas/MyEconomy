package com.danielgimenez.myeconomy.ui.components

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.danielgimenez.myeconomy.R
import com.danielgimenez.myeconomy.domain.model.Expense
import com.danielgimenez.myeconomy.domain.model.Type
import kotlinx.android.synthetic.main.new_expenses_component_layout.view.*

class NewExpensesComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    private var types: List<Type>? = ArrayList()
    lateinit var mViewPagerAdapter: ViewPagerAdapter
    private lateinit var fragments : ArrayList<ExpenseTypeFragment>

    init{
        val view = LayoutInflater.from(context).inflate(R.layout.new_expenses_component_layout, this, true)
        orientation = VERTICAL
    }

    fun setTypes(types: List<Type>){
        this.types = types
    }

    fun setList(list: ArrayList<Expense>){
        fragments = ArrayList()
        types?.map { type ->
            fragments.add(ExpenseTypeFragment.newInstance(type, list.filter { it.type == type.localId } as ArrayList<Expense>))
        }
        configViewPager(fragments)
    }

    private fun configViewPager(fragmentList: List<Fragment>) {
        mViewPagerAdapter = ViewPagerAdapter((context as AppCompatActivity).supportFragmentManager, fragmentList)
        new_expense_component_viewpager.adapter = mViewPagerAdapter
        new_expense_component_viewpager.isEnabled = false
    }

    fun addExpense(expense: Expense) {
        fragments.map { fragment ->
            if(fragment.type.localId == expense.type) fragment.addExpense(expense)
        }
    }

    inner class ViewPagerAdapter(fm: FragmentManager, var list: List<Fragment>) : FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int = list.size

        override fun getItem(position: Int): Fragment {
            return list[position]
        }
    }
}