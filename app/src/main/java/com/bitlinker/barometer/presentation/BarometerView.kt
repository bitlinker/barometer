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

/**
 * Main barometer view interface
 *
 * Created by bitlinker on 31.03.2018.
 */
interface BarometerView {
    fun setPressure(value : Float, isAnimated: Boolean)
    fun setStoredPressure(value : Float, isAnimated: Boolean)
    fun showInitError()
}