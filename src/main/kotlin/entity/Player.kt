package entity

/**
 * Entity to represent a player in the game "Swim".
 *
 * @param name Player's name that must not be empty
 * @param score Player's current score that is initialized with 0
 * @param hasKnocked shows whether the current player has already knocked, is initialized with false (player hasn't
 * knocked yet)
 */

class Player (private var name : String,
              private var score : Double = 0.0,
              private var hasKnocked : Boolean = false
) {

}