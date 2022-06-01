package com.azzuresolutionscmrewards.azzuresolutionsc.repository

import androidx.lifecycle.MutableLiveData
import com.azzuresolutionscmrewards.azzuresolutionsc.model.RewardResponse

interface RewardRepository {
    fun getCoins(): MutableLiveData<ArrayList<RewardResponse>>
    fun getSpins(): MutableLiveData<ArrayList<RewardResponse>>

}