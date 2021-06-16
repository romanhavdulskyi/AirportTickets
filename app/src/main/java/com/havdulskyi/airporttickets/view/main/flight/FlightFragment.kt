package com.havdulskyi.airporttickets.view.main.flight

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.emc.verticalweekcalendar.model.CalendarDay
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.havdulskyi.airporttickets.R
import com.havdulskyi.airporttickets.databinding.FlightFragmentBinding
import kotlinx.coroutines.FlowPreview
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*


@FlowPreview
class FlightFragment : Fragment() {

    companion object {
        fun newInstance() = FlightFragment()
    }

    private val model: FlightViewModel by viewModel()
    private lateinit var adapter : FlightAdapter
    private lateinit var binding: FlightFragmentBinding
    private lateinit var selected : GregorianCalendar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        adapter = FlightAdapter(viewLifecycleOwner, model)
        binding =  DataBindingUtil.inflate(inflater, R.layout.flight_fragment, container, false)
        binding.verticalCalendar.setDateWatcher { year, month, day, view ->
            view?.dayView?.setBackgroundColor(Color.parseColor("#ff4000"))
            if(this::selected.isInitialized) {
                if (selected.compareTo(GregorianCalendar(year,
                        month,
                        day)) == 0
                ) CalendarDay.SELECTED else CalendarDay.DEFAULT
            } else {
                selected = GregorianCalendar(year, month, day)
                CalendarDay.SELECTED
            }
        }
        binding.verticalCalendar.setOnDateClickListener { year, month, day ->
            val selectedDay = GregorianCalendar(year, month, day)
            if (selected.compareTo(selectedDay) != 0) {
                selected = selectedDay
                model.setDepartureDate(selectedDay)
            }
        }
        binding.flightList.layoutManager = LinearLayoutManager(requireContext())
        binding.flightList.adapter = adapter
        binding.autoCompleteTextView.addTextChangedListener {
            model.setSearchQuery(it.toString() ?: "")
        }
        binding.verticalCalendar.init()
        model.flightsLiveData.observe(viewLifecycleOwner, {
            list ->
            if(this::adapter.isInitialized)
                adapter.setData(list)
        })
        model.showBookedDialog.observe(viewLifecycleOwner, {
                event ->
            event.getContentIfNotHandled()
                ?.let {
                showBookedDialog(it)
            }
        })
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(this::binding.isInitialized) {
            binding.viewmodel = model
            binding.lifecycleOwner = viewLifecycleOwner
            binding.executePendingBindings()
        }
    }

    private fun showBookedDialog(flightId : String)
    {
        MaterialAlertDialogBuilder(requireActivity())
            .setTitle("You booked the ticket with id $flightId")
            .setMessage("Here should a Google Billing dialog, but it's the simplest example ^)")
            .setPositiveButton("Okay") { dialog, _ ->
                dialog.dismiss()
            }
            .show()

    }

}