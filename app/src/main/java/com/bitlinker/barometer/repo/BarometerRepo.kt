package com.bitlinker.barometer.repo

/**
 * Barometer repository
 *
 * Created by bitlinker on 01.04.2018.
 */
class BarometerRepo(
        private val sensorWrapper: SensorWrapper,
        private val storage: Storage) {

    interface BarometerRepoListener {
        fun onPressureChanged(value: Float, isAnimated: Boolean)
        fun onStoredPressureChanged(value: Float, isAnimated: Boolean)
    }

    private var listener: BarometerRepoListener? = null

    fun start(listener: BarometerRepoListener): Boolean {
        this.listener = listener
        listener.onStoredPressureChanged(hpa2mmhg(storage.getStoredPressure()), false)
        listener.onPressureChanged(hpa2mmhg(storage.getLastPressure()), false)
        return sensorWrapper.start(object : SensorWrapper.SensorRepoListener {
            override fun onPressureChanged(value: Float) {
                listener.onPressureChanged(hpa2mmhg(value), true)
            }
        })
    }

    fun stop() {
        storage.setLastPressure(sensorWrapper.pressure)
        sensorWrapper.stop()
        listener = null
    }

    fun setManualPressure() {
        storage.setStoredPressure(sensorWrapper.pressure)
        listener?.onStoredPressureChanged(hpa2mmhg(storage.getStoredPressure()), true)
    }

    private fun hpa2mmhg(value: Float): Float {
        return value * 0.75006157584566F
    }
}