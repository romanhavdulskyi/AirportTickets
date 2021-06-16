package com.havdulskyi.airporttickets.view.main.purchased

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.havdulskyi.airporttickets.R
import com.havdulskyi.airporttickets.data.entity.PurchasedTicketEntity
import com.havdulskyi.airporttickets.databinding.TicketItemBinding

class PurchasedTicketAdapter(private val lifecycleOwner: LifecycleOwner,
                             private val purchasedTicketViewModel: PurchasedTicketViewModel) :
        RecyclerView.Adapter<PurchasedTicketAdapter.ViewHolder>() {

    init {
        setHasStableIds(true)
    }

    private var dataSet: MutableList<PurchasedTicketEntity> = mutableListOf()

    override fun getItemId(position: Int): Long {
        return dataSet[position].id.hashCode().toLong()
    }

    fun setData(data: List<PurchasedTicketEntity>)
    {
        dataSet.clear()
        dataSet.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(val ticketItemBinding: TicketItemBinding) :
        RecyclerView.ViewHolder(ticketItemBinding.root)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val inflater = LayoutInflater.from(viewGroup.context)
        return ViewHolder(DataBindingUtil
            .inflate(inflater,
                R.layout.ticket_item,
                viewGroup,
                false))
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

       val item = dataSet[position]
        viewHolder.ticketItemBinding.flight = item
        viewHolder.ticketItemBinding.flightViewModel = purchasedTicketViewModel
        viewHolder.ticketItemBinding.lifecycleOwner = lifecycleOwner
        viewHolder.ticketItemBinding.executePendingBindings()
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
