package com.bitlinker.barometer.presentation

/**
 * Created by bitlinker on 31.03.2018.
 */
public interface BarometerView {
    fun setPressure(value : Float, isAnimated: Boolean)
    fun setStoredPressure(value : Float, isAnimated: Boolean)
    fun showInitError()
}