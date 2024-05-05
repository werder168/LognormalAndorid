package com.example.myapplication.logic

import io.mockk.every
import io.mockk.mockkConstructor
import io.mockk.unmockkAll
import org.apache.commons.math3.distribution.LogNormalDistribution
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class RandomFromLognormalTest {

    private val randomFromLognormal = RandomFromLognormal()
    private val expected = 4.48168907

    @Before
    fun setUp() {
        mockkConstructor(LogNormalDistribution::class)
        every { anyConstructed<LogNormalDistribution>().sample() } returns expected
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun testLognormal() {
        val actual = randomFromLognormal.lognormal(1.5, 0.5)

        assertEquals(expected, actual, 0.00001)
    }
}