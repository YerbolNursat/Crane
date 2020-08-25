package com.example.crane.di

import com.example.crane.utils.SharedPreferencesSetting
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val locale_module = module {
    single { SharedPreferencesSetting(androidContext()) }
}