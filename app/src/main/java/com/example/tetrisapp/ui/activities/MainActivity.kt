package com.example.tetrisapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.example.apprecetas.databinding.ActivityMainBinding
import com.example.tetrisapp.figuras.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var handler: Handler
    private var delay: Long = 800L
    private var points = 0
    private var level = 1
    private var currentFigure: Figura? = null

    private val gameRunnable = object : Runnable {
        override fun run() {
            moveDown()
            handler.postDelayed(this, delay)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handler = Handler(Looper.getMainLooper())

        startNewGame()

        // Control táctil: izquierda/derecha
        binding.tetrisBoard.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> handleTouch(event.x, event.y)
            }
            true
        }

        // Botón BAJAR
        binding.btnDown.setOnClickListener { dropFigureToBottom() }
        // Botón ROTAR
        binding.btnRotate.setOnClickListener { rotateFigure() }
    }

    private fun startNewGame() {
        points = 0
        level = 1
        delay = 800L
        binding.tetrisBoard.clearBoard()
        spawnNewFigure()
        updatePoints(0)
        handler.postDelayed(gameRunnable, delay)
    }

    private fun spawnNewFigure() {
        val figures = listOf(
            FiguraI(5, 0), FiguraJ(5, 0), FiguraL(5, 0),
            FiguraO(5, 0), FiguraS(5, 0), FiguraT(5, 0), FiguraZ(5, 0)
        )
        currentFigure = figures[Random.nextInt(figures.size)]
        binding.tetrisBoard.currentFigure = currentFigure
    }

    private fun moveDown() {
        currentFigure?.moverAbajo()
        if (binding.tetrisBoard.isCollision()) {
            currentFigure?.y = currentFigure?.y?.minus(1) ?: 0
            binding.tetrisBoard.mergeFigure()
            val linesCleared = binding.tetrisBoard.clearFullLines()
            updatePoints(linesCleared)
            checkLevelUp()
            spawnNewFigure()
            if (binding.tetrisBoard.isGameOver()) {
                gameOver()
            }
        }
        binding.tetrisBoard.invalidate()
    }

    // Lado izquierdo/derecho de la pantalla: mueve la figura
    private fun handleTouch(x: Float, y: Float) {
        val width = binding.tetrisBoard.width
        when {
            x < width / 2 -> moveLeft()
            x > width / 2 -> moveRight()
        }
    }

    private fun moveLeft() {
        currentFigure?.moverIzquierda()
        if (binding.tetrisBoard.isCollision()) {
            currentFigure?.moverDerecha()
        }
        binding.tetrisBoard.invalidate()
    }

    private fun moveRight() {
        currentFigure?.moverDerecha()
        if (binding.tetrisBoard.isCollision()) {
            currentFigure?.moverIzquierda()
        }
        binding.tetrisBoard.invalidate()
    }

    private fun rotateFigure() {
        currentFigure?.rotar()
        if (binding.tetrisBoard.isCollision()) {
            repeat(3) { currentFigure?.rotar() } // revertir rotación
        }
        binding.tetrisBoard.invalidate()
    }

    private fun dropFigureToBottom() {
        while (!binding.tetrisBoard.isCollision()) {
            currentFigure?.moverAbajo()
        }
        currentFigure?.y = currentFigure?.y?.minus(1) ?: 0
        moveDown()
    }

    private fun updatePoints(linesCleared: Int) {
        if (linesCleared > 0) {
            // Combo: Si más de 1 línea, n × n × 10, si es solo una línea, 10
            points += if (linesCleared > 1) linesCleared * linesCleared * 10 else 10
        }
        binding.tvPoints.text = "Puntos: $points\nNivel: $level"
    }

    private fun checkLevelUp() {
        if (points >= level * 5000) {
            level++
            delay = (delay * 0.8).toLong()
            binding.tetrisBoard.clearBoard()
            binding.tvPoints.text = "Puntos: $points\nNivel: $level"
        }
    }

    private fun gameOver() {
        handler.removeCallbacks(gameRunnable)
        val intent = Intent(this, GameOverActivity::class.java).apply {
            putExtra("points", points)
        }
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        handler.removeCallbacks(gameRunnable)
        super.onDestroy()
    }
}
