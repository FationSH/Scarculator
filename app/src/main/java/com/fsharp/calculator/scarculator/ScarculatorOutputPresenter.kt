package com.fsharp.calculator.scarculator

import bsh.Interpreter
import java.lang.Exception

object ScarculatorOutputPresenter {

    private var mmView : ScarculatorOutputInterfaceView? = null

    // Current equation
    private var mmCurrentEquation: String = ""

    // Current outcome result
    private var mmCurrentOutcome: String = ""

    // Interpreter
    private val mmInterpreter = Interpreter()

    // current attached view
    fun attach(view: ScarculatorOutputInterfaceView) {
        mmView = view
        updateEquation()
        updateOutcome()
    }

    fun detach(){
        mmView = null
    }

    fun add(item: String) {
        mmCurrentEquation = if (mmCurrentEquation.isEmpty()) {
            if (item == "/" || item == "*" || item == "+" || item == "%") {  // allow - as first character
                mmCurrentEquation
            } else if (item == ".") {
                mmCurrentEquation.plus("0$item")
            } else {
                mmCurrentEquation.plus(item)
            }
        } else {
            addTheItem(item)
        }

        updateEquation()
        calculateOutcome()
        updateOutcome()
    }

    private fun addTheItem(item: String): String {
        return if (mmCurrentEquation == "0") {
            if (item == "/" || item == "*" || item == "-" || item == "+" || item == "%") {
                mmCurrentEquation.plus(item)
            } else {
                item
            }
        } else {
            if (item == "." && mmCurrentEquation.contains(".")) {
                val arr = mmCurrentEquation.split("-","+","%","/","*")
                if (arr[arr.size-1].contains(".")) {
                    mmCurrentEquation
                } else {
                    checkLast(item)
                }
            } else {
                checkLast(item)
            }
        }
    }

    private fun checkLast(item: String): String {
        val last: String = mmCurrentEquation.substring(
            mmCurrentEquation.length - 1,
            mmCurrentEquation.length
        )
        return if (last == "/" || last == "*" || last == "-" || last == "+" || last == "%") {
            if (item == "/" || item == "*" || item == "-" || item == "+" || item == "%" || item == "0" || item == ".") {
                mmCurrentEquation
            } else {
                mmCurrentEquation.plus(item)
            }
        } else {
            mmCurrentEquation.plus(item)
        }
    }

    fun remove() {
        mmCurrentEquation = if (mmCurrentEquation.length > 1) {
            mmCurrentEquation.substring(0, mmCurrentEquation.length - 1)
        } else {
            ""
        }

        updateEquation()
        calculateOutcome()
        updateOutcome()
    }

    fun solve() {
        if (mmCurrentOutcome.isNotEmpty()) {
            mmCurrentEquation = mmCurrentOutcome
            mmCurrentOutcome = ""
        }
        updateEquation()
        updateOutcome()
    }

    fun clear() {
        mmCurrentEquation = ""
        mmCurrentOutcome = ""
        updateEquation()
        updateOutcome()
    }

    private fun calculateOutcome() {
        if (mmCurrentEquation.isNotEmpty()) {
            try {
                mmInterpreter.eval("result = $mmCurrentEquation")
                val result = mmInterpreter.get("result")

                if (result != null && result is Int) {
                    mmCurrentOutcome = result.toString()
                }
            } catch (e: Exception) {
                mmCurrentOutcome = ""
            }
        } else {
            mmCurrentOutcome = ""
        }
    }

    private fun updateEquation() {
        mmView?.setEquation(mmCurrentEquation)
    }

    private fun updateOutcome() {
        mmView?.setOutcome(mmCurrentOutcome)
    }

}