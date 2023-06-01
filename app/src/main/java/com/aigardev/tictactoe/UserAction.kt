package com.aigardev.tictactoe

sealed class UserAction {
    object PlayAgainButtonClicked: UserAction()
    object ResetGameButtonClicked: UserAction()
    data class BoardTapped(val cellNum: Int): UserAction()
}
