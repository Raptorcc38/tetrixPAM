
package com.example.tetrisapp.figuras

class FiguraI(x: Int, y: Int) : Figura(x, y) {

    override var shape: Array<Array<Int>> = arrayOf(
        arrayOf(1, 1, 1, 1)
    )

    override fun rotar() {
        shape = if (shape.size == 1) {
            arrayOf(
                arrayOf(1),
                arrayOf(1),
                arrayOf(1),
                arrayOf(1)
            )
        } else {
            arrayOf(arrayOf(1, 1, 1, 1))
        }
    }
}
