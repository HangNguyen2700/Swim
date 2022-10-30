package entity

/**
 * Entity class represents a game state of "Swim".
 *
 * @param passCounter count number of players, who continuously pass
 * @param openCards list of cards in the middle that will initially be exchanged
 * @param unusedCards list of new cards
 */

class Game (var passCounter : Int,
            var openCards : List<Card>,
            var unusedCards : List<Card>
) {

}