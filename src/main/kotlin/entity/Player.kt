package entity

/**
 * Entity to represent a player in the game "Swim".
 *
 * @param name Player's name that must not be empty
 * @param score Player's current score
 * @param hasKnocked shows whether the current player has already knocked
 */

class Player ( var name : String,
               var score : Double,
               var hasKnocked : Boolean
) {

}