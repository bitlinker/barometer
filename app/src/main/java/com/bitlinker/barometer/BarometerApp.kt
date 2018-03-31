package com.bitlinker.barometer

import android.app.Application
import com.bitlinker.barometer.di.BarometerComponent

/**
 * Main application class
 *
 * Created by bitlinker on 01.04.2018.
 */
public class BarometerApp : Application() {
    companion object {
        @JvmStatic lateinit var barometerComponent : BarometerComponent
    }

    override fun onCreate() {
        super.onCreate()
        barometerComponent = BarometerComponent(this)
    }
}