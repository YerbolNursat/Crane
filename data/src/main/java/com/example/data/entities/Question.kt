package com.example.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "questions_table")
data class Question(
    @ColumnInfo(name = "name") val name: String

)