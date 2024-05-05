package com.example.myapplication

import android.content.pm.ActivityInfo
import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.github.kakaocup.compose.node.element.ComposeScreen.Companion.onComposeScreen
import io.github.kakaocup.compose.node.element.KNode
import junit.framework.TestCase.assertEquals
import org.apache.commons.math3.stat.StatUtils
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics
import org.junit.Rule
import org.junit.Test
import kotlin.math.abs
import kotlin.math.exp
import kotlin.math.sqrt

class MainScreenTest {

    @Rule
    @JvmField
    internal val composeTestRule = createAndroidComposeRule<MainActivity>()
    private val currentState get() = composeTestRule.activity.viewModel.state.value

    @Test
    fun checkInterface() {
        onComposeScreen<MainScreenScreen>(composeTestRule) {
            resultText {
                assertIsDisplayed()
                assertTextContains("Введите данные")
            }

            meanField {
                assertIsDisplayed()
                assertTextContains("Введите μ")
            }

            varianceField {
                assertIsDisplayed()
                assertTextContains("Введите σ^2")
            }

            calcButton {
                assertIsDisplayed()
                assertTextContains("Получить результат")
            }
        }
    }

    @Test
    fun generateWrongInput() {
        onComposeScreen<MainScreenScreen>(composeTestRule) {
            varianceField.performTextInput("1..3")
            meanField.performTextInput("1.3")
            calcButton.performClick()
            resultText.assertTextContains("Введите данные")
        }
    }

    @Test
    fun stateSavedOnDeviceRotate() {
        onComposeScreen<MainScreenScreen>(composeTestRule) {
            varianceField.performTextInput("1")
            meanField.performTextInput("0.5")
            calcButton.performClick()
            resultText.assertTextContains(currentState)
            rotateDevice(true)
            resultText.assertTextContains(currentState)
        }
    }

    @Test
    fun testGenerate() {
        onComposeScreen<MainScreenScreen>(composeTestRule) {
            val mean = 0.5
            val variance = 1.0
            varianceField.performTextInput(variance.toString())
            meanField.performTextInput(mean.toString())
            val values = ArrayList<Double>()
            for (i in 0..1500) {
                calcButton.performClick()
                values.add(currentState.toDouble())
            }
            checkLogNorm(
                a = values,
                m = exp(mean + variance / 2.0),
                v = exp(2 * mean + variance) * (exp(variance) - 1),
                sk = sqrt(exp(variance) - 1) * (exp(variance) + 2),
                kur = exp(4 * variance) + 2 * exp(3 * variance) + 3 * exp(2 * variance) - 6
            )
        }
    }

    @Throws(InterruptedException::class)
    private fun rotateDevice(landscapeMode: Boolean) {
        if (landscapeMode) {
            composeTestRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            composeTestRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    private fun checkLogNorm(a: ArrayList<Double>, m: Double, v: Double, sk: Double, kur: Double) {
        val d = a.toDoubleArray()
        val gm = StatUtils.mean(d)
        val gv = StatUtils.variance(d)
        val gskewness = DescriptiveStatistics(d).skewness
        val gkurtosis = DescriptiveStatistics(d).kurtosis
        println(
            "DistributionTest ${abs(gm - m)} ${abs(gv - v)} " +
                    "${abs(gskewness - sk)} ${abs(gkurtosis - kur)}"
        )
        assertEquals("Mean is different", gm, m, MEAN_DELTA)
        assertEquals("Variance is different", gv, v, VARIANCE_DELTA)
        assertEquals("Skewness is different", gskewness, sk, SKEWNESS_DELTA)
        assertEquals("Kurtosis is different", gkurtosis, kur, KURTOSIS_DELTA)
    }

    companion object {
        private const val MEAN_DELTA = 0.1 * 1.5
        private const val VARIANCE_DELTA = 0.8 * 1.5
        private const val SKEWNESS_DELTA = 1.1 * 1.5
        private const val KURTOSIS_DELTA = 3.1 * 1.5
    }
}

internal class MainScreenScreen(
    semanticsProvider: SemanticsNodeInteractionsProvider
): ComposeScreen<MainScreenScreen>(
    semanticsProvider = semanticsProvider,
    viewBuilderAction = { hasTestTag(MAIN_SCREEN) }
) {
    val meanField: KNode = child {
        hasTestTag(MEAN_VAL)
    }

    val varianceField: KNode = child {
        hasTestTag(VARIANCE_VALUE)
    }

    val resultText: KNode = child {
        hasTestTag(RANDOM_NUMBER_RES)
    }

    val calcButton: KNode = child {
        hasTestTag(GET_RANDOM_NUM)
    }
}