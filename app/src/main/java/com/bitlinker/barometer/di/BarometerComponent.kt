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