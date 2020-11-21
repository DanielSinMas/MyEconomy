package com.danielgimenez.myeconomy.ui

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.danielgimenez.myeconomy.R
import com.danielgimenez.myeconomy.app.dagger.ApplicationComponent
import com.danielgimenez.myeconomy.app.dagger.subcomponent.formulary.FormularyFragmentModule
import com.danielgimenez.myeconomy.domain.model.Expense
import com.danielgimenez.myeconomy.ui.viewmodel.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import java.text.Format
import java.util.*
import javax.inject.Inject

class FormularyFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var formularyViewModel: FormularyViewModel
    private var dialog: AlertDialog? = null

    private var calendar: Calendar? = null

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
        val date = dialogView.findViewById<TextInputEditText>(R.id.formulary_date_text)
        val type = 0
        date.setOnClickListener {
            createDatePicker(date)
        }
        addButton.setOnClickListener{
            val expense = Expense(amount.text.toString().toFloat(), description.text.toString(), type, date.text.toString())
            formularyViewModel.insertExpense(expense)
        }
        dialog = builder.show()
    }

    private fun createDatePicker(date: TextInputEditText) {
        var listener = DatePickerDialog.OnDateSetListener{ datePicker: DatePicker, year: Int, month: Int, day: Int ->
            calendar = Calendar.getInstance()
            calendar?.set(year, month, day)
            date.setText(formatDate(calendar!!))
            date.clearFocus()
        }
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]
        context.let {
            var dialog = it?.let { it1 -> DatePickerDialog(it1, listener, year, month, day) }
            dialog?.show()
        }
    }

    private fun formatDate(calendar: Calendar): String{
        val dateFormat: Format = DateFormat.getDateFormat(context)
        return dateFormat.format(calendar.time)
    }


}