package com.havdulskyi.airporttickets.view.purchased

import android.app.Dialog
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.havdulskyi.airporttickets.R
import com.havdulskyi.airporttickets.databinding.PurchasedTicketFragmentBinding
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class PurchasedTicketFragment : Fragment() {

    companion object {
        fun newInstance() = PurchasedTicketFragment()
    }

    private val model: PurchasedTicketViewModel by viewModel()
    private lateinit var adapter : PurchasedTicketAdapter
    private lateinit var binding: PurchasedTicketFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        adapter = PurchasedTicketAdapter(viewLifecycleOwner, model)
        binding =  DataBindingUtil.inflate(inflater, R.layout.purchased_ticket_fragment, container, false)
        binding.ticketsView.adapter = adapter
        binding.ticketsView.layoutManager = LinearLayoutManager(requireContext())
        model.ticketsLiveData.observe(viewLifecycleOwner, {
                list ->
            if(this::adapter.isInitialized)
                adapter.setData(list)
        })
        model.showTicketQr.observe(viewLifecycleOwner, {
            qr ->
            qr.getContentIfNotHandled()
                ?.let {
                showQrDialog(it)
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

    private fun showQrDialog(bitmap : Bitmap)
    {
        val view = LayoutInflater.from(requireContext())
            .inflate(R.layout.ticket_view, null, false)

        view.findViewById<ImageView>(R.id.ticketQrView)
            .setImageBitmap(bitmap)

        MaterialAlertDialogBuilder(requireActivity())
            .setMessage("Your ticket qr code")
            .setPositiveButton("Okay") { dialog, _ ->
                dialog.dismiss()
            }
            .setView(view)
            .show()
    }

}