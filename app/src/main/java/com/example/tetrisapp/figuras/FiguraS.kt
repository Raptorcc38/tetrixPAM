package com.example.tetrisapp.figuras
import com.example.tetrisapp.utils.rotateMatrix

class FiguraS(x: Int, y: Int) : Figura(x, y) {
    override var shape = arrayOf(
        arrayOf(0, 1, 1),
        arrayOf(1, 1, 0)
    )
    override fun rotar() {
        shape = shape.rotateMatrix()
    }
}