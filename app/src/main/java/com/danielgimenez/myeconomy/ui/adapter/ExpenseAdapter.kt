package com.danielgimenez.myeconomy.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.danielgimenez.myeconomy.R
import com.danielgimenez.myeconomy.domain.model.Expense
import java.util.*
import kotlin.collections.ArrayList

class ExpenseAdapter(private var list: ArrayList<Expense>, private var types: List<String>, private var changeMonthListener: ExpenseAdapter.ChangeMonthListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var TYPE_HEADER = 0
    private var TYPE_VIEW = 1

    class ExpenseViewHolder(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder(inflater.inflate(
        R.layout.expense_recycler_item, parent, false)){
        private var amount: TextView? = null
        private var date: TextView? = null
        private var type: TextView? = null
        private var layout: LinearLayout? = null

        init {
            amount = itemView.findViewById(R.id.expense_item_amount)
            date = itemView.findViewById(R.id.expense_item_date)
            type = itemView.findViewById(R.id.expense_item_type)
            layout = itemView.findViewById(R.id.expense_item_layout)
        }

        fun bind(expense: Expense, position: Int, types: List<String>){
            amount?.text = expense.amount.toString().plus("€")
            date?.text = expense.date.toString()
            type?.text = types.get(expense.type-1)

            if(position % 2 == 1){
                layout?.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.recyclerBackgroundColor))
            }
        }
    }

    class ExpenseHeaderVieHolder(inflater: LayoutInflater, var parent: ViewGroup, list: ArrayList<Expense>, private val changeMonthListener: ChangeMonthListener): RecyclerView.ViewHolder(inflater.inflate(R.layout.expense_recycler_header, parent, false)){
        private var amount: TextView? = null
        private var monthTv: TextView? = null
        private var list: ArrayList<Expense>? = null
        private var left: ImageView? = null
        private var right: ImageView? = null
        private var monthSelected: Int? = null

        init {
            amount = itemView.findViewById(R.id.expense_header_total)
            monthTv = itemView.findViewById(R.id.expense_header_month)
            left = itemView.findViewById(R.id.expense_header_left)
            right = itemView.findViewById(R.id.expense_header_right)
            monthSelected = Calendar.getInstance()[Calendar.MONTH]
            changeMonthListener.onMonthChanged(monthSelected!!+1)
            this.list = list
        }

        fun bind(){
            var total = 0.0
            for(expense in list!!){
                Log.e("Amount", expense.amount.toString())
                total += expense.amount
                Log.e("Total", total.toString())
            }
            amount?.text = "Total: ".plus(total.toString()).plus("€")

            left?.setOnClickListener {
                val animation = AnimationUtils.loadAnimation(parent.context, android.R.anim.slide_out_right)
                animation.setAnimationListener(object: Animation.AnimationListener{
                    override fun onAnimationStart(animation: Animation?) {
                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        monthTv?.text = "October"
                        monthSelected = monthSelected?.minus(1)
                        changeMonthListener.onMonthChanged(monthSelected!!)
                    }

                    override fun onAnimationRepeat(animation: Animation?) {
                    }
                })
                monthTv?.startAnimation(animation)
            }

            right?.setOnClickListener {
                val animation = AnimationUtils.loadAnimation(parent.context, R.anim.slide_out_left)
                animation.setAnimationListener(object: Animation.AnimationListener{
                    override fun onAnimationStart(animation: Animation?) {
                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        monthTv?.text = "October"
                        monthSelected = monthSelected?.plus(1)
                        changeMonthListener.onMonthChanged(monthSelected!!)
                    }

                    override fun onAnimationRepeat(animation: Animation?) {
                    }
                })
                monthTv?.startAnimation(animation)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == TYPE_VIEW) {
            val inflater = LayoutInflater.from(parent.context)
            return ExpenseViewHolder(inflater, parent)
        }
        else{
            val inflater = LayoutInflater.from(parent.context)
            return ExpenseHeaderVieHolder(inflater, parent, list, changeMonthListener)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ExpenseViewHolder) {
            val expense = list[position-1]
            holder.bind(expense, position-1, types)
        }
        if(holder is ExpenseHeaderVieHolder){
            holder.bind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        if(position==0) return TYPE_HEADER
        else return TYPE_VIEW
    }

    override fun getItemCount() = list.size+1

    fun setList(list: ArrayList<Expense>){
        this.list = list
    }

    fun addExpense(expense: Expense){
        list.add(expense)
    }

    fun setTypes(list: List<String>) {
        this.types = list
    }

    interface ChangeMonthListener{
        fun onMonthChanged(month: Int, year: Int? = Calendar.getInstance()[Calendar.YEAR])
    }
}