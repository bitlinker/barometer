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
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

/**
 * Persistent application storage
 * <p>
 * Created by bitlinker on 27.03.2018.
 */
public class Storage {
    private static final String BAROMETER_STORAGE = "Storage";
    private static final String STORED_PRESSURE = "STORED_PRESSURE";
    private static final String LAST_PRESSURE = "LAST_PRESSURE";

    private final SharedPreferences mSharedPreferences;

    public Storage(@NonNull Context context) {
        mSharedPreferences = context.getSharedPreferences(BAROMETER_STORAGE, Context.MODE_PRIVATE);
    }

    public float getStoredPressure() {
        return mSharedPreferences.getFloat(STORED_PRESSURE, 0.F);
    }

    public void setStoredPressure(float value) {
        mSharedPreferences.edit()
                .putFloat(STORED_PRESSURE, value)
                .apply();
    }

    public float getLastPressure() {
        return mSharedPreferences.getFloat(LAST_PRESSURE, 0.F);
    }

    public void setLastPressure(float value) {
        mSharedPreferences.edit()
                .putFloat(LAST_PRESSURE, value)
                .apply();
    }
}
