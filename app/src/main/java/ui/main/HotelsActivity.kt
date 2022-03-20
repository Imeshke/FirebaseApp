package com.imeshke.firebaselogin.ui.main

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.asFlow
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.imeshke.firebaselogin.R
import com.imeshke.firebaselogin.api.model.Hotel
import com.imeshke.firebaselogin.databinding.ActivityHotelsBinding
import com.imeshke.firebaselogin.ui.home.HotelsViewModelFactory
import com.imeshke.firebaselogin.ui.main.structure.HotelListAdapter
import com.imeshke.firebaselogin.utils.startDetailActivity
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.messaging.FirebaseMessaging


class HotelsActivity : AppCompatActivity(), KodeinAware , HotelListAdapter.OnClickEvent{

    override val kodein by kodein()
    private val factory : HotelsViewModelFactory by instance()

    private lateinit var viewModel: HotelsViewModel
    private var hotelListAdapter: HotelListAdapter? = null
    private var mFirebaseAnalytics: FirebaseAnalytics? = null
    private var notifyUIJob : Job? = null
    private lateinit var  progressbar : ProgressBar

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotels)
        supportActionBar?.title=getString(R.string.Listview_header)
        setUpFirebaseId()
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val binding: ActivityHotelsBinding = DataBindingUtil.setContentView(this, R.layout.activity_hotels)
        viewModel = ViewModelProviders.of(this, factory).get(HotelsViewModel::class.java)
        binding.viewmodel = viewModel
        progressbar =  findViewById<ProgressBar>(R.id.progressbar)
        val recyclerView: RecyclerView = binding.hotelsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        hotelListAdapter = HotelListAdapter(this)
        binding.hotelsRecyclerView.adapter = hotelListAdapter
    }

    private fun setUpFirebaseId(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            val token = task.result
        })
    }

    private val messageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            intent.extras?.getString("message")
            //TODO handle push notification
        }
    }


    override fun onStart() {
        super.onStart()
        progressbar!!.visibility = View.VISIBLE
        notifyUIJob = lifecycleScope.launch {
            viewModel?.getHotels()?.asFlow()?.collectLatest {
                hotelListAdapter?.setHotelList(it)
                progressbar!!.visibility = View.GONE
            }
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, IntentFilter("MyData"))
    }

    override fun onStop() {
        notifyUIJob?.cancel()
        super.onStop()
    }

    override fun onClickListner(hotel: Hotel) {
        startDetailActivity(hotel)
    }

}