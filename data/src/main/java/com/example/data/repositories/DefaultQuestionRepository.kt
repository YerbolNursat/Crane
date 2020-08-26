package com.example.data.repositories

import androidx.lifecycle.LiveData
import com.example.data.daos.QuestionDao
import com.example.domain.entities.Question
import com.example.domain.repositories.QuestionRepository

class DefaultQuestionRepository (
    private val questionDao: QuestionDao
): QuestionRepository {
    override suspend fun insertQuestion(question: Question) {
        val item = question.let {
            com.example.data.entities.Question(
                name = it.name
            )
        }
        questionDao.insert(item)
    }

    override suspend fun getAllQuestions(): List<Question> {
       return questionDao.getAllQuestions().map {
           Question(
               name = it.name
           )
       }
    }

    override suspend fun deleteAllQuestions() {
        questionDao.deleteAllQuestions()
    }

}