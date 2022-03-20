package com.imeshke.firebaselogin

import android.app.Application
import com.imeshke.firebaselogin.data.firebase.FirebaseSource
import com.imeshke.firebaselogin.repositories.UserRepository
import com.imeshke.firebaselogin.ui.auth.AuthViewModelFactory
import com.imeshke.firebaselogin.ui.home.HotelsViewModelFactory

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class FirebaseApplication : Application(), KodeinAware{

    override val kodein = Kodein.lazy {
        import(androidXModule(this@FirebaseApplication))

        bind() from singleton { FirebaseSource() }
        bind() from singleton { UserRepository(instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
        bind() from provider { HotelsViewModelFactory(instance()) }

    }
}