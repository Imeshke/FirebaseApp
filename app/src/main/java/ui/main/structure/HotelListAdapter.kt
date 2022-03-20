package com.imeshke.firebaselogin.ui.main.structure

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.imeshke.firebaselogin.R
import com.imeshke.firebaselogin.api.model.Hotel
import com.imeshke.firebaselogin.databinding.HotelListItemBinding
import com.imeshke.firebaselogin.ui.main.HotelsActivity
import com.imeshke.firebaselogin.ui.main.structure.HotelListAdapter.HotelViewHolder

internal class HotelListAdapter(private val callback: OnClickEvent) : RecyclerView.Adapter<HotelViewHolder>() {
    private var hotels: List<Hotel>? = null
    interface OnClickEvent {
        fun onClickListner(hotel:Hotel)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): HotelViewHolder {
        val hotelListItemBinding: HotelListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context),
            R.layout.hotel_list_item, viewGroup, false
        )
        return HotelViewHolder(hotelListItemBinding)
    }

    override fun onBindViewHolder(hotelViewHolder: HotelViewHolder, i: Int) {
        val currentHotel = hotels!![i]

        hotelViewHolder.hotelListItemBinding.hotel = currentHotel
        hotelViewHolder.itemView.setOnClickListener {
            callback.onClickListner(currentHotel)
        }

    }

    override fun getItemCount(): Int {
        return if (hotels != null) {
            hotels!!.size
        } else {
            0
        }
    }

    fun setHotelList(hotelsList: List<Hotel>) {
        this.hotels = hotelsList
        notifyDataSetChanged()
    }

    internal class HotelViewHolder(@NonNull hotelListItemBinding: HotelListItemBinding) :
        RecyclerView.ViewHolder(hotelListItemBinding.root) {
        var hotelListItemBinding: HotelListItemBinding = hotelListItemBinding

    }



}



