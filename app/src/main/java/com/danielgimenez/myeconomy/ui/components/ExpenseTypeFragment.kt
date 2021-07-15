package com.danielgimenez.myeconomy.ui.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.danielgimenez.myeconomy.R
import com.danielgimenez.myeconomy.domain.model.Expense
import com.danielgimenez.myeconomy.domain.model.Type
import com.danielgimenez.myeconomy.ui.adapter.ExpenseTypeAdapter
import kotlinx.android.synthetic.main.fragment_expense_type.*
import java.util.*
import kotlin.collections.ArrayList

class ExpenseTypeFragment: Fragment() {

    var type: Type? = null
    private lateinit var list: ArrayList<Expense>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_expense_type, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getExtras()
        configView()
    }

    private fun getExtras(){
        if(arguments?.containsKey(TYPE) == true){
            type = arguments?.getParcelable(TYPE)!!
        }
        if(arguments?.containsKey(LIST) == true){
            list = arguments?.getParcelableArrayList(LIST)!!
        }
    }

    private fun configView(){
        if(!this::list.isInitialized || list.size == 0){
            fragment_expense_type_image.visibility = View.VISIBLE
        }

        fragment_expense_type_recycler.adapter = ExpenseTypeAdapter(list, type!!)
        fragment_expense_type_recycler.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun configRecycler(){
        fragment_expense_type_recycler.adapter = ExpenseTypeAdapter(list, type!!)
        fragment_expense_type_recycler.layoutManager = LinearLayoutManager(requireContext())
    }

    fun addExpense(expense: Expense){
        list.add(expense)
        configRecycler()
        fragment_expense_type_image.visibility = View.GONE
    }

    companion object{

        const val TYPE = "type"
        const val LIST = "list"

        fun newInstance(type: Type, list: ArrayList<Expense>) =
            ExpenseTypeFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(TYPE, type)
                    putParcelableArrayList(LIST, list)
                }
            }
    }
}