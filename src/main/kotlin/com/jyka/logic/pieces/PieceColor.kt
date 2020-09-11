package com.jyka.logic.pieces

enum class PieceColor {
    WHITE,
    BLACK;

    fun opposite() = if (this == WHITE) {
        BLACK
    } else {
        WHITE
    }
}