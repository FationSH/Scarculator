package com.fsharp.calculator.scarculator

import android.content.Context
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import com.fsharp.calculator.R
import kotlinx.android.synthetic.main.view_scarculator_output.view.*

import android.content.Intent
import android.net.Uri
import android.widget.TextView

class ScarculatorOutputView(context: Context, attributeSet: AttributeSet?) : LinearLayout(context, attributeSet), ScarculatorOutputInterfaceView {

    private val myUrl = "https://fationsh.github.io/myPort/"

    init {

        // Set orientation
        orientation = VERTICAL

        // Set gravity
        gravity = Gravity.CENTER_VERTICAL

        LayoutInflater.from(context).inflate(R.layout.view_scarculator_output, this, true)

    }

    fun addItem(item: String) {
        ScarculatorOutputPresenter.add(item)
    }

    fun removeItem() {
        ScarculatorOutputPresenter.remove()
    }

    fun solve() {
        ScarculatorOutputPresenter.solve()
    }

    fun clear() {
        ScarculatorOutputPresenter.clear()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        ScarculatorOutputPresenter.attach(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        ScarculatorOutputPresenter.detach()
    }

    override fun setEquation(equation: String) {
        calculator_input_equation.text = equation
    }

    override fun setOutcome(outcome: String) {
        calculator_input_outcome.text = outcome
    }

    fun popupInfoView() {
        // Initialize a new layout inflater instance
        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // Inflate a custom view using layout inflater
        val view = inflater.inflate(R.layout.view_about, null)

        // Find text link and open if clicked
        val linkTxt = view.findViewById<TextView>(R.id.link_to_myport)
        linkTxt.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(myUrl))
            context.startActivity(browserIntent)
        }

        // Initialize a new instance of popup window
        val popupWindow = PopupWindow(
            view, // Custom view to show in popup window
            LayoutParams.WRAP_CONTENT, // Width of popup window
            LayoutParams.WRAP_CONTENT // Window height
        )

        // Set an elevation for the popup window
        popupWindow.elevation = 10.0F

        popupWindow.isOutsideTouchable = true
        popupWindow.isFocusable = true

        // Finally, show the popup window on app
        TransitionManager.beginDelayedTransition(rootView as ViewGroup?)
        popupWindow.showAtLocation(
            rootView, // Location to display popup window
            Gravity.CENTER, // Exact position of layout to display popup
            0, // X offset
            0  // Y offset
        )
    }

}