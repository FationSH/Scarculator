package com.fsharp.calculator.scarculator

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.fsharp.calculator.R
import kotlinx.android.synthetic.main.view_scarculator_output.view.*

class ScarculatorOutputView(context: Context, attributeSet: AttributeSet?) : LinearLayout(context, attributeSet), ScarculatorOutputInterfaceView {

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

}