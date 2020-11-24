package com.danielgimenez.myeconomy.ui

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.danielgimenez.myeconomy.R
import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.app.dagger.ApplicationComponent
import com.danielgimenez.myeconomy.app.dagger.subcomponent.formulary.FormularyFragmentModule
import com.danielgimenez.myeconomy.domain.model.Expense
import com.danielgimenez.myeconomy.ui.adapter.ExpenseAdapter
import com.danielgimenez.myeconomy.ui.viewmodel.*
import com.danielgimenez.myeconomy.utils.DateFunctions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class FormularyFragment : Fragment(), ExpenseAdapter.ChangeMonthListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var formularyViewModel: FormularyViewModel
    private var dialog: AlertDialog? = null

    private var calendar: Calendar? = null
    private var recycler : RecyclerView? = null
    private var expenseAdapter: ExpenseAdapter? = null

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
        formularyViewModel.seachTypes()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            createDialog()
        }
        recycler = view.findViewById(R.id.expense_recycler)
        expenseAdapter = ExpenseAdapter(ArrayList(), ArrayList(), this)
        recycler?.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = expenseAdapter
        }
    }

    private fun prepareViewModel(){
        formularyViewModel = ViewModelProvider(this, viewModelFactory).get(FormularyViewModel::class.java)
        formularyViewModel.addExpenseListLiveData.observe(viewLifecycleOwner, addExpenseListStateObserver)
        formularyViewModel.getExpenseListLiveData.observe(viewLifecycleOwner, getExpenseListStateObserver)
    }

    private val addExpenseListStateObserver = Observer<AddExpenseListState>{ state ->
        state?.let {
            when(state){
                is SuccessAddEntryListState -> {
                    val response = it.response as Response.Success
                    (recycler?.adapter as ExpenseAdapter).addExpense(response.data)
                    (recycler?.adapter as ExpenseAdapter).notifyDataSetChanged()
                    (recycler?.adapter as ExpenseAdapter).notifyItemChanged(0)
                    sendEvents(response)
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

    private val getExpenseListStateObserver = Observer<GetExpenseListState>{state ->
        state.let {
            when(state){
                is SuccessGetEntryListState -> {
                    val list = state.response as Response.Success
                    expenseAdapter?.setList(list.data as ArrayList<Expense>)
                    expenseAdapter?.notifyDataSetChanged()
                    expenseAdapter?.setTypes(formularyViewModel.getTypes())
                }
                is LoadingGetEntryListState -> {

                }
                is ErrorGetEntryListState -> {

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
                android.R.layout.simple_spinner_dropdown_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                selector.adapter = adapter
            }
        }
        val addButton = dialogView.findViewById<Button>(R.id.formulary_add_button)
        val amount = dialogView.findViewById<TextInputEditText>(R.id.formulary_amount_text)
        val description = dialogView.findViewById<TextInputEditText>(R.id.formulary_description_text)
        val date = dialogView.findViewById<TextInputEditText>(R.id.formulary_date_text)
        date.setText(DateFunctions.formatDate(Calendar.getInstance(), requireContext()))
        val adapter: ArrayAdapter<String> = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, formularyViewModel.getTypes())
        selector.adapter = adapter
        date.setOnClickListener {
            createDatePicker(date)
        }
        addButton.setOnClickListener{
            if(validate(dialogView, amount, date)) {
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yy")
                val localdate = LocalDate.parse(date.text.toString(), formatter)
                val expense = Expense(
                    amount.text.toString().toFloat(),
                    description.text.toString(),
                    formularyViewModel.getId(
                        selector.selectedItem as String
                    ),
                    localdate
                )
                formularyViewModel.insertExpense(expense)
            }
        }
        dialog = builder.show()
    }

    private fun validate(dialogView: View, amount: TextInputEditText, date: TextInputEditText): Boolean{
        var isValid = true
        if(amount.text.toString().isEmpty()){
            isValid = false
            dialogView.findViewById<ImageView>(R.id.formulary_amount_error).visibility = View.VISIBLE
        }
        if(date.text.toString().isEmpty()){
            isValid = false
            dialogView.findViewById<ImageView>(R.id.formulary_date_error).visibility = View.VISIBLE
        }
        return isValid
    }

    private fun createDatePicker(date: TextInputEditText) {
        val listener = DatePickerDialog.OnDateSetListener{ _: DatePicker, year: Int, month: Int, day: Int ->
            calendar = Calendar.getInstance()
            calendar?.set(year, month, day)
            date.setText(DateFunctions.formatDate(calendar!!, requireContext()))
            date.clearFocus()
        }
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]
        context.let {
            val dialog = it?.let { it1 -> DatePickerDialog(it1, listener, year, month, day) }
            dialog?.show()
        }
    }

    private fun sendEvents(response: Response.Success<Expense>) {
        response.data.let {
            (context as MainActivity).sendEvent("Expense", "Factura")
        }
    }

    override fun onMonthChanged(month: Int, year: Int?) {
        Log.e("Month", "Selected: $month")
        formularyViewModel.getExpensesByMonth(month)
    }
}