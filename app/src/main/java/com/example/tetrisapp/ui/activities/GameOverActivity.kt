package com.example.tetrisapp.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.apprecetas.databinding.ActivityGameOverBinding
import com.example.tetrisapp.ui.viewmodels.TetrisViewModel

class GameOverActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameOverBinding
    private val viewModel: TetrisViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameOverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val points = intent.getIntExtra("points", 0)

        binding.btnSaveScore.setOnClickListener {
            val playerName = binding.etPlayerName.text.toString()
            viewModel.guardarPuntaje(playerName, points)
            startActivity(Intent(this, HighScoresActivity::class.java))
            finish()
        }
    }
}
