package entity

/**
 * Entity to represent a player in the game "Swim".
 *
 *  @constructor Create empty Player
 */

class Player {
    private var name: String = ""
    private var score: Double = 0.0
    private var hasKnocked: Boolean = false
    private var playerCards: MutableList<Card> = emptyList<Card>().toMutableList()

    constructor(name: String, playerCards: MutableList<Card>) {
        if (name.isEmpty()) throw IllegalArgumentException("Player's name must be entered")

        this.name = name
        this.playerCards = playerCards
    }

    /**
     * Get name
     *
     * @return name of player
     */
    fun getName(): String {
        return name
    }

    /**
     * Get score
     *
     * @return score of player
     */
    fun getScore(): Double {
        return score
    }

    /**
     * Set score that is updated right after player played card
     *
     * @param newScore score that is updated
     * @return score after being set
     */
    fun setScore(newScore: Double): Double {
        score = newScore
        return score
    }

    /**
     * Get has knocked for current player
     *
     * @return true if current player knocked, false if not
     */
    fun getHasKnocked(): Boolean {
        return hasKnocked
    }

    /**
     * Set has knocked for current player
     *
     * @return true if current player knocked, false if not
     */
    fun setHasKnocked(): Boolean {
        hasKnocked = true
        return hasKnocked
    }

    /**
     * Get player cards
     *
     * @return list of all players's cards
     */
    fun getPlayerCards(): MutableList<Card> {
        return playerCards
    }

}