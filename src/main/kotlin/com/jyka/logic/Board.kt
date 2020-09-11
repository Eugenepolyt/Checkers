package com.jyka.logic

import com.jyka.logic.pieces.Piece
import com.jyka.logic.pieces.PieceColor

class Board  {
    private val data = List(8) { MutableList<Piece?>(8) { null } }

     operator fun get(x: Int, y: Int): Piece? = data[x][y]

     operator fun set(x: Int, y: Int, value: Piece?) {
        require(x in 0..7 && y in 0..7)
        data[x][y] = value
        if (value is Piece) {
            value.setBoard(this)
        }
    }

    fun clear() {
        for (i in 0..7) {
            for (j in 0..7) {
                data[i][j] = null
            }
        }
    }



    fun getPossibleMoves(x: Int, y: Int): List<Pair<Int, Int>> {

        return get(x,y)!!.getPossibleMoves(x,y)
    }

    fun getPossibleCheckers(x: Int, y: Int, list: MutableList<Pair<Int,Int>>) {

        get(x,y)!!.getPossibleCheckers(x,y,list)
    }


    fun checkLooser(): PieceColor? {
        var black = 0
        var white = 0
        for(i in 0..7) {
            for (j in 0..7) {
                if (this[i, j] is Piece && this[i, j]!!.color == PieceColor.BLACK) {
                    black++
                } else if (this[i, j] is Piece && this[i, j]!!.color == PieceColor.WHITE) {
                    white++
                }
            }
        }

        if (black == 0) return PieceColor.WHITE
        else if (white == 0) return  PieceColor.BLACK

        return null
   }
}
