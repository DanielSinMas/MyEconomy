package com.danielgimenez.myeconomy.ui.activities

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.danielgimenez.myeconomy.R
import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.app.dagger.ApplicationComponent
import com.danielgimenez.myeconomy.app.dagger.subcomponent.formulary.FormularyFragmentModule
import com.danielgimenez.myeconomy.domain.model.Expense
import com.danielgimenez.myeconomy.domain.model.Type
import com.danielgimenez.myeconomy.ui.App
import com.danielgimenez.myeconomy.ui.adapter.ExpenseAdapter
import com.danielgimenez.myeconomy.ui.components.ExpensesComponent
import com.danielgimenez.myeconomy.ui.viewmodel.*
import com.danielgimenez.myeconomy.utils.DateFunctions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*
import javax.inject.Inject

class FormularyFragment : Fragment(), ExpenseAdapter.ChangeMonthListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var formularyViewModel: FormularyViewModel
    private var dialog: AlertDialog? = null

    private var calendar: Calendar? = null
    private var expensesComponent: ExpensesComponent? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.formylary_fragment, container, false)
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
        formularyViewModel.searchTypes()
        formularyViewModel.getExpenses()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            createDialog()
        }
        expensesComponent = view.findViewById(R.id.expense_component)
        expensesComponent?.setListener(this)
        configureButtons(view)
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
                    response.data.expenses.map {
                        expensesComponent?.addExpense(it.toExpense())
                    }
                    sendEvents(response.data.expenses.map { expense ->
                        expense.toExpense() 
                    })
                    Toast.makeText(context, "Registro insertado", Toast.LENGTH_LONG).show()
                    if(dialog?.isShowing!!) dialog?.dismiss()
                }
                is LoadingAddEntryListState -> {

                }
                is ErrorAddEntryListState -> {
                    val floating = view?.findViewById<FloatingActionButton>(R.id.fab)
                    val error = it.response as Response.Error
                    Snackbar.make(floating!!, error.exception.message!!, Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(context?.getColor(R.color.error_color)!!)
                            .setTextColor(context?.getColor(R.color.white)!!)
                            .show()
                }
            }
        }
    }

    private val getExpenseListStateObserver = Observer<GetExpenseListState>{state ->
        state.let {
            when(state){
                is SuccessGetEntryListState -> {
                    val list = state.response as Response.Success
                    expensesComponent?.setTypes(formularyViewModel.getTypes())
                    expensesComponent?.setList(list.data as ArrayList<Expense>)
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
        val adapter: ArrayAdapter<String> = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, formularyViewModel.getTypes().map { it.name })
        selector.adapter = adapter
        date.setOnClickListener {
            createDatePicker(date)
        }
        date.setOnFocusChangeListener{ _: View, b: Boolean ->
            if(b) createDatePicker(date)
        }
        addButton.setOnClickListener{
            if(validate(dialogView, amount, date)) {
                val localdate = DateFunctions.getLocalDate(date.text.toString())
                val expense = Expense(
                    amount.text.toString().toFloat(),
                    description.text.toString(),
                    formularyViewModel.getId(
                        selector.selectedItem as String
                    ),
                    localdate
                )
                formularyViewModel.insertExpense(expense)
                dialog?.dismiss()
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

    private fun configureButtons(view: View) {
        val left = view.findViewById<ImageButton>(R.id.expense_header_left)
        val right = view.findViewById<ImageButton>(R.id.expense_header_right)
        val monthTv = view.findViewById<TextView>(R.id.expense_header_month)
        val yearTv = view.findViewById<TextView>(R.id.expense_header_year)
        ExpenseAdapter.monthSelected = Calendar.getInstance()[Calendar.MONTH]
        ExpenseAdapter.year = Calendar.getInstance()[Calendar.YEAR]
        monthTv.text = ExpenseAdapter.MONTHS[ExpenseAdapter.monthSelected]
        yearTv.text = ExpenseAdapter.year.toString()
        left?.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(context, R.anim.slide_out_right)
            animation.setAnimationListener(object: Animation.AnimationListener{
                override fun onAnimationStart(animation: Animation?) {
                }

                override fun onAnimationEnd(animation: Animation?) {
                    ExpenseAdapter.changeMonth(-1)
                    setDateAndTriggerMonthChanged(monthTv, yearTv)
                }

                override fun onAnimationRepeat(animation: Animation?) {
                }
            })
            monthTv?.startAnimation(animation)
        }

        right?.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(context, R.anim.slide_out_left)
            animation.setAnimationListener(object: Animation.AnimationListener{
                override fun onAnimationStart(animation: Animation?) {
                }

                override fun onAnimationEnd(animation: Animation?) {
                    ExpenseAdapter.changeMonth(1)
                    setDateAndTriggerMonthChanged(monthTv, yearTv)
                }

                override fun onAnimationRepeat(animation: Animation?) {
                }
            })
            monthTv?.startAnimation(animation)
        }
    }

    private fun setDateAndTriggerMonthChanged(monthTv: TextView, yearTv: TextView) {
        monthTv.text = ExpenseAdapter.MONTHS[ExpenseAdapter.monthSelected]
        yearTv.text = ExpenseAdapter.year.toString()
        onMonthChanged(ExpenseAdapter.monthSelected, ExpenseAdapter.year)
    }

    private fun sendEvents(response: List<Expense>) {
        response.let {
            (context as MainActivity).sendEvent("Expense", "Factura")
        }
    }

    override fun onMonthChanged(month: Int, year: Int?) {
        Log.e("Month", "Selected: $month")
        formularyViewModel.getExpensesByDate(month+1, year!!)
    }
}