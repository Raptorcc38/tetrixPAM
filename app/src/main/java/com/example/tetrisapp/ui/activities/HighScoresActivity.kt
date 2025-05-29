package com.example.tetrisapp.ui.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apprecetas.databinding.ActivityHighScoresBinding
import com.example.tetrisapp.ui.adapters.ScoreAdapter
import com.example.tetrisapp.ui.viewmodels.TetrisViewModel
import kotlinx.coroutines.launch

class HighScoresActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHighScoresBinding
    private val viewModel: TetrisViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHighScoresBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            val scores = viewModel.obtenerPuntajes()
            binding.rvScores.layoutManager = LinearLayoutManager(this@HighScoresActivity)
            binding.rvScores.adapter = ScoreAdapter(scores)
        }
    }
}
