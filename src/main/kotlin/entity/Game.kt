package entity

import service.AbstractRefreshingService

/**
 * Entity class represents a game state of "Swim".
 *
 */

class Game {
    private var players: MutableList<Player> = emptyList<Player>().toMutableList()
    private var passCounter: Int = 0
    private var openCards: MutableList<Card> = emptyList<Card>().toMutableList()
    private var unusedCards: MutableList<Card> = emptyList<Card>().toMutableList()
    private var currentPlayer: Player

    constructor(players: MutableList<Player>, openCards: MutableList<Card>, unusedCards: MutableList<Card>) {
        if (players.size < 2 || players.size > 4) throw IllegalArgumentException("Game can be played only by 2-4 players")
        this.players = players
        this.openCards = openCards
        this.unusedCards = unusedCards
        currentPlayer = players.first()
    }

    /**
     *
     */
    fun getPlayers(): MutableList<Player> {
        return players
    }

    fun getCurrentPlayer(): Player {
        return currentPlayer
    }

    fun nextPlayer(): Player {
        var index = (players.indexOf(currentPlayer) + 1) % players.size
        currentPlayer = players[index]
//        onAllRefreshables { this.refreshAfterNextPlayer() }
        return currentPlayer
    }

    fun getPassCounter(): Int {
        return passCounter
    }

    fun increasePassCounter(): Int {
        passCounter++
        return passCounter
    }

    fun resetPassCounter(): Int {
        passCounter = 0
        return passCounter
    }

    fun getOpenCards(): MutableList<Card> {
        return openCards
    }

//    fun setOpenCards(openCards: MutableList<Card>) {
////        if (openCards.size != 3) {
////            throw IllegalArgumentException("Player muss have exact 3 cards")
////        }
//        this.openCards.clear()
//        this.openCards.addAll(openCards)
////        for(i in 0..2) {
////            this.openCards.add(openCards[i])
////        }
//    }

    fun getUnusedCards(): MutableList<Card> {
        return unusedCards
    }
}