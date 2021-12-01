package edu.gvsu.cis.androidgeocalculator

import android.app.Application
import net.danlew.android.joda.JodaTimeAndroid

class GeoCalculatorApp: Application() {
    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(this)
    }
}
