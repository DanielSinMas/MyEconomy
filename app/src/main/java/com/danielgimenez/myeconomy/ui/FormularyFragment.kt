package com.danielgimenez.myeconomy.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.danielgimenez.myeconomy.R
import com.danielgimenez.myeconomy.app.dagger.ApplicationComponent
import com.danielgimenez.myeconomy.app.dagger.subcomponent.formulary.FormularyFragmentModule
import com.danielgimenez.myeconomy.domain.model.Expense
import com.danielgimenez.myeconomy.ui.viewmodel.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import javax.inject.Inject

class FormularyFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var formularyViewModel: FormularyViewModel
    private var dialog: AlertDialog? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpInjection(App.graph)
    }

    private fun setUpInjection(applicationComponent: ApplicationComponent){
        setHasOptionsMenu(true)
        applicationComponent.plus(FormularyFragmentModule(this)).injectTo(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        prepareViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            createDialog()
        }

        view.findViewById<Button>(R.id.button_first).setOnClickListener {

        }
    }

    private fun prepareViewModel(){
        formularyViewModel = ViewModelProvider(this, viewModelFactory).get(FormularyViewModel::class.java)
        formularyViewModel.addExpenseListLiveData.observe(viewLifecycleOwner, addExpenseListStateObserver)
    }

    private val addExpenseListStateObserver = Observer<AddExpenseListState>{ state ->
        state?.let {
            when(state){
                is SuccessAddEntryListState -> {
                    Toast.makeText(context, "Registro insertado", Toast.LENGTH_LONG).show()
                    if(dialog?.isShowing!!) dialog?.dismiss()
                }
                is LoadingAddEntryListState -> {

                }
                is ErrorAddEntryListState -> {

                }
            }
        }
    }

    private fun createDialog(){
        val dialogView = LayoutInflater.from(context).inflate(R.layout.formularydialog_layout, null)
        val builder = AlertDialog.Builder(context).setView(dialogView)
        val selector = dialogView.findViewById<Spinner>(R.id.formulary_type_selector)
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                    R.array.types,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                selector.adapter = adapter
            }
        }
        val addButton = dialogView.findViewById<Button>(R.id.formulary_add_button)
        val amount = dialogView.findViewById<TextInputEditText>(R.id.formulary_amount_text)
        val description = dialogView.findViewById<TextInputEditText>(R.id.formulary_description_text)
        val date = "20/11/2020"
        val type = 0
        addButton.setOnClickListener{
            val expense = Expense(amount.text.toString().toFloat(), description.text.toString(), type, date)
            formularyViewModel.insertExpense(expense)
        }
        dialog = builder.show()
    }
}