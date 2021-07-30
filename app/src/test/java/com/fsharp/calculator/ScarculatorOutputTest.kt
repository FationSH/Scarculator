package com.fsharp.calculator

import com.fsharp.calculator.scarculator.ScarculatorOutputInterfaceView
import com.fsharp.calculator.scarculator.ScarculatorOutputPresenter
import org.junit.Test
import org.mockito.BDDMockito.then
import org.mockito.Mockito

class ScarculatorOutputTest {

    private val mmPresenter: ScarculatorOutputPresenter = ScarculatorOutputPresenter
    private val mmMockView: ScarculatorOutputInterfaceView = Mockito.mock(ScarculatorOutputInterfaceView::class.java)

    @Test
    fun `1 plus 1 is 2` () {

        // Given that the view is attached
        mmPresenter.attach(mmMockView)

        // When a number is added
        mmPresenter.add("1")

        // Then the correct equations should be set
        then(mmMockView).should().setEquation("1")

        // When an operator is added
        mmPresenter.add("+")

        // Then the correct equations should be set
        then(mmMockView).should().setEquation("1+")

        // When a number is added
        mmPresenter.add("1")

        // Then the correct equations should be set
        then(mmMockView).should().setEquation("1+1")

        // Then the correct outcome should be set
        then(mmMockView).should().setOutcome("2")

        // Clear presenter
        mmPresenter.clear()
    }
}