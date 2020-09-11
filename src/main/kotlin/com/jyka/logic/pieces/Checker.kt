package com.jyka.logic.pieces


class Checker(Color: PieceColor) : Piece(Color) {


    override fun getPossibleMoves(x: Int, y: Int): List<Pair<Int, Int>> {
        val result = mutableListOf(x to y)
        val board = this.getBoard()!!


        val mapOfEat = mapOf(
                (-1 to 1) to (-2 to 2),
                (-1 to -1) to (-2 to -2),
                (1 to 1) to (2 to 2),
                (1 to -1) to (2 to -2)
        )

        for ((enemy, clear) in mapOfEat) {
            if (inDesk(x + enemy.first to y + enemy.second) && board[x + enemy.first, y + enemy.second] != null
                    && isOpposite(board[x + enemy.first, y + enemy.second])) {
                if (inDesk(x + clear.first to y + clear.second)
                        && board[x + clear.first, y + clear.second] == null) {
                    result.add(x + clear.first to y + clear.second)

                }
            }
        }


            if (result.size == 1) {
                if (color == PieceColor.BLACK) {
                    if (inDesk(x + 1 to y + 1) && board[x + 1, y + 1] == null) {
                        result.add(Pair(x + 1, y + 1))
                    }

                    if (inDesk(x + 1 to y - 1) && board[x + 1, y - 1] == null) {
                        result.add(Pair(x + 1, y - 1))
                    }
                } else {

                    if (inDesk(x - 1 to y + 1) && board[x - 1, y + 1] == null) {
                        result.add(Pair(x - 1, y + 1))
                    }

                    if (inDesk(x - 1 to y - 1) && board[x - 1, y - 1] == null) {
                        result.add(Pair(x - 1, y - 1))
                    }
                }
            }




        return result
    }

    override fun getPossibleCheckers(x: Int, y: Int, list: MutableList<Pair<Int,Int>>) {
            val board = this.getBoard()!!

            val mapOfEat = mapOf(
                    (-1 to 1) to (-2 to 2),
                    (-1 to -1) to (-2 to -2),
                    (1 to 1) to (2 to 2),
                    (1 to -1) to (2 to -2)
            )


             for ((enemy, clear) in mapOfEat) {
                if (inDesk(x + enemy.first to y + enemy.second) && board[x + enemy.first, y + enemy.second] != null
                        && isOpposite(board[x + enemy.first, y + enemy.second])
                        && inDesk(x + clear.first to y + clear.second)
                        && board[x + clear.first, y + clear.second] == null) list.add(x to y)
             }
        }


    override fun toString(): String = if (this.color == PieceColor.WHITE) {
        "white_checker"
    } else {
        "black_checker"
    }
}

