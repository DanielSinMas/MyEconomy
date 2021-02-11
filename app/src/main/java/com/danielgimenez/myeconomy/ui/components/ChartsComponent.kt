package com.danielgimenez.myeconomy.ui.components

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.LinearLayout
import com.danielgimenez.myeconomy.R

class ChartsComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    private var barArray: ArrayList<LinearLayout>
    private var view: View =
        LayoutInflater.from(context).inflate(R.layout.charts_component_layout, this, true)

    init {
        var button = view.findViewById<Button>(R.id.button)
        button.setOnClickListener {
            loadCharts()
        }
        barArray = ArrayList()
    }

    private fun loadCharts(){
        barArray.clear()
        var container = view.findViewById<LinearLayout>(R.id.charts_container)
        container.removeAllViews()
        repeat(3){
            var chartBar = getChartBar(container)
            barArray.add(chartBar?.findViewById(R.id.layout_to_animate)!!)
            container.addView(chartBar)
        }
        barArray.map { slideView(0, (50..250).random().dp, it) }
    }

    private fun getChartBar(container: ViewGroup): View? {
        return LayoutInflater.from(context).inflate(R.layout.chart_bar_layout, null, true)
    }

    fun slideView(currentWidth: Int, newWidth: Int, view: View){
        var slideAnimator = ValueAnimator.ofInt(currentWidth, newWidth).setDuration(500);
        slideAnimator.addUpdateListener {
            var value = it.getAnimatedValue() as Int
            view.getLayoutParams().width = value
            view.requestLayout();
        }

        var animationSet = AnimatorSet()
        animationSet.setInterpolator(AccelerateDecelerateInterpolator());
        animationSet.play(slideAnimator);
        animationSet.start()
    }
}

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()