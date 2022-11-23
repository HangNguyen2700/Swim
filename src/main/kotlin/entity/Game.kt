package entity

import service.AbstractRefreshingService

/**
 *  Entity class represents a game state of "Swim".
 *
 * @constructor Create empty Game
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
     * Get players
     *
     * @return list of all players
     */
    fun getPlayers(): MutableList<Player> {
        return players
    }

    /**
     * Get current player
     *
     * @return current player
     */
    fun getCurrentPlayer(): Player {
        return currentPlayer
    }

    /**
     * Set current player
     *
     * @param player is current player that will be set
     */
    fun setCurrentPlayer(player : Player){
        this.currentPlayer = player
    }

    /**
     * Reset current player to the first player
     *
     */
    fun resetCurrentPlayer(){
        this.currentPlayer = players.first()
    }

    /**
     * Get the next player
     *
     * @return the next player in line
     */
    fun nextPlayer(): Player {
        var index = (players.indexOf(currentPlayer) + 1) % players.size
        return players[index]
    }

    /**
     * Get pass counter
     *
     * @return current number of continuous players, that have passed
     */
    fun getPassCounter(): Int {
        return passCounter
    }

    /**
     * Increase pass counter by 1
     *
     * @return [passCounter]
     */
    fun increasePassCounter(): Int {
        passCounter++
        return passCounter
    }

    /**
     * Reset pass counter to 0
     *
     * @return [passCounter]
     */
    fun resetPassCounter(): Int {
        passCounter = 0
        return passCounter
    }

    /**
     * Get open cards
     *
     * @return list of current open cards
     */
    fun getOpenCards(): MutableList<Card> {
        return openCards
    }


    /**
     * Get unused cards
     *
     * @return list of current unused cards
     */
    fun getUnusedCards(): MutableList<Card> {
        return unusedCards
    }
}