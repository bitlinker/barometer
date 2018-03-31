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

/**
 * Persistent application storage
 *
 * Created by bitlinker on 01.04.2018.
 */
class Storage(appContext: Context) {
    companion object {
        internal val BAROMETER_STORAGE = "Storage"
        internal val STORED_PRESSURE = "STORED_PRESSURE"
        internal val LAST_PRESSURE = "LAST_PRESSURE"
    }

    private val preferences = appContext.getSharedPreferences(BAROMETER_STORAGE, Context.MODE_PRIVATE)

    fun getStoredPressure(): Float {
        return preferences.getFloat(STORED_PRESSURE, 0.0F)
    }

    fun setStoredPressure(value: Float) {
        preferences.edit()
                .putFloat(STORED_PRESSURE, value)
                .apply()
    }

    fun getLastPressure(): Float {
        return preferences.getFloat(LAST_PRESSURE, 0.0F)
    }

    fun setLastPressure(value: Float) {
        preferences.edit()
                .putFloat(LAST_PRESSURE, value)
                .apply()
    }
}