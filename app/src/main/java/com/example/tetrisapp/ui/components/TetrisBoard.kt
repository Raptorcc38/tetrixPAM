package com.example.tetrisapp.ui.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.tetrisapp.figuras.Figura

class TetrisBoard(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val rows = 20
    private val cols = 10
    private val board = Array(rows) { Array(cols) { 0 } }

    var currentFigure: Figura? = null

    private val paint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Mantener relación 1:2 (10x20)
        val viewWidth = width.toFloat()
        val viewHeight = height.toFloat()
        val boardAspect = cols.toFloat() / rows.toFloat() // 0.5 para 10x20

        val targetWidth: Float
        val targetHeight: Float

        if (viewWidth / viewHeight > boardAspect) {
            // View es más ancho que el tablero
            targetHeight = viewHeight
            targetWidth = viewHeight * boardAspect
        } else {
            // View es más alto que el tablero
            targetWidth = viewWidth
            targetHeight = viewWidth / boardAspect
        }

        val cellSize = targetWidth / cols
        val offsetX = (viewWidth - targetWidth) / 2f
        val offsetY = (viewHeight - targetHeight) / 2f

        // Dibuja el fondo solo en el área del tablero real
        paint.color = Color.rgb(40, 40, 40)
        canvas.drawRect(
            offsetX, offsetY,
            offsetX + targetWidth, offsetY + targetHeight, paint
        )

        // Dibuja líneas de la cuadrícula
        paint.color = Color.parseColor("#666666")
        paint.strokeWidth = cellSize / 15f
        for (i in 0..rows) {
            canvas.drawLine(
                offsetX,
                offsetY + i * cellSize,
                offsetX + cols * cellSize,
                offsetY + i * cellSize,
                paint
            )
        }
        for (j in 0..cols) {
            canvas.drawLine(
                offsetX + j * cellSize,
                offsetY,
                offsetX + j * cellSize,
                offsetY + rows * cellSize,
                paint
            )
        }

        // Dibuja piezas ya colocadas (azul)
        paint.color = Color.BLUE
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                if (board[row][col] == 1) {
                    canvas.drawRect(
                        offsetX + col * cellSize + 3,
                        offsetY + row * cellSize + 3,
                        offsetX + (col + 1) * cellSize - 3,
                        offsetY + (row + 1) * cellSize - 3,
                        paint
                    )
                }
            }
        }

        // Dibuja la figura actual (rojo)
        currentFigure?.let { figura ->
            paint.color = Color.RED
            figura.shape.forEachIndexed { i, rowArray ->
                rowArray.forEachIndexed { j, cell ->
                    if (cell == 1) {
                        val x = figura.x + j
                        val y = figura.y + i
                        if (y in 0 until rows && x in 0 until cols) {
                            canvas.drawRect(
                                offsetX + x * cellSize + 3,
                                offsetY + y * cellSize + 3,
                                offsetX + (x + 1) * cellSize - 3,
                                offsetY + (y + 1) * cellSize - 3,
                                paint
                            )
                        }
                    }
                }
            }
        }
    }

    fun isCollision(): Boolean {
        currentFigure?.let { figura ->
            figura.shape.forEachIndexed { i, rowArray ->
                rowArray.forEachIndexed { j, cell ->
                    if (cell == 1) {
                        val x = figura.x + j
                        val y = figura.y + i
                        if (x < 0 || x >= cols || y >= rows || (y >= 0 && board[y][x] == 1)) {
                            return true
                        }
                    }
                }
            }
        }
        return false
    }

    fun mergeFigure() {
        currentFigure?.let { figura ->
            figura.shape.forEachIndexed { i, rowArray ->
                rowArray.forEachIndexed { j, cell ->
                    if (cell == 1) {
                        val x = figura.x + j
                        val y = figura.y + i
                        if (y in 0 until rows && x in 0 until cols) {
                            board[y][x] = 1
                        }
                    }
                }
            }
        }
    }

    fun clearFullLines(): Int {
        val linesToClear = mutableListOf<Int>()
        for (y in board.indices) {
            if (board[y].all { it == 1 }) {
                linesToClear.add(y)
            }
        }
        if (linesToClear.isNotEmpty()) {
            for (line in linesToClear) {
                for (row in line downTo 1) {
                    board[row] = board[row - 1].copyOf()
                }
                board[0] = Array(cols) { 0 }
            }
        }
        return linesToClear.size
    }

    fun clearBoard() {
        for (row in board) {
            row.fill(0)
        }
        invalidate()
    }

    fun isGameOver(): Boolean {
        return board[0].any { it == 1 }
    }
}
