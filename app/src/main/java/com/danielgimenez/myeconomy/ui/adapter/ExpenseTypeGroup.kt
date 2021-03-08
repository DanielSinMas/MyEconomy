package com.danielgimenez.myeconomy.ui.adapter

import com.danielgimenez.myeconomy.domain.model.Expense
import com.danielgimenez.myeconomy.domain.model.Type
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

class ExpenseTypeGroup(val type: Type, val items: ArrayList<Expense>): ExpandableGroup<Expense>(type.name, items)