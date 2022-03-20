package com.imeshke.firebaselogin.api

import com.imeshke.firebaselogin.api.model.HotelsResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("s/6nt7fkdt7ck0lue/hotels.json")
    suspend fun getHotelList(): Response<HotelsResponse>

}