package com.danielgimenez.myeconomy.ui.adapter.viewholders

import android.view.View
import android.view.animation.Animation.RELATIVE_TO_SELF
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import com.danielgimenez.myeconomy.R
import com.danielgimenez.myeconomy.domain.model.Expense
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder


class TypeViewHolder(view: View): GroupViewHolder(view) {
    private var type: TextView = view.findViewById(R.id.textView)
    private var arrow: ImageView = view.findViewById(R.id.type_expense_image)
    private var total: TextView = view.findViewById(R.id.total)

    fun setType(typeGroup: ExpandableGroup<Expense>){
        type.text = typeGroup.title
        var sum = 0f
        typeGroup.items.map { sum += it.amount }
        if(sum % 1.0 != 0.0) total.text = sum.toString().plus("€")
        else total.text = sum.toInt().toString().plus("€")
    }

    override fun expand() {
        animateExpand()
    }

    override fun collapse() {
        animateCollapse()
    }

    private fun animateExpand() {
        val rotate = RotateAnimation(360F, 180F, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f)
        rotate.duration = 300
        rotate.fillAfter = true
        arrow.animation = rotate
    }

    private fun animateCollapse() {
        val rotate = RotateAnimation(180F, 360F, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f)
        rotate.duration = 300
        rotate.fillAfter = true
        arrow.animation = rotate
    }


}