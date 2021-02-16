package com.danielgimenez.myeconomy.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.danielgimenez.myeconomy.R

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        setUpView(view)

        return view
    }

    private fun setUpView(view: View){
        var expensesTypesLayout = view.findViewById<LinearLayout>(R.id.settings_expenses_type)
        expensesTypesLayout.setOnClickListener {
            startActivity(Intent(context, TypesActivity::class.java))
        }
    }
}