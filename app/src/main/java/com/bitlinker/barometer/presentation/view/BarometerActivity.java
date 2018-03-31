/*
 *    Copyright (c) 2018 Bitlinker
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.bitlinker.barometer.presentation.view;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bitlinker.barometer.R;
import com.bitlinker.barometer.presentation.BarometerPresenter;

/**
 * Main barometer view activity
 * <p>
 * TODO:
 * kotlin
 * <p>
 * Created by bitlinker on 25.03.2018.
 */
public class BarometerActivity extends Activity implements IBarometerView {
    private static final int MANUAL_ARROW_ANIM_DURATION = 500;
    private static final int MAIN_ARROW_ANIM_DURATION = 200;

    private static final float ROTATION_MIN = -54.8F;
    private static final float ROTATION_MAX = 233.F;
    private static final float PRESSURE_MIN = 720.0F;
    private static final float PRESSURE_MAX = 800.F;

    private BarometerPresenter mPresenter;

    private ImageView mIvArrowMain;
    private ImageView mIvArrowManual;

    private ObjectAnimator mArrowMainAnimator;
    private ObjectAnimator mArrowManualAnimator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_barometer_view);
        mIvArrowMain = findViewById(R.id.ivArrowMain);
        mIvArrowMain.setRotation(ROTATION_MIN);
        mIvArrowManual = findViewById(R.id.ivArrowManual);
        mIvArrowManual.setRotation(ROTATION_MIN);
        mIvArrowManual.setOnLongClickListener(v -> {
            mPresenter.onManualArrowPress();
            return false;
        });

        mArrowMainAnimator = ObjectAnimator.ofFloat(mIvArrowMain, "rotation", 0.F);
        mArrowMainAnimator.setDuration(MAIN_ARROW_ANIM_DURATION);

        mArrowManualAnimator = ObjectAnimator.ofFloat(mIvArrowManual, "rotation", 0.F);
        mArrowManualAnimator.setDuration(MANUAL_ARROW_ANIM_DURATION);

        mPresenter = new BarometerPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override
    protected void onPause() {
        mPresenter.pause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mPresenter.removeView();
        super.onDestroy();
    }

    @Override
    public void setPressure(float value, boolean isAnimated) {
        float v = mmhg2rotation(value);
        if (isAnimated) {
            mArrowMainAnimator.setFloatValues(v);
            mArrowMainAnimator.start();
        } else {
            mIvArrowMain.setRotation(v);
        }

        mIvArrowMain.setRotation(mmhg2rotation(value));
    }

    @Override
    public void setStoredPressure(float value, boolean isAnimated) {
        float v = mmhg2rotation(value);
        if (isAnimated) {
            mArrowManualAnimator.setFloatValues(v);
            mArrowManualAnimator.start();
        } else {
            mIvArrowManual.setRotation(v);
        }
    }

    private float mmhg2rotation(float value) {
        value = Math.max(PRESSURE_MIN, Math.min(value, PRESSURE_MAX));
        return ROTATION_MIN
                + (value - PRESSURE_MIN) / (PRESSURE_MAX - PRESSURE_MIN)
                * (ROTATION_MAX - ROTATION_MIN);
    }

    @Override
    public void showInitError() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.barometer_app_name)
                .setMessage(R.string.activity_barometer_error_not_supported)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> finish())
                .setCancelable(false)
                .show();
    }
}
