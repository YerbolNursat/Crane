package com.example.crane.di

import com.example.crane.ui.choose_crane.ChooseCraneViewModel
import com.example.crane.ui.crane_full_info.CraneFullInfoViewModel
import com.example.crane.ui.crane_info.CraneInfoViewModel
import com.example.crane.ui.crane_metal_constructor.CraneMetalConstructorInfoViewModel
import com.example.crane.ui.crane_types.CraneTypesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { CraneTypesViewModel() }
    viewModel { CraneInfoViewModel(createQuestionUseCase = get(),getAllQuestionsUseCase = get()) }
    viewModel { CraneFullInfoViewModel() }
    viewModel { ChooseCraneViewModel() }
    viewModel { CraneMetalConstructorInfoViewModel() }
}
