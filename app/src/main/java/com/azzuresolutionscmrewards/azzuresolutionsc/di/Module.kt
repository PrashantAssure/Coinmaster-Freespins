package com.azzuresolutionscmrewards.azzuresolutionsc.di

import com.azzuresolutionscmrewards.azzuresolutionsc.repository.RewardRepository
import com.azzuresolutionscmrewards.azzuresolutionsc.repository.RewardRepositoryImpl
import com.azzuresolutionscmrewards.azzuresolutionsc.viewmodel.RewardViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val repositoryModule = module {
    single<RewardRepository> { RewardRepositoryImpl(get(), androidContext()) }
}

val viewModelModule = module {
    viewModel { RewardViewModel(get()) }

}



