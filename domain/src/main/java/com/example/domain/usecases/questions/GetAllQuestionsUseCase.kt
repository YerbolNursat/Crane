package com.example.domain.usecases.questions

import com.example.domain.entities.Question
import com.example.domain.repositories.QuestionRepository
import com.example.domain.usecases.BaseUseCase

class GetAllQuestionsUseCase(
    private val questionRepository: QuestionRepository
) : BaseUseCase<List<Question>, Unit>() {

    override suspend fun run(params: Unit): List<Question> {
        return questionRepository.getAllQuestions()
    }

}