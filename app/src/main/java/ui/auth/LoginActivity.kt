package com.imeshke.firebaselogin.ui.auth

import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.imeshke.firebaselogin.R
import com.imeshke.firebaselogin.databinding.ActivityLoginBinding
import com.imeshke.firebaselogin.utils.startHomeActivity


import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class LoginActivity : AppCompatActivity(), AuthListener, KodeinAware {

    override val kodein by kodein()
    private val factory : AuthViewModelFactory by instance()


    private lateinit var viewModel: AuthViewModel
    private lateinit var  progressbar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title=getString(R.string.SignIn_header)

        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.authListener = this
        progressbar =  findViewById<ProgressBar>(R.id.progressbar)
    }

    override fun onStarted() {
        progressbar!!.visibility = View.VISIBLE
    }

    override fun onSuccess() {
        progressbar!!.visibility = View.GONE
        startHomeActivity()
    }

    override fun onFailure(message: String) {
        progressbar!!.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        viewModel.user?.let {
            startHomeActivity()
        }
    }
}
