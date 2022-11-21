package view

import entity.Player

interface Refreshable {
    fun refreshAfterStartGame() {}
    fun refreshAfterPlayerAction() {}
    fun refreshAfterEndGame(players : MutableList<Player>){}
    fun refreshAfterNextPlayer(){}
    fun refreshAfterUpdateScore(){}
}