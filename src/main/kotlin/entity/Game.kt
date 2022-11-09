package entity

/**
 * Entity class represents a game state of "Swim".
 *
 * @param passCounter count number of players who continuously pass and is initialized with 0
 * @param openCards list of 3 cards in the middle that will initially be exchanged
 * @param unusedCards list of new cards, must be greater than or equal to 0
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
        var index = (players.indexOf(currentPlayer) + 1) / players.size
        currentPlayer = players[index]
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

    fun getUnusedCards(): MutableList<Card> {
        return unusedCards
    }
}