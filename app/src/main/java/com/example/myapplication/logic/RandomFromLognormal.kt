package com.example.myapplication.logic

import org.apache.commons.math3.distribution.LogNormalDistribution
import kotlin.math.cos
import kotlin.math.ln
import kotlin.math.sqrt
import kotlin.random.Random

class RandomFromLognormal {

    /**
     * По определению x принадлежит lognorm, если log(x) принадлежит нормальному
     * Как получаем: берем число из нормального и берем от него экспоненту
     *
     * e^x - принадлежит логнормальному,
     * тогда log(e^x) = x принадлежит нормальному
     *
     *
     * @param mean среднее
     * @param std стандартное отклонение
     */
    fun lognormal(mean: Double, std: Double): Double =
        LogNormalDistribution(mean, std).sample()

//    fun lognormal(mean: Double, std: Double): Double =
//        exp(mean + java.util.Random().nextGaussian() * std)

//    fun lognormal(mean: Double, std: Double): Double {
//        val normal = generateRandomNormal(mean, std)
//        return exp(mean + normal * std)
//    }
//
    /** Метод Бокса-Мюллера
     *
     * @param mean μ
     * @param std σ
     */
    private fun generateRandomNormal(mean: Double, std: Double): Double {
        val u1 = Random.nextDouble()
        val u2 = Random.nextDouble()
        val z = sqrt(-2 * ln(u1)) * cos(2 * Math.PI * u2)
        return mean + z * std
    }
}