package edu.gvsu.cis.androidgeocalculator

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculatorDataViewModel : ViewModel() {

    data class UnitSettings(val distanceUnits: String, val bearingUnits:String)
    data class CalculatorState(val p1Lat:String, val p1Lng:String, val p2lat:String, val p2Lng:String)

    private var _settings = MutableLiveData<UnitSettings>()
    private var _calcData = MutableLiveData<CalculatorState>()

    // Declare properties with getters
    val settings get() = _settings
    val calcData get() = _calcData
}