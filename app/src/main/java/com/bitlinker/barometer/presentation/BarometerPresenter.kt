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

    public fun resume() {
        var started = repo.start(object : BarometerRepo.BarometerRepoListener {
            override fun onPressureChanged(pressure: Float, isAnimated: Boolean) {
                view?.setPressure(pressure, isAnimated)
            }

            override fun onStoredPressureChanged(pressure: Float, isAnimated: Boolean) {
                view?.setStoredPressure(pressure, isAnimated)
            }
        })
        if (!started) {
            view?.showInitError()
        }
    }

    public fun pause() {
        repo.stop()
    }

    public fun onManualArrowPress() {
        repo.setManualPressure()
    }

    public fun removeView() {
        view = null
    }
}