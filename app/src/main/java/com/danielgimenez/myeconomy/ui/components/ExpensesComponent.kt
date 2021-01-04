package com.danielgimenez.myeconomy.ui.components

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.danielgimenez.myeconomy.R

class ExpensesComponent(context: Context) : LinearLayout(context) {

    init{
        LayoutInflater.from(context).inflate(R.layout.expenses_component_layout, this, true)
        orientation = VERTICAL
    }
}