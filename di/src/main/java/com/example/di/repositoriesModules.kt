package com.example.di

import com.example.data.repositories.DefaultQuestionRepository
import com.example.domain.repositories.QuestionRepository
import org.koin.dsl.module

val repositoriesModule = module {
    single<QuestionRepository> { DefaultQuestionRepository(questionDao = get()) }

}