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

package com.bitlinker.barometer.repo;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Barometric sensor repository
 * <p>
 * Created by bitlinker on 27.03.2018.
 */
public class SensorRepo implements SensorEventListener {
    public interface SensorRepoListener {
        void onPressureChanged(float pressure);
    }

    @Nullable
    private final SensorManager mSensorManager;
    private Sensor mPressureSensor;
    private float mPressure = SensorManager.PRESSURE_STANDARD_ATMOSPHERE;
    @Nullable
    private SensorRepoListener mListener;

    public SensorRepo(@NonNull Context context) {
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager != null) {
            mPressureSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        }
    }

    private boolean isAvailable() {
        return mSensorManager != null && mPressureSensor != null;
    }

    boolean start(@NonNull SensorRepoListener listener) {
        if (!isAvailable()) {
            return false;
        }
        mListener = listener;
        mSensorManager.registerListener(this,
                mPressureSensor,
                SensorManager.SENSOR_DELAY_UI);
        return true;
    }

    void stop() {
        if (!isAvailable()) {
            return;
        }
        mSensorManager.unregisterListener(this);
        mListener = null;
    }

    float getPressure() {
        return mPressure;
    }

    @Override
    public void onSensorChanged(@NonNull SensorEvent event) {
        if (mListener != null) {
            mPressure = event.values[0];  // in hPa
            mListener.onPressureChanged(mPressure);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing...
    }
}
