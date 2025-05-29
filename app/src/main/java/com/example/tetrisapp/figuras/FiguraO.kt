package com.example.tetrisapp.figuras

class FiguraO(x: Int, y: Int) : Figura(x, y) {
    override val shape = arrayOf(
        arrayOf(1, 1),
        arrayOf(1, 1)
    )
    override fun rotar() {}
}