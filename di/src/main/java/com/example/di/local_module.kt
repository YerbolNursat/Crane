package com.example.di

import com.example.data.DB
import com.example.data.common.SharedPreferencesSetting
import com.example.data.daos.QuestionDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val locale_module = module {
    single { SharedPreferencesSetting(androidContext()) }
    single { DB.getDatabase(androidContext()) }
    single { provideQuestionDao(get()) }

}
fun provideQuestionDao(db: DB): QuestionDao = db.getQuestion()
