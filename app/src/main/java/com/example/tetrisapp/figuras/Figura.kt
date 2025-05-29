
package com.example.tetrisapp.figuras

abstract class Figura(
    var x: Int,
    var y: Int
) {
    abstract val shape: Array<Array<Int>>

    fun moverIzquierda() { x -= 1 }
    fun moverDerecha() { x += 1 }
    fun moverAbajo() { y += 1 }

    abstract fun rotar()
}
