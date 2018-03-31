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

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Barometer main repository
 *
 * Created by bitlinker on 27.03.2018.
 */
public class BarometerRepo {
    public interface BarometerRepoListener {
        void onPressureChanged(float pressure, boolean isAnimated);
        void onStoredPressureChanged(float pressure, boolean isAnimated);
    }

    @NonNull
    private final SensorRepo mSensorRepo;
    @NonNull
    private final Storage mStorage;
    @Nullable
    private BarometerRepoListener mListener;

    public BarometerRepo(@NonNull SensorRepo sensorRepo,
                         @NonNull Storage storage) {
        mSensorRepo = sensorRepo;
        mStorage = storage;
    }

    public boolean start(@NonNull BarometerRepoListener listener) {
        mListener = listener;
        mListener.onStoredPressureChanged(hpa2mmhg(mStorage.getStoredPressure()), false);
        mListener.onPressureChanged(hpa2mmhg(mStorage.getLastPressure()), false);
        return mSensorRepo.start(pressure -> mListener.onPressureChanged(hpa2mmhg(pressure), true));
    }

    public void stop() {
        mStorage.setLastPressure(mSensorRepo.getPressure());
        mSensorRepo.stop();
        mListener = null;
    }

    public void save() {
        mStorage.setStoredPressure(mSensorRepo.getPressure());
        if (mListener != null) {
            mListener.onStoredPressureChanged(hpa2mmhg(mStorage.getStoredPressure()), true);
        }
    }

    private static float hpa2mmhg(float value) {
        return value * 0.75006157584566F;
    }
}
