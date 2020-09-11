package com.jyka.view

import com.jyka.logic.pieces.PieceColor
import javafx.scene.control.ButtonBar
import javafx.scene.control.ButtonType
import javafx.scene.control.Dialog

class WinnerDialog(color: PieceColor) : Dialog<ButtonType>() {
    init {
        title = "Congratulation!!!"
        with(dialogPane) {
            headerText = "$color is win"
            buttonTypes.add(ButtonType("You can restart game in menu", ButtonBar.ButtonData.OK_DONE))
        }
    }
}
