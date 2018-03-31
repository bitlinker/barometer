/*
 * Copyright (c) 2018 Bitlinker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bitlinker.barometer.repo

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

/**
 * Barometric sensor wrapper
 *
 * Created by bitlinker on 31.03.2018.
 */
class SensorWrapper(appContext: Context) : SensorEventListener {
    interface SensorRepoListener {
        fun onPressureChanged(value: Float)
    }

    private val sensorManager = appContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager?
    private val sensor = sensorManager?.getDefaultSensor(Sensor.TYPE_PRESSURE)
    private var listener: SensorRepoListener? = null

    var pressure = SensorManager.PRESSURE_STANDARD_ATMOSPHERE

    private fun isAvailable(): Boolean {
        return sensorManager != null && sensor != null
    }

    fun start(listener: SensorRepoListener): Boolean {
        if (!isAvailable()) {
            return false
        }
        this.listener = listener
        sensorManager?.registerListener(
                this,
                sensor,
                SensorManager.SENSOR_DELAY_UI)
        return true
    }

    fun stop() {
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