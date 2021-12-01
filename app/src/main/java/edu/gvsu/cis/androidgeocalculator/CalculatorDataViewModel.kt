package edu.gvsu.cis.androidgeocalculator

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.gvsu.cis.androidgeocalculator.placeholder.HistoryContent

class CalculatorDataViewModel : ViewModel() {

    data class UnitSettings(val distanceUnits: String, val bearingUnits:String)
    data class CalculatorState(val p1Lat:String, val p1Lng:String, val p2lat:String, val p2Lng:String)

    private var _settings = MutableLiveData<UnitSettings>()
    private var _calcData = MutableLiveData<CalculatorState>()
    private var _selected = MutableLiveData<HistoryContent.HistoryItem>()


    // Declare properties with getters
    val settings get() = _settings
    val calcData get() = _calcData
    val selected get() = _selected
}