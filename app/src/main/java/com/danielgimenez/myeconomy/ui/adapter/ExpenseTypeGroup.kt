package com.danielgimenez.myeconomy.ui.adapter

import com.danielgimenez.myeconomy.domain.model.Expense
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

class ExpenseTypeGroup(val type: String, val items: ArrayList<Expense>): ExpandableGroup<Expense>(type, items)