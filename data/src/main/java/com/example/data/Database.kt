package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.converters.QuestionConverter
import com.example.data.daos.QuestionDao
import com.example.data.entities.CraneInfo

@Database(
    entities = [
        CraneInfo::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(QuestionConverter::class)
abstract class DB : RoomDatabase() {

    abstract fun getQuestion(): QuestionDao


    companion object {

        @Volatile
        private lateinit var INSTANCE: DB

        fun getDatabase(context: Context): DB {
            synchronized(DB::class.java) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    DB::class.java, "crane_db"
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }


            return INSTANCE
        }
    }
}