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

class ExpenseTypeFragment: Fragment() {

    private lateinit var type: Type
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
        if(arguments?.containsKey("type") == true){
            type = arguments?.getParcelable("type")!!
        }
        if(arguments?.containsKey("list") == true){
            list = arguments?.getParcelableArrayList("list")!!
        }
    }

    private fun configView(){
        if(this::type.isInitialized){
            fragment_expense_type_title.text = type.name
        }
        if(!this::list.isInitialized || list.size == 0){
            fragment_expense_type_image.visibility = View.VISIBLE
        }

        fragment_expense_type_recycler.adapter = ExpenseTypeAdapter(list, type)
        fragment_expense_type_recycler.layoutManager = LinearLayoutManager(requireContext())
    }

    companion object{
        fun newInstance(type: Type, list: ArrayList<Expense>) =
            ExpenseTypeFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("type", type)
                    putParcelableArrayList("list", list)
                }
            }
    }
}