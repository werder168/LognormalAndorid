package com.example.myapplication.logic

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.util.Locale
import kotlin.math.sqrt

class MainViewModel(
    private val calculator: RandomFromLognormal
): ViewModel() {
    val state: MutableState<String> = mutableStateOf(DEFAULT_VALUE)

    fun onClick(mean: String, variance: String) {
        val meanD: Double? = mean.toDoubleOrNull()
        val varianceD: Double? = variance.toDoubleOrNull()
        state.value = if (meanD == null || varianceD == null) {
            DEFAULT_VALUE
        } else {
            "%.${4}f".format(
                locale =  Locale.US,
                calculator.lognormal(meanD, sqrt(varianceD))
            )
        }
    }

    companion object {
        const val DEFAULT_VALUE = "Введите данные"
    }
}