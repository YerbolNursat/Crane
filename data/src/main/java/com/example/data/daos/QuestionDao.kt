package com.example.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.entities.Question

@Dao
interface QuestionDao{

    @Query("DELETE FROM questions_table")
    fun deleteAllQuestions()

    @Query("SELECT * FROM questions_table")
    fun getAllQuestions(): List<Question>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(questions: Question)

}