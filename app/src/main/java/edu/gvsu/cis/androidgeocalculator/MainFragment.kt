package edu.gvsu.cis.androidgeocalculator

import android.location.Location
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import java.lang.Exception
import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {

    private var bearingUnits = "Degrees"
    private var distanceUnits = "Kilometers"

    private var p1Lat: EditText? = null
    private var p1Lng: EditText? = null
    private var p2Lat: EditText? = null
    private var p2Lng: EditText? = null
    private var distance: TextView? = null
    private var bearing: TextView? = null
    var navCtrl : NavController? = null

    lateinit var viewModel: CalculatorDataViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        navCtrl = findNavController()
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(CalculatorDataViewModel::class.java)
        viewModel.settings.observe(this.viewLifecycleOwner, Observer { z ->
            distanceUnits = z.distanceUnits
            bearingUnits = z.bearingUnits
        })
        viewModel.calcData.observe(this.viewLifecycleOwner, Observer { z ->
            p1Lat?.setText(z.p1Lat)
            p1Lng?.setText(z.p1Lng)
            p2Lat?.setText(z.p2Lng)
            p2Lng?.setText(z.p2Lng)
            updateScreen()
        })
        val clearButton = view.findViewById(R.id.clearButton) as Button
        val calcButton = view.findViewById(R.id.calcButton) as Button
        p1Lat = view.findViewById(R.id.p1lat) as EditText
        p1Lng = view.findViewById(R.id.p1long) as EditText
        p2Lat = view.findViewById(R.id.p2lat) as EditText
        p2Lng = view.findViewById(R.id.p2long) as EditText
        distance = view.findViewById(R.id.distance) as TextView
        bearing = view.findViewById(R.id.bearing) as TextView

        clearButton.setOnClickListener { v: View? ->
            hideKeyboard()
            p1Lat!!.text.clear()
            p1Lng!!.text.clear()
            p2Lat!!.text.clear()
            p2Lng!!.text.clear()
            distance!!.text = "Distance:"
            bearing!!.text = "Bearing:"
        }
        calcButton.setOnClickListener { v: View? -> updateScreen() }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                viewModel.settings.value = CalculatorDataViewModel.UnitSettings(distanceUnits,bearingUnits)
                viewModel.calcData.value = CalculatorDataViewModel.CalculatorState(p1Lat?.text.toString(), p1Lng?.text.toString(), p2Lat?.text.toString(), p2Lng?.text.toString())
                navCtrl?.navigate(R.id.action_main2settings)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    private fun updateScreen() {
        try {
            val lat1 = p1Lat!!.text.toString().toDouble()
            val lng1 = p1Lng!!.text.toString().toDouble()
            val lat2 = p2Lat!!.text.toString().toDouble()
            val lng2 = p2Lng!!.text.toString().toDouble()
            val p1 = Location("") //provider name is unecessary
            p1.latitude = lat1 //your coords of course
            p1.longitude = lng1
            val p2 = Location("")
            p2.latitude = lat2
            p2.longitude = lng2
            var b = p1.bearingTo(p2).toDouble()
            var d = p1.distanceTo(p2) / 1000.0
            if (distanceUnits == "Miles") {
                d *= 0.621371
            }
            if (bearingUnits == "Mils") {
                b *= 17.777777778
            }
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.CEILING
            val dStr = "Distance: " + df.format(d) + " " + distanceUnits
            distance!!.text = dStr
            val bStr = "Bearing: " + df.format(b) + " " + bearingUnits
            bearing!!.text = bStr
            hideKeyboard()
        } catch (e: Exception) {
            return
        }
    }

    private fun hideKeyboard() {
        // Check if no view has focus:
        val view = this.activity?.currentFocus
        if (view != null) {
            val imm = this.activity?.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}