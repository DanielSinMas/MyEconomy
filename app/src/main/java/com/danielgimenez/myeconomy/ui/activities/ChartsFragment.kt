package com.danielgimenez.myeconomy.ui.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.danielgimenez.myeconomy.R
import com.danielgimenez.myeconomy.app.dagger.ApplicationComponent
import com.danielgimenez.myeconomy.app.dagger.subcomponent.formulary.charts.ChartsFragmentModule
import com.danielgimenez.myeconomy.ui.App
import com.danielgimenez.myeconomy.ui.components.ChartsComponent
import com.danielgimenez.myeconomy.ui.viewmodel.*
import javax.inject.Inject


class ChartsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var chartsViewModel: ChartsViewModel
    private lateinit var chartsComponent: ChartsComponent

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.charts_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpInjection(App.graph)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        prepareViewModel()
        chartsViewModel.getCharts("", "")
    }

    private fun prepareViewModel(){
        chartsViewModel = ViewModelProvider(this, viewModelFactory).get(ChartsViewModel::class.java)
        chartsViewModel.getChartsLiveData.observe(viewLifecycleOwner, getChartsObserver)
    }

    private val getChartsObserver = Observer<GetChartsListState>{ state ->
        state.let {
            when(state){
                is SuccessGetChartsListState -> {
                    chartsComponent = view?.findViewById(R.id.charts_component)!!
                    //chartsComponent.loadCharts()
                }
                is LoadingGetChartsListState -> {

                }
                is ErrorGetChartsListState -> {

                }
            }
        }
    }

    private fun setUpInjection(applicationComponent: ApplicationComponent){
        setHasOptionsMenu(true)
        applicationComponent.plus(ChartsFragmentModule(this)).injectTo(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}