package com.jyka.logic.pieces

class Queen(Color: PieceColor) : Piece(Color) {

    override fun getPossibleMoves(x: Int, y: Int): List<Pair<Int, Int>> {
        val result = mutableListOf(x to y)
        val eatList = mutableListOf(x to y)
        val board = this.getBoard()!!
        for ((directionX, directionY) in listOf(1 to 1, 1 to -1, -1 to -1, -1 to 1)) {
            var newX = x + directionX
            var newY = y + directionY
            while (inDesk(newX to newY) && isOpposite(board[newX, newY])) {

                if ((board[newX, newY]) is Piece) {
                    if (isOpposite(board[newX, newY]) && inDesk(newX + directionX to newY + directionY) &&
                           board[newX + directionX , newY + directionY] == null) {
                        newX += directionX
                        newY += directionY
                        while (inDesk(newX to newY) && board[newX, newY] == null) {
                            eatList.add(newX to newY)
                            newX += directionX
                            newY += directionY
                        }
                        break
                    } else break
                }
                result.add(Pair(newX, newY))
                newX += directionX
                newY += directionY
            }
        }
        if (eatList.size > 1) return eatList

        return result
    }

    override fun getPossibleCheckers(x: Int, y: Int, list: MutableList<Pair<Int,Int>>) {
        val board = this.getBoard()!!
        for ((directionX, directionY) in listOf(1 to 1, 1 to -1, -1 to -1, -1 to 1)) {
            var newX = x + directionX
            var newY = y + directionY
            while (inDesk(newX to newY) && isOpposite(board[newX, newY])) {
                if ((board[newX, newY]) is Piece) {
                    if (isOpposite(board[newX, newY]) && inDesk(newX + directionX to newY + directionY) &&
                            board[newX + directionX , newY + directionY] == null) {
                        list.add(x to y)
                        break
                    }
                }
                newX += directionX
                newY += directionY
            }
        }
    }

    override fun toString(): String = if (this.color == PieceColor.WHITE) {
        "white_queen"
    } else {
        "black_queen"
    }
}