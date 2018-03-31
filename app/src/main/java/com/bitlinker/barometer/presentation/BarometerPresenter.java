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

package com.bitlinker.barometer.presentation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bitlinker.barometer.BarometerApp;
import com.bitlinker.barometer.presentation.view.IBarometerView;
import com.bitlinker.barometer.repo.BarometerRepo;

/**
 * Barometer view presenter
 * <p>
 * Created by bitlinker on 27.03.2018.
 */

public class BarometerPresenter {
    @NonNull
    private final BarometerRepo mRepo;
    @Nullable
    private IBarometerView mView;

    public BarometerPresenter(@NonNull IBarometerView view) {
        mView = view;
        mRepo = BarometerApp.getAppComponent().provideBarometerRepo();
    }

    public void resume() {
        boolean isStated = mRepo.start(new BarometerRepo.BarometerRepoListener() {
            @Override
            public void onPressureChanged(float pressure, boolean isAnimated) {
                if (mView != null) {
                    mView.setPressure(pressure, isAnimated);
                }
            }

            @Override
            public void onStoredPressureChanged(float pressure, boolean isAnimated) {
                if (mView != null) {
                    mView.setStoredPressure(pressure, isAnimated);
                }
            }
        });

        if (mView !=null && !isStated) {
            mView.showInitError();
        }
    }

    public void pause() {
        mRepo.stop();
    }

    public void onManualArrowPress() {
        mRepo.save();
    }

    public void removeView() {
        mView = null;
    }
}
