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

package com.bitlinker.barometer.presentation

import com.bitlinker.barometer.BarometerApp
import com.bitlinker.barometer.repo.BarometerRepo

/**
 * Main barometer view presenter
 *
 * Created by bitlinker on 31.03.2018.
 */
class BarometerPresenter(private var view: BarometerView?) {
    private val repo = BarometerApp.barometerComponent.barometerRepo

    fun resume() {
        val started = repo.start(object : BarometerRepo.BarometerRepoListener {
            override fun onPressureChanged(value: Float, isAnimated: Boolean) {
                view?.setPressure(value, isAnimated)
            }

            override fun onStoredPressureChanged(value: Float, isAnimated: Boolean) {
                view?.setStoredPressure(value, isAnimated)
            }
        })
        if (!started) {
            view?.showInitError()
        }
    }

    fun pause() {
        repo.stop()
    }

    fun onManualArrowPress() {
        repo.setManualPressure()
    }

    fun removeView() {
        view = null
    }
}