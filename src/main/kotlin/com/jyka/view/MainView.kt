package com.jyka.view

import com.jyka.logic.Board
import com.jyka.logic.pieces.*
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.BorderPane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.text.Font
import tornadofx.*
import kotlin.math.abs


class MainView : View("Checkers") {

    override val root = BorderPane()

    private var checkEat = false

    private val gameBoard = Board()

    private val stack = MutableList(8) { MutableList(8) { Rectangle() to ImageView() } }

    private var moves = mutableListOf<Pair<Int, Int>>()

    private var shouldBeat = mutableListOf<Pair<Int, Int>>()

    private var pieceChosen = false

    private var turn = PieceColor.WHITE

    private var statusText = text("")

    init {
        primaryStage.isResizable = false

        with(root) {
            top {
                vbox {
                    menubar {
                        menu("Game") {
                            item("Restart").action {
                                restartGame()
                            }
                            separator()
                            item("Exit").action {
                                this@MainView.close()
                            }
                        }
                    }
                    borderpane {
                        center {
                            statusText = text("White's turn") {
                                fill = Color.BLACK
                                font = Font(20.0)
                            }
                        }
                    }
                }
            }
            center {
                gridpane {
                    for (row in 0..7) {
                        row {
                            for (column in 0..7) {
                                stackpane {
                                    val rectangle = rectangle {
                                        fill = if ((row + column) % 2 == 0) {
                                            Color.rgb(240, 217, 181)
                                        } else {
                                            Color.rgb(181, 136, 99)
                                        }
                                        width = 100.0
                                        height = 100.0
                                    }
                                    val image = imageview {
                                        image = null
                                    }
                                    stack[row][column] = rectangle to image
                                    setOnMouseClicked {
                                        handleClick(row, column)
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
        spawnAllPieces()
    }

    private fun handleClick(row: Int, column: Int) {

        if (pieceChosen && row to column in moves.drop(1)) {
            val oldRow = moves.first().first
            val oldColumn = moves.first().second
            moves.removeAt(0)
            movePiece(oldRow, oldColumn, row, column)
            gameBoard.getPossibleCheckers(row,column,shouldBeat)
            disableHint()
            moves.clear()
            pieceChosen = false
            if (shouldBeat.isNotEmpty() && checkEat) {
                checkEat = false
                return
            }
            turn = turn.opposite()
            checkEat = false
            updateStatus()
            return
        }

         if (pieceChosen) {
             disableHint()
             moves.clear()
             pieceChosen = false
             return
         }

        if (!pieceChosen && gameBoard[row, column] is Piece && gameBoard[row, column]!!.color == turn) {
            // Для проверки есть ли шашка,которая может убить другую шашку
            for(i in 0..7) {
                for (j in 0..7) {
                    if (gameBoard[i, j] is Piece && gameBoard[i, j]!!.color == turn) {
                        gameBoard.getPossibleCheckers(i,j,shouldBeat)
                    }
                }
            }
            if (shouldBeat.isNotEmpty() && row to column in shouldBeat) {
                pieceChosen = true
                moves = gameBoard.getPossibleMoves(row, column).toMutableList()
                checkEat = true
                enableHint()
            } else if (shouldBeat.isEmpty()) {
                pieceChosen = true
                moves = gameBoard.getPossibleMoves(row, column).toMutableList()
                enableHint()
            }
            shouldBeat.clear()
            return
        }

        }


    private fun updateStatus() {
        statusText.apply {
            text = if (turn == PieceColor.WHITE) {
                "White's turn"
            } else {
                "Black's turn"
            }
        }
        if (gameBoard.checkLooser() != null) {
            statusText.apply {
               text = if (gameBoard.checkLooser()!!.opposite() == PieceColor.WHITE) {
                   "White win"
                } else {
                   "Black win"
                }
            }
           WinnerDialog(gameBoard.checkLooser()!!.opposite()).showAndWait()
        }
    }


    private fun movePiece(row: Int, column: Int, newRow: Int, newColumn: Int) {



        gameBoard[newRow, newColumn] = gameBoard[row, column]
        gameBoard[row, column] = null
        stack[newRow][newColumn].second.apply {
            image = stack[row][column].second.image
        }
        stack[row][column].second.apply {
            image = null
        }


        if (abs(newRow - row) > 1) {
            var directionX = row
            var directionY = column
            if (row < newRow && column > newColumn) {
                while (directionX != newRow - 1) {
                    directionX++
                    directionY--
                    if (gameBoard[directionX, directionY] is Piece) {
                        despairPiece(directionX,directionY)
                    }
                }
            }  else if (row > newRow && column < newColumn)  {
                while (directionX != newRow + 1) {
                    directionX--
                    directionY++
                    if (gameBoard[directionX, directionY] is Piece) {
                        despairPiece(directionX,directionY)
                    }
                }
            } else if (row > newRow && column > newColumn) {
                while (directionX != newRow + 1) {
                    directionX--
                    directionY--
                    if (gameBoard[directionX, directionY] is Piece) {
                        despairPiece(directionX,directionY)
                    }
                }
            } else {
                while (directionX != newRow - 1) {
                    directionX++
                    directionY++
                    if (gameBoard[directionX, directionY] is Piece) {
                        despairPiece(directionX,directionY)
                    }
                }
            }
        }

      if (gameBoard[newRow, newColumn]!!.color == PieceColor.BLACK && newRow == 7) {
            spawnPiece(Queen(PieceColor.BLACK), newRow, newColumn)
        } else {
            if (gameBoard[newRow, newColumn]!!.color == PieceColor.WHITE && newRow == 0) {
                spawnPiece(Queen(PieceColor.WHITE), newRow, newColumn)
            }
        }
    }


    private fun restartGame() {
        gameBoard.clear()
        turn = PieceColor.WHITE
        for (i in 0..7) {
            for (j in 0..7) {
                stack[i][j].second.apply {
                    image = null
                }
            }
        }
        spawnAllPieces()
        disableHint()
    }

    private fun enableHint() {
        for ((x, y) in moves.drop(1)) {
            stack[x][y].first.apply {
                fill = if ((x + y) % 2 == 0) {
                    Color.rgb(175, 237, 173)
                } else {
                    Color.rgb(109, 181, 99)
                }
            }
        }
    }

    private fun disableHint() {
        for ((x, y) in moves) {
            stack[x][y].first.apply {
                fill = if ((x + y) % 2 == 0) {
                    Color.rgb(240, 217, 181)
                } else {
                    Color.rgb(181, 136, 99)
                }
            }
        }
    }

    private fun spawnAllPieces() {
        for (row in 0..2) {
            for (column in 0..7) {
                if ((row % 2 == 0) && (column % 2 == 1)) spawnPiece(Checker(PieceColor.BLACK), row, column)
                else if ((row % 2 == 1) && (column % 2 == 0)) spawnPiece(Checker(PieceColor.BLACK), row, column)
            }
        }

        for (row in 5..7) {
            for (column in 0..7) {
                if ((row % 2 == 0) && (column % 2 == 1)) spawnPiece(Checker(PieceColor.WHITE), row, column)
                else if ((row % 2 == 1) && (column % 2 == 0)) spawnPiece(Checker(PieceColor.WHITE), row, column)
            }
        }
    }

    private fun despairPiece(x: Int, y: Int) {
        gameBoard[x, y] = null
        stack[x][y].second.apply {
            image = null
        }
    }


    private fun spawnPiece(piece: Piece, x: Int, y: Int) {
        gameBoard[x, y] = piece
        stack[x][y].second.apply {
            image = Image("file:src\\main\\resources\\${piece}.png")
        }
    }

}


