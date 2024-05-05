package com.example.myapplication.logic

import com.example.myapplication.logic.MainViewModel.Companion.DEFAULT_VALUE
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class MainViewModelTest {

    private val calculator = mockk<RandomFromLognormal>()
    private val viewModel = MainViewModel(calculator)

    @Before
    fun setUp() {
        every { calculator.lognormal(any(), any()) } returns 1.5
    }

    @Test
    fun `invalid input`() {
        viewModel.onClick("labuda", "var")

        val actual = viewModel.state.value
        assertEquals(DEFAULT_VALUE, actual)
    }

    @Test
    fun `invalid double for cast`() {
        viewModel.onClick("1.3.4", "1.34")

        val actual = viewModel.state.value
        assertEquals(DEFAULT_VALUE, actual)
    }

    @Test
    fun `valid input`() {
        viewModel.onClick("1.3", "13.54")

        val actual = viewModel.state.value
        assertEquals("1.5000", actual)
    }
}