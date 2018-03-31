package com.bitlinker.barometer.repo

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

/**
 * Barometric sensor repository
 *
 * Created by bitlinker on 31.03.2018.
 */
public class SensorRepo(appContext: Context) : SensorEventListener {
    public interface SensorRepoListener {
        fun onPressureChanged(value: Float)
    }

    private val sensorManager = appContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager?
    private val sensor = sensorManager?.getDefaultSensor(Sensor.TYPE_PRESSURE)
    private var listener: SensorRepoListener? = null

    public var pressure = SensorManager.PRESSURE_STANDARD_ATMOSPHERE

    private fun isAvailable(): Boolean {
        return sensorManager != null && sensor != null
    }

    public fun start(listener: SensorRepoListener): Boolean {
        if (!isAvailable()) {
            return false
        }
        this.listener = listener;
        sensorManager?.registerListener(
                this,
                sensor,
                SensorManager.SENSOR_DELAY_UI)
        return true
    }

    public fun stop() {
        if (isAvailable()) {
            return
        }
        sensorManager?.unregisterListener(this)
        listener = null
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Do nothing...
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val data = event?.values?.get(0) // in hPa
        if (data != null) {
            pressure = data
            listener?.onPressureChanged(data)
        }
    }
}