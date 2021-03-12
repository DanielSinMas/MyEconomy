package com.danielgimenez.myeconomy.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.danielgimenez.myeconomy.R
import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.app.dagger.ApplicationComponent
import com.danielgimenez.myeconomy.app.dagger.subcomponent.formulary.FormularyFragmentModule
import com.danielgimenez.myeconomy.app.dagger.subcomponent.types.TypesActivityModule
import com.danielgimenez.myeconomy.domain.model.Type
import com.danielgimenez.myeconomy.ui.App
import com.danielgimenez.myeconomy.ui.adapter.TypeActivityAdapter
import com.danielgimenez.myeconomy.ui.viewmodel.AddExpenseListState
import com.danielgimenez.myeconomy.ui.viewmodel.FormularyViewModel
import com.danielgimenez.myeconomy.ui.viewmodel.SuccessGetEntryListState
import com.danielgimenez.myeconomy.ui.viewmodel.TypesViewModel
import com.danielgimenez.myeconomy.ui.viewmodel.states.ErrorGetTypesState
import com.danielgimenez.myeconomy.ui.viewmodel.states.GetTypesState
import com.danielgimenez.myeconomy.ui.viewmodel.states.LoadingGetTypesState
import com.danielgimenez.myeconomy.ui.viewmodel.states.SuccessGetTypesState
import javax.inject.Inject

class TypesActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var typesviewModel: TypesViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_types)
        setUpInjection(App.graph)
        setUpObservers()
        typesviewModel.getTypes()
    }

    private fun setUpInjection(applicationComponent: ApplicationComponent){
        applicationComponent.plus(TypesActivityModule(this)).injectTo(this)
    }

    private fun setUpObservers(){
        typesviewModel = ViewModelProvider(this, viewModelFactory).get(TypesViewModel::class.java)
        typesviewModel.typesLiveData.observe(this, getTypesStateObserver)

        saveButton = findViewById(R.id.activity_types_save)
        saveButton.setOnClickListener {
            val types = (recyclerView.adapter as TypeActivityAdapter).getTypes()
        }
    }

    private val getTypesStateObserver = Observer<GetTypesState> { state ->
        state.let {
            when(state){
                is SuccessGetTypesState -> {
                    val types = (it.response as Response.Success).data
                    val adapter = TypeActivityAdapter(this, types as ArrayList<Type>)
                    recyclerView = findViewById(R.id.types_recycler)
                    recyclerView.layoutManager = LinearLayoutManager(this)
                    recyclerView.adapter = adapter
                }
                is LoadingGetTypesState -> {

                }
                is ErrorGetTypesState -> {

                }
            }
        }
    }
}