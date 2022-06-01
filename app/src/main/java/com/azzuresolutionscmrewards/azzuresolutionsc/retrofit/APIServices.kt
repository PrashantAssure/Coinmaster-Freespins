package com.azzuresolutionscmrewards.azzuresolutionsc.retrofit

import retrofit2.Call
import retrofit2.http.GET

interface APIServices {
    @GET("Spins.txt")
    fun getSpinsReward(): Call<String>

    @GET("Coins.txt")
    fun getCoinsReward(): Call<String>
}