package com.jyka.logic.pieces

import com.jyka.logic.ChessBoard

abstract class Piece(Color: PieceColor) {
    val color: PieceColor = Color
    private var board: ChessBoard? = null

    fun setBoard(board: ChessBoard) {
        this.board = board
    }

    fun getBoard() = board

    fun inDesk(pair: Pair<Int,Int>) = pair.first in 0..7 && pair.second in 0..7

    fun isOpposite(other: Piece?) = (other?.color ?: false) != this.color


    abstract fun getPossibleMovies(x: Int, y: Int): List<Pair<Int, Int>>

    abstract fun getPossibleCheckers(x: Int, y: Int, list: MutableList<Pair<Int,Int>>)


}