package entity

/**
 * Entity class represents a game state of "Swim".
 *
 * @param passCounter count number of players who continuously pass and is initialized with 0
 * @param openCards list of 3 cards in the middle that will initially be exchanged
 * @param unusedCards list of new cards, must be greater than or equal to 0
 */

class Game (var passCounter : Int,
            var openCards : List<Card>,
            var unusedCards : List<Card>
) {

}