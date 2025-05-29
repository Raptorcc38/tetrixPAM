package com.example.tetrisapp.figuras
import com.example.tetrisapp.utils.rotateMatrix

class FiguraZ(x: Int, y: Int) : Figura(x, y) {
    override var shape = arrayOf(
        arrayOf(1, 1, 0),
        arrayOf(0, 1, 1)
    )
    override fun rotar() {
        shape = shape.rotateMatrix()
    }
}