package com.azzuresolutionscmrewards.azzuresolutionsc.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.azzuresolutionscmrewards.azzuresolutionsc.model.RewardResponse
import com.azzuresolutionscmrewards.azzuresolutionsc.retrofit.APIServices
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class RewardRepositoryImpl(private val retrofitService: APIServices, val context: Context) :
    RewardRepository {

    override fun getCoins(): MutableLiveData<ArrayList<RewardResponse>> {
        val rewardResponse = MutableLiveData<ArrayList<RewardResponse>>()
        retrofitService.getCoinsReward()
            .enqueue(object : Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.body() != null) {
                        val updatedResponse = response.body()!!.replace("],", "]")

                        val gson = Gson()
                        val itemType = object : TypeToken<ArrayList<RewardResponse>>() {}.type
                        val rewardResponseList =
                            gson.fromJson<ArrayList<RewardResponse>>(updatedResponse, itemType)
                        rewardResponse.postValue(rewardResponseList)
                    }
                }

                override fun onFailure(
                    call: Call<String>,
                    t: Throwable
                ) {
                    t.localizedMessage?.let { Log.d("API---", it) }
                }
            })
        return rewardResponse
    }

    override fun getSpins(): MutableLiveData<ArrayList<RewardResponse>> {
        val responseData = MutableLiveData<ArrayList<RewardResponse>>()
        retrofitService.getSpinsReward()
            .enqueue(object : Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.body() != null) {
                        val updatedResponse = response.body()!!.replace("],", "]")

                        val gson = Gson()
                        val itemType = object : TypeToken<ArrayList<RewardResponse>>() {}.type
                        val rewardResponseList =
                            gson.fromJson<ArrayList<RewardResponse>>(updatedResponse, itemType)
                        responseData.postValue(rewardResponseList)
                    }
                }

                override fun onFailure(
                    call: Call<String>,
                    t: Throwable
                ) {
                    t.localizedMessage?.let { Log.d("API---", it) }
                }
            })
        return responseData
    }


}