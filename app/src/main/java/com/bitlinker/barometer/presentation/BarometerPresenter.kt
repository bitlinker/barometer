package com.bitlinker.barometer.presentation

import com.bitlinker.barometer.BarometerApp
import com.bitlinker.barometer.repo.BarometerRepo

/**
 * Main barometer view presenter
 *
 * Created by bitlinker on 31.03.2018.
 */
class BarometerPresenter(private var view: BarometerView?) {
    private val repo = BarometerApp.barometerComponent.barometerRepo

    fun resume() {
        val started = repo.start(object : BarometerRepo.BarometerRepoListener {
            override fun onPressureChanged(value: Float, isAnimated: Boolean) {
                view?.setPressure(value, isAnimated)
            }

            override fun onStoredPressureChanged(value: Float, isAnimated: Boolean) {
                view?.setStoredPressure(value, isAnimated)
            }
        })
        if (!started) {
            view?.showInitError()
        }
    }

    fun pause() {
        repo.stop()
    }

    fun onManualArrowPress() {
        repo.setManualPressure()
    }

    fun removeView() {
        view = null
    }
}