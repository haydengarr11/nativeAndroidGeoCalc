package edu.gvsu.cis.androidgeocalculator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton


/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment() {

    private var bearingUnits: String? = "Degrees"
    private var distanceUnits: String? = "Kilometers"
    var navCtrl: NavController? = null
    lateinit var viewModel: CalculatorDataViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navCtrl = findNavController()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(CalculatorDataViewModel::class.java)
        viewModel.settings.observe(this.viewLifecycleOwner, Observer { z ->
            distanceUnits = z.distanceUnits
            bearingUnits = z.bearingUnits
            setupSpinners()
        })

        val fab: FloatingActionButton = view.findViewById(R.id.fab) as FloatingActionButton

        fab.setOnClickListener { //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            viewModel.settings.value = CalculatorDataViewModel.UnitSettings(distanceUnits!!,bearingUnits!!)
            navCtrl?.navigate(R.id.action_settings2main)
        }
    }

    fun setupSpinners() {
        val spinnerDistance = this.activity?.let {
            it.findViewById(R.id.distance_units) as Spinner
        }

        val adapterDistance = this.activity?.applicationContext?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.distance,
                android.R.layout.simple_spinner_dropdown_item
            )
        }

        spinnerDistance?.adapter = adapterDistance

        spinnerDistance?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                i: Int,
                l: Long
            ) {
                distanceUnits = adapterView?.getItemAtPosition(i) as String ?: "Kilometers"
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

        // set default value
        if (distanceUnits != null) {
            adapterDistance?.getPosition(distanceUnits)?.let {
                spinnerDistance?.setSelection(it)
            }
        }

        val spinnerBearing = activity?.let {
            it.findViewById(R.id.bearing_units) as Spinner
        }

        val adapterBearing = this.activity?.applicationContext?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.bearing,
                android.R.layout.simple_spinner_dropdown_item
            )
        }
//            adapterBearing.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerBearing?.adapter = adapterBearing
        spinnerBearing?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                i: Int,
                l: Long
            ) {
                bearingUnits = adapterView?.getItemAtPosition(i) as String ?: "Degrees"
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

        // set default value
        if (bearingUnits != null) {
            adapterBearing?.getPosition(bearingUnits)?.let {
                spinnerBearing?.setSelection(it)
            }
        }
    }
}


