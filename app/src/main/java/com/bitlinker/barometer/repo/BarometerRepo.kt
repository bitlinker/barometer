package com.bitlinker.barometer.repo

/**
 * Created by bitlinker on 01.04.2018.
 */
public class BarometerRepo(
        private val sensorRepo: SensorRepo,
        private val storage: Storage) {

    public interface BarometerRepoListener {
        fun onPressureChanged(value: Float, isAnimated: Boolean)
        fun onStoredPressureChanged(value: Float, isAnimated: Boolean)
    }

    private var listener: BarometerRepoListener? = null

    public fun start(listener: BarometerRepoListener): Boolean {
        this.listener = listener
        listener.onStoredPressureChanged(hpa2mmhg(storage.getStoredPressure()), false)
        listener.onPressureChanged(hpa2mmhg(storage.getLastPressure()), false)
        return sensorRepo.start(object : SensorRepo.SensorRepoListener {
            override fun onPressureChanged(value: Float) {
                listener.onPressureChanged(hpa2mmhg(value), true)
            }
        })
    }

    public fun stop() {
        storage.setLastPressure(sensorRepo.pressure)
        sensorRepo.stop()
        listener = null
    }

    public fun setManualPressure() {
        storage.setStoredPressure(sensorRepo.pressure)
        listener?.onStoredPressureChanged(hpa2mmhg(storage.getStoredPressure()), true)
    }

    private fun hpa2mmhg(value: Float): Float {
        return value * 0.75006157584566F
    }
}