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

import android.animation.ObjectAnimator
import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import com.bitlinker.barometer.R
import kotlinx.android.synthetic.main.activity_barometer_view.*

/**
 * Main barometer activity
 *
 * Created by bitlinker on 31.03.2018.
 */
class BarometerActivity : Activity(), BarometerView {
    companion object {
        internal val MANUAL_ARROW_ANIM_DURATION = 500L
        internal val MAIN_ARROW_ANIM_DURATION = 200L

        internal val ROTATION_MIN = -54.8F
        internal val ROTATION_MAX = 233.0F
        internal val PRESSURE_MIN = 720.0F
        internal val PRESSURE_MAX = 800.0F
    }

    private lateinit var presenter: BarometerPresenter


    private lateinit var arrowMainAnimator: ObjectAnimator
    private lateinit var arrowManualAnimator: ObjectAnimator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barometer_view)

        ivArrowMain.rotation = ROTATION_MIN
        ivArrowManual.rotation = ROTATION_MIN
        ivArrowManual.setOnLongClickListener {
            presenter.onManualArrowPress()
            false
        }

        arrowMainAnimator = ObjectAnimator.ofFloat(ivArrowMain, "rotation", 0f)
        arrowMainAnimator.duration = MAIN_ARROW_ANIM_DURATION

        arrowManualAnimator = ObjectAnimator.ofFloat(ivArrowManual, "rotation", 0f)
        arrowManualAnimator.duration = MANUAL_ARROW_ANIM_DURATION

        presenter = BarometerPresenter(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.resume()
    }

    override fun onPause() {
        presenter.pause()
        super.onPause()
    }

    override fun setPressure(value: Float, isAnimated: Boolean) {
        val rotation = mmhg2rotation(value)
        if (isAnimated) {
            arrowMainAnimator.setFloatValues(rotation)
            arrowMainAnimator.start()
        } else {
            ivArrowMain.rotation = rotation
        }
    }

    override fun setStoredPressure(value: Float, isAnimated: Boolean) {
        val rotation = mmhg2rotation(value)
        if (isAnimated) {
            arrowManualAnimator.setFloatValues(rotation)
            arrowManualAnimator.start()
        } else {
            ivArrowManual.rotation = rotation
        }
    }

    private fun mmhg2rotation(value: Float): Float {
        val clamped = Math.max(PRESSURE_MIN, Math.min(value, PRESSURE_MAX))
        return ROTATION_MIN + (clamped - PRESSURE_MIN) / (PRESSURE_MAX - PRESSURE_MIN) * (ROTATION_MAX - ROTATION_MIN)
    }

    override fun showInitError() {
        AlertDialog.Builder(this)
                .setTitle(R.string.barometer_app_name)
                .setMessage(R.string.activity_barometer_error_not_supported)
                .setPositiveButton(android.R.string.ok) { _, _ -> finish() }
                .setCancelable(false)
                .show()
    }

    override fun onDestroy() {
        presenter.removeView()
        super.onDestroy()
    }
}