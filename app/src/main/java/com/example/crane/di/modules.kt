package com.example.crane.di

import com.example.di.locale_module
import com.example.di.repositoriesModule

val modules = listOf(
    appModule,
    locale_module,
    useCaseModule,
    repositoriesModule
)