package com.example.tetrisapp.utils


fun Array<Array<Int>>.rotateMatrix(): Array<Array<Int>> {
    val rows = this.size
    val cols = this[0].size
    val rotated = Array(cols) { Array(rows) { 0 } }

    for (i in 0 until rows) {
        for (j in 0 until cols) {
            rotated[j][rows - i - 1] = this[i][j]
        }
    }
    return rotated
}
