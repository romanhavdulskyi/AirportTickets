package com.havdulskyi.airporttickets.view.main.flight

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.havdulskyi.airporttickets.R
import com.havdulskyi.airporttickets.data.entity.ProposedFlightEntity
import com.havdulskyi.airporttickets.databinding.FlightItemBinding
import com.havdulskyi.airporttickets.viewModel.main.flight.FlightViewModel

class FlightAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val flightViewModel: FlightViewModel,
) :
    RecyclerView.Adapter<FlightAdapter.ViewHolder>() {

    init {
        setHasStableIds(true)
    }

    private var dataSet: MutableList<ProposedFlightEntity> = mutableListOf()

    override fun getItemId(position: Int): Long {
        return dataSet[position].id.hashCode().toLong()
    }

    fun setData(data: List<ProposedFlightEntity>)
    {
        dataSet.clear()
        dataSet.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(val flightItemBinding: FlightItemBinding) :
        RecyclerView.ViewHolder(flightItemBinding.root)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val inflater = LayoutInflater.from(viewGroup.context)
        return ViewHolder(DataBindingUtil
            .inflate(inflater,
                R.layout.flight_item,
                viewGroup,
                false))
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

       val item = dataSet[position]
        viewHolder.flightItemBinding.flight = item
        viewHolder.flightItemBinding.flightViewModel = flightViewModel
        viewHolder.flightItemBinding.lifecycleOwner = lifecycleOwner
        viewHolder.flightItemBinding.executePendingBindings()
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
