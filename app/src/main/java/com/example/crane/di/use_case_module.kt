package com.example.crane.di

import com.example.domain.usecases.questions.CreateQuestionUseCase
import com.example.domain.usecases.questions.DeleteAllQuestionsUseCase
import com.example.domain.usecases.questions.GetAllQuestionsUseCase
import org.koin.dsl.module

val useCaseModule = module {

    factory { CreateQuestionUseCase(questionRepository = get()) }
    factory { DeleteAllQuestionsUseCase(questionRepository = get()) }
    factory { GetAllQuestionsUseCase(questionRepository = get()) }

}