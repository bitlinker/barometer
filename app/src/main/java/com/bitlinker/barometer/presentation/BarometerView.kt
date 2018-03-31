package com.bitlinker.barometer.presentation

/**
 * Main barometer view interface
 *
 * Created by bitlinker on 31.03.2018.
 */
interface BarometerView {
    fun setPressure(value : Float, isAnimated: Boolean)
    fun setStoredPressure(value : Float, isAnimated: Boolean)
    fun showInitError()
}