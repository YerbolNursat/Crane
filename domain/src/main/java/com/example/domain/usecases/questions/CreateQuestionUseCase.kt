package com.example.domain.usecases.questions

import com.example.domain.entities.Question
import com.example.domain.repositories.QuestionRepository
import com.example.domain.usecases.BaseUseCase

class CreateQuestionUseCase(
    private val questionRepository: QuestionRepository
) : BaseUseCase<Unit, Question>() {

    override suspend fun run(params: Question) {
        questionRepository.insertQuestion(params)
    }

}