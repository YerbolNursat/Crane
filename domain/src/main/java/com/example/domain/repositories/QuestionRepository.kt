package com.example.domain.repositories

import androidx.lifecycle.LiveData
import com.example.domain.entities.Question

interface QuestionRepository {
    suspend fun insertQuestion(question: Question)
    suspend fun getAllQuestions():List<Question>
    suspend fun deleteAllQuestions()
}