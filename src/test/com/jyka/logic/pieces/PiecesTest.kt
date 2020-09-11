package com.jyka.logic.pieces

import com.jyka.logic.ChessBoard
import junit.framework.TestCase
import org.junit.Test


class CheckerTest : TestCase() {
    var desk = ChessBoard()

    @Test
    fun testGetPossibleMoves() {
        desk[0, 1] = Checker(PieceColor.BLACK)
        assertEquals(listOf(1 to 2, 1 to 0), desk.getPossibleMovies(0, 1).drop(1))
        desk[0, 1] = Queen(PieceColor.BLACK)
        assertEquals(listOf(1 to 2, 2 to 3, 3 to 4, 4 to 5, 5 to 6, 6 to 7, 1 to 0), desk.getPossibleMovies(0, 1).drop(1))
        desk[0, 1] = Queen(PieceColor.BLACK)
        assertEquals(listOf(1 to 2, 2 to 3, 3 to 4, 4 to 5, 5 to 6, 6 to 7, 1 to 0), desk.getPossibleMovies(0, 1).drop(1))
    }

    @Test
    fun testPossibleMoviesToEat() {
        desk[6, 3] = Checker(PieceColor.WHITE)
        desk[5, 2] = Checker(PieceColor.BLACK)
        desk[5, 4] = Checker(PieceColor.BLACK)
        desk[1, 6] = Checker(PieceColor.BLACK)
        assertEquals(listOf(4 to 5, 4 to 1), desk.getPossibleMovies(6,3).drop(1))

        desk[6, 3] = Queen(PieceColor.WHITE)
        desk[5, 2] = Queen(PieceColor.BLACK)
        desk[5, 4] = Queen(PieceColor.BLACK)
        desk[1, 6] = Queen(PieceColor.BLACK)
        assertEquals(listOf(4 to 1, 3 to 0, 4 to 5, 3 to 6, 2 to 7), desk.getPossibleMovies(6,3).drop(1))

    }

    @Test
    fun testisOpposite() {
        desk[7, 7] = Checker(PieceColor.WHITE)
        desk[5, 3] = Checker(PieceColor.BLACK)
        assertEquals(true, desk[7, 7]!!.isOpposite(desk[5, 3]))
        assertEquals(true, desk[5, 3]!!.isOpposite(desk[7, 7]))
        assertEquals(false, desk[5, 3]!!.isOpposite(desk[5, 3]))
    }

    @Test
    fun testTestToString() {
        assertEquals("black_checker", Checker(PieceColor.BLACK).toString())
        assertEquals("white_checker", Checker(PieceColor.WHITE).toString())
        assertEquals("black_queen", Queen(PieceColor.BLACK).toString())
        assertEquals("white_queen", Queen(PieceColor.WHITE).toString())
    }

}