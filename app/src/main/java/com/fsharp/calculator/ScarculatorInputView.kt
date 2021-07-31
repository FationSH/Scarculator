package com.fsharp.calculator

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.view_scarculator_input.view.*

class ScarculatorInputView(context: Context, attributeSet: AttributeSet?) : RelativeLayout(context, attributeSet) {

    init {
        // Inflate Layout
        LayoutInflater.from(context).inflate(R.layout.view_scarculator_input, this, true)

        // Read attribute set
        attributeSet?.run {
            val typedArray: TypedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ScarculatorInputView)

            val textResource: String? = typedArray.getString(R.styleable.ScarculatorInputView_item_text)
            val iconResource: Int = typedArray.getResourceId(R.styleable.ScarculatorInputView_item_icon, -1)

            when {
                iconResource != -1 -> {
                    input_element_text.visibility = View.GONE
                    input_element_image.apply {
                        visibility = View.VISIBLE
                        setImageResource(iconResource)
                    }
                }

                !textResource.isNullOrEmpty() -> {
                    input_element_image.visibility = View.GONE
                    input_element_text.apply {
                        visibility = View.VISIBLE
                        text = textResource
                    }
                }

                else -> {
                    input_element_image.visibility = View.GONE
                    input_element_text.visibility = View.GONE
                }
            }

            typedArray.recycle()

        }
    }

    override fun setOnClickListener(l: OnClickListener?) {
        input_element_click.setOnClickListener(l)
    }

}