package com.azzuresolutionscmrewards.azzuresolutionsc.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.azzuresolutionscmrewards.azzuresolutionsc.model.RewardResponse
import com.azzuresolutionscmrewards.azzuresolutionsc.repository.RewardRepository

class RewardViewModel(private val rewardRepository: RewardRepository) : ViewModel() {

    fun getSpins(): MutableLiveData<ArrayList<RewardResponse>> {
        return rewardRepository.getSpins()
    }

    fun getCoins(): MutableLiveData<ArrayList<RewardResponse>> {
        return rewardRepository.getCoins()
    }

}