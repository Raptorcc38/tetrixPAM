package com.example.tetrisapp.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tetrisapp.data.database.AppDatabase
import com.example.tetrisapp.data.model.Score
import kotlinx.coroutines.launch

class TetrisViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val scoreDao = db.scoreDao()

    fun guardarPuntaje(playerName: String, points: Int) {
        viewModelScope.launch {
            val score = Score(playerName = playerName, points = points)
            scoreDao.insert(score)
        }
    }

    suspend fun obtenerPuntajes(): List<Score> {
        return scoreDao.getTopScores()
    }
}
