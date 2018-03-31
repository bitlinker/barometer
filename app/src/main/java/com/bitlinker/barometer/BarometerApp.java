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

import android.app.Application;
import android.support.annotation.NonNull;

/**
 * Main application class
 * <p>
 * Created by bitlinker on 31.03.2018.
 */
public class BarometerApp extends Application {
    private static AppComponent sAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        sAppComponent = new AppComponent(this);
    }

    public static @NonNull
    AppComponent getAppComponent() {
        if (sAppComponent == null) {
            throw new IllegalStateException("No app component!");
        }
        return sAppComponent;
    }
}
