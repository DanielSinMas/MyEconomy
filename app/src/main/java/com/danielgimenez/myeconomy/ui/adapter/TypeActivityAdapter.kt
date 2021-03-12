package com.danielgimenez.myeconomy.ui.adapter

import android.app.Service
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.danielgimenez.myeconomy.R
import com.danielgimenez.myeconomy.domain.model.Type
import com.danielgimenez.myeconomy.utils.Rotate3dAnimation


class TypeActivityAdapter(var context: Context, private val types: ArrayList<Type>): RecyclerView.Adapter<TypeActivityAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val typeText: TextView = view.findViewById(R.id.type_item_text)
        val editType: ImageView = view.findViewById(R.id.type_item_edit)
        val saveType: ImageView = view.findViewById(R.id.type_item_save)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.type_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.typeText.text = types[position].name
        holder.editType.setOnClickListener{
            holder.typeText.isFocusableInTouchMode = true
            holder.typeText.setSelectAllOnFocus(true)
            holder.typeText.focusable = TextView.FOCUSABLE

            holder.typeText.requestFocus()
            showKeyboard(holder.typeText)

            animateImages(holder.editType, holder.saveType)
        }

        holder.saveType.setOnClickListener {
            if(holder.typeText.text.toString().isNotEmpty()) {
                holder.typeText.focusable = TextView.NOT_FOCUSABLE
                holder.typeText.clearFocus()
                holder.typeText.isFocusableInTouchMode = false
                holder.typeText.clearFocus()
                hideKeyboard(holder.typeText)
                types[position].name = holder.typeText.text.toString()
                animateImages(holder.saveType, holder.editType)
            }
        }
    }

    private fun animateImages(viewToHide: ImageView, viewToShow: ImageView){
        val editTypeAnimation = Rotate3dAnimation(0f, 0f, 0f, 90f, 0f, 0f)
        editTypeAnimation.duration = 100
        editTypeAnimation.setAnimationListener(object: Animation.AnimationListener{
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                viewToShow.visibility = View.VISIBLE
                viewToHide.visibility = View.GONE
                val zAxisAnimation = Rotate3dAnimation(0f, 0f, 90f, 0f, 0f, 0f)
                zAxisAnimation.duration = 100
                viewToShow.startAnimation(zAxisAnimation)
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
        viewToHide.startAnimation(editTypeAnimation)
    }

    private fun showKeyboard(view: View){
        val imm = context.getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun hideKeyboard(view: View){
        val imm = context.getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun getItemCount() = types.size

    fun getTypes() = types
}