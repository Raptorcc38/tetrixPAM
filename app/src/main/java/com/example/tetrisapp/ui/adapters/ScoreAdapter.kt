package com.example.tetrisapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.apprecetas.databinding.ItemScoreBinding
import com.example.tetrisapp.data.model.Score
import java.text.SimpleDateFormat
import java.util.*

class ScoreAdapter(private val scores: List<Score>) : RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>() {

    inner class ScoreViewHolder(private val binding: ItemScoreBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(score: Score) {
            binding.tvPlayerName.text = score.playerName
            binding.tvPoints.text = "${score.points} pts"
            binding.tvDate.text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(score.date))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val binding = ItemScoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScoreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        holder.bind(scores[position])
    }

    override fun getItemCount() = scores.size
}
