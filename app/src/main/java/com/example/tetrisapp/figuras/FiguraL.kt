package com.example.tetrisapp.figuras
import com.example.tetrisapp.utils.rotateMatrix

class FiguraL(x: Int, y: Int) : Figura(x, y) {
    override var shape = arrayOf(
        arrayOf(0, 0, 1),
        arrayOf(1, 1, 1)
    )
    override fun rotar() {
        shape = shape.rotateMatrix()
    }
}