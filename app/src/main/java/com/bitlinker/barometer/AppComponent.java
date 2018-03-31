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

package com.bitlinker.barometer;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bitlinker.barometer.repo.BarometerRepo;
import com.bitlinker.barometer.repo.SensorRepo;
import com.bitlinker.barometer.repo.Storage;

/**
 * Main application DI component
 * Created by bitlinker on 31.03.2018.
 */

public class AppComponent {
    @NonNull
    private final Context mAppContext;
    private Storage mStorage;
    private SensorRepo mSensorRepo;
    private BarometerRepo mBarometerRepo;

    public AppComponent(@NonNull Context appContext) {
        mAppContext = appContext;
    }

    @NonNull
    private Storage provideStorage() {
        synchronized (this) {
            if (mStorage == null) {
                mStorage = new Storage(mAppContext);
            }
            return mStorage;
        }
    }

    @NonNull
    private SensorRepo provideSensorRepo() {
        synchronized (this) {
            if (mSensorRepo == null) {
                mSensorRepo = new SensorRepo(mAppContext);
            }
            return mSensorRepo;
        }
    }

    public @NonNull
    BarometerRepo provideBarometerRepo() {
        synchronized (this) {
            if (mBarometerRepo == null) {
                mBarometerRepo = new BarometerRepo(
                        provideSensorRepo(),
                        provideStorage()
                );
            }
            return mBarometerRepo;
        }
    }

}
