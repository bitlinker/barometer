package com.bitlinker.barometer.di

import android.content.Context
import com.bitlinker.barometer.repo.BarometerRepo
import com.bitlinker.barometer.repo.SensorRepo
import com.bitlinker.barometer.repo.Storage

/**
 * Barometer DI component
 *
 * Created by bitlinker on 01.04.2018.
 */
public class BarometerComponent(private val appContext: Context) {
    private companion object {
        private val sync = Any()
    }

    private var _storage: Storage? = null
    public val storage: Storage
        get() {
            synchronized(sync) {
                _storage = _storage ?: Storage(appContext)
                return _storage!!
            }
        }

    private var _sensorRepo: SensorRepo? = null
    public val sensorRepo: SensorRepo
        get() {
            synchronized(sync) {
                _sensorRepo = _sensorRepo ?: SensorRepo(appContext)
                return _sensorRepo!!
            }
        }

    private var _barometerRepo: BarometerRepo? = null
    public val barometerRepo: BarometerRepo
        get() {
            synchronized(sync) {
                _barometerRepo = _barometerRepo ?: BarometerRepo(sensorRepo, storage)
                return _barometerRepo!!
            }
        }
}