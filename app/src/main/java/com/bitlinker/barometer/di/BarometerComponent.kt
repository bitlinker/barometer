package com.bitlinker.barometer.di

import android.content.Context
import com.bitlinker.barometer.repo.BarometerRepo
import com.bitlinker.barometer.repo.SensorWrapper
import com.bitlinker.barometer.repo.Storage

/**
 * Barometer DI component
 *
 * Created by bitlinker on 01.04.2018.
 */
class BarometerComponent(private val appContext: Context) {
    private companion object {
        private val sync = Any()
    }

    private var _storage: Storage? = null
    private val storage: Storage
        get() {
            synchronized(sync) {
                _storage = _storage ?: Storage(appContext)
                return _storage!!
            }
        }

    private var _sensorWrapper: SensorWrapper? = null
    private val sensorWrapper: SensorWrapper
        get() {
            synchronized(sync) {
                _sensorWrapper = _sensorWrapper ?: SensorWrapper(appContext)
                return _sensorWrapper!!
            }
        }

    private var _barometerRepo: BarometerRepo? = null
    val barometerRepo: BarometerRepo
        get() {
            synchronized(sync) {
                _barometerRepo = _barometerRepo ?: BarometerRepo(sensorWrapper, storage)
                return _barometerRepo!!
            }
        }
}