package com.imeshke.firebaselogin.ui.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.imeshke.firebaselogin.R
import com.imeshke.firebaselogin.api.model.Hotel
import com.imeshke.firebaselogin.databinding.FragmentHotelDetailsBinding


class DetailDataFragment(hotel: Hotel) : Fragment() {

private var  currentHotel :Hotel = hotel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)
        (activity as DetailActivity?)!!.supportActionBar?.title=getString(R.string.details_header)
        (activity as DetailActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as DetailActivity?)!!.supportActionBar!!.setDisplayShowHomeEnabled(true)
        val fragmentHotelDetailsBinding =
            FragmentHotelDetailsBinding.inflate(inflater, container, false)
        fragmentHotelDetailsBinding.hotel = currentHotel
        return fragmentHotelDetailsBinding.root
    }

    companion object {
        fun newInstance(hotel:Hotel): DetailDataFragment {
            return DetailDataFragment(hotel)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)


    }



}
