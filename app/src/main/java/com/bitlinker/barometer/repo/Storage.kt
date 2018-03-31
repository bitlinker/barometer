package com.bitlinker.barometer.repo

import android.content.Context

/**
 * Persistent application storage
 *
 * Created by bitlinker on 01.04.2018.
 */
public class Storage(appContext: Context) {
    companion object {
        internal val BAROMETER_STORAGE = "Storage"
        internal val STORED_PRESSURE = "STORED_PRESSURE"
        internal val LAST_PRESSURE = "LAST_PRESSURE"
    }

    private val preferences = appContext.getSharedPreferences(BAROMETER_STORAGE, Context.MODE_PRIVATE)

    public fun getStoredPressure(): Float {
        return preferences.getFloat(STORED_PRESSURE, 0.0F)
    }

    public fun setStoredPressure(value: Float) {
        preferences.edit()
                .putFloat(STORED_PRESSURE, value)
                .apply()
    }

    public fun getLastPressure(): Float {
        return preferences.getFloat(LAST_PRESSURE, 0.0F)
    }

    public fun setLastPressure(value: Float) {
        preferences.edit()
                .putFloat(LAST_PRESSURE, value)
                .apply()
    }
}