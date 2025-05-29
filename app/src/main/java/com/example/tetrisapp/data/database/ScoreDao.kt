package com.example.tetrisapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.tetrisapp.data.model.Score

@Dao
interface ScoreDao {

    @Insert
    suspend fun insert(score: Score)

    @Query("SELECT * FROM scores ORDER BY points DESC LIMIT 10")
    suspend fun getTopScores(): List<Score>
}
