package entity

/**
 * Entity to represent a player in the game "Swim".
 *
 * @param name Player's name that must not be empty
 * @param score Player's current score
 * @param hasKnocked shows whether the current player has already knocked
 */

class Player {
    private var name: String = ""
    private var score: Double = 0.0
    private var hasKnocked: Boolean = false
    private var playerCards: MutableList<Card> = emptyList<Card>().toMutableList()

    constructor(name: String, playerCards: MutableList<Card>) {
        if (name.isEmpty()) throw IllegalArgumentException("Player's name must be entered")
        if (playerCards.size != 3) throw IllegalArgumentException("Player must have exact 3 cards")

        this.name = name
        this.playerCards = playerCards
    }

    /**
     * Get name
     *
     * @return
     */
    fun getName(): String {
        return name
    }

    fun getScore(): Double {
        return score
    }

    /**
     * Set score
     *
     * @param newScore
     * @return
     */
    fun setScore(newScore: Double): Double {
        score = newScore
        return score
    }

    fun getHasKnocked(): Boolean {
        return hasKnocked
    }

    fun setHasKnocked(): Boolean {
        hasKnocked = true
        return hasKnocked
    }

    fun getPlayerCards(): MutableList<Card> {
        return playerCards
    }

    fun setPlayerCards(playerCards: MutableList<Card>) {
        if (playerCards.size != 3) {
            throw IllegalArgumentException("Player muss have exact 3 cards")
        }
        this.playerCards = playerCards
    }

}