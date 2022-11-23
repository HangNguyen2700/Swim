package service

import entity.Card

/**
 * Player service class provides the logic for the 4 possible actions a player
 * can take in Swim
 *
 * @property rootService
 * @constructor Create empty Player service
 */
class PlayerService(private var rootService: RootService) : AbstractRefreshingService() {

    private var gameService = rootService.gameService


    /**
     * Update score of current player and is called after current player's cards were updated
     *
     * If 3 cards have the same suit, current player gets 30.5 points
     * Otherwise if current player has only 2 cards with the same suit, player's score must be the maximum points of 2 cards that
     * have the same suit and the rest card
     * Otherwise if current player has 3 cards with 3 different suits, player's score is the sum of 3 card values
     *
     * @return calculated points of current player
     */
    private fun updateScore() : Double {
        var currentGame = rootService.currentGame
        var currentPlayer = currentGame!!.getCurrentPlayer()
        var card0 = currentPlayer.getPlayerCards()[0]
        var card1 = currentPlayer.getPlayerCards()[1]
        var card2 = currentPlayer.getPlayerCards()[2]
        var sum : Double = 0.0

        if(card0.equalsValue(card1) && card0.equalsValue(card2)){
            return 30.5
        }

        if(card0.equalsSuit(card1) && card0.equalsSuit(card2)) {
            return card0.toDoubleValue() + card1.toDoubleValue() + card2.toDoubleValue()
        }

        if(card0.equalsSuit(card1)){
            sum = card0.toDoubleValue() + card1.toDoubleValue()
            return maxOf(sum, card2.toDoubleValue())
        }

        if(card0.equalsSuit(card2)){
            sum = card0.toDoubleValue() + card2.toDoubleValue()
            return maxOf(sum, card1.toDoubleValue())
        }

        if(card1.equalsSuit(card2)){
            sum = card1.toDoubleValue() + card2.toDoubleValue()
            return maxOf(sum, card0.toDoubleValue())
        }

        onAllRefreshables { this.refreshAfterUpdateScore() }
        return maxOf(card0.toDoubleValue(), card1.toDoubleValue(), card2.toDoubleValue())
    }

    /**
     * Exchange one player's card and is auxiliary method for [exchangeOneCard] and [exchangeAllCards]
     *
     * @param playerCardIndex index of current player's card, which will be exchanged
     * @param openCardIndex index of card in the middle stack, which will be exchanged
     */
    private fun exchangePlayerCard(playerCardIndex: Int, openCardIndex: Int) {
        var currentGame = rootService.currentGame
        var currentPlayer = currentGame!!.getCurrentPlayer()
        var playerCard = currentPlayer.getPlayerCards()[playerCardIndex]
        var openCard = currentGame.getOpenCards()[openCardIndex]

        currentPlayer.getPlayerCards()[playerCardIndex] = openCard
        currentGame.getOpenCards()[openCardIndex] = playerCard
    }

    /**
     * Check if current player is valid and exchange one current player's card with one in the middle stack
     *
     * @param playerCardIndex current player's card index that is chosen to exchange
     * @param openCardIndex card in middle stack that is chosen to exchange
     */
    fun exchangeOneCard(playerCardIndex: Int, openCardIndex: Int) {
        var currentGame = rootService.currentGame
        var currentPlayer = currentGame!!.getCurrentPlayer()
        exchangePlayerCard(playerCardIndex, openCardIndex)

        currentGame.resetPassCounter()
        currentPlayer.setScore(updateScore())

        onAllRefreshables { this.refreshAfterPlayerAction() }
    }

    /**
     * Check if current player is valid and exchange all current player's cards with cards in the middle stack
     *
     */
    fun exchangeAllCards() {
        var currentGame = rootService.currentGame
        var currentPlayer = currentGame!!.getCurrentPlayer()

        currentPlayer.getPlayerCards().forEachIndexed { index, _ -> exchangePlayerCard(index, index) }

        currentPlayer.setScore(updateScore())
        currentGame.resetPassCounter()

        onAllRefreshables { this.refreshAfterPlayerAction() }
    }

    /**
     * Check if it has more than 2 cards in new stack and replace 3 cards in middle stack with 3 cards in new stack.
     * Is used in [nextPlayer]
     *
     * @param openCards cards in middle stack that are no longer used
     * @param unusedCards cards in new stack that will be put in middle stack and will be removed from new stack
     */
    private fun replaceOpenCardsWithUnusedCards(openCards: MutableList<Card>, unusedCards: MutableList<Card>) {
        if (unusedCards.size >= 3) {
            openCards.forEachIndexed { index: Int, _ -> openCards[index] = unusedCards.removeAt(0) }
        }
    }

    /**
     * current player just passes and increases passCounter by 1
     *
     */
    fun pass() {
        var currentGame = rootService.currentGame
        var currentPlayer = currentGame!!.getCurrentPlayer()

        currentGame.increasePassCounter()
        currentPlayer.setScore(updateScore())

        if (currentGame.getPassCounter() == currentGame.getPlayers().size) {
            if (currentGame.getUnusedCards().size < 3) {
                currentGame.resetPassCounter()
                gameService.endGame()
                return
            }
            replaceOpenCardsWithUnusedCards(currentGame.getOpenCards(), currentGame.getUnusedCards())
            currentGame.resetPassCounter()
        }

        onAllRefreshables { this.refreshAfterPlayerAction() }
    }

    /**
     * Check if current player is valid, then if current player has already knocked, end game
     * when not, knock now current player and set that he/she has already knocked
     *
     */
    fun knock() {
        var currentGame = rootService.currentGame
        var currentPlayer = currentGame!!.getCurrentPlayer()

        if (currentPlayer.getHasKnocked()) {
            gameService.endGame()
            return
        }
        currentPlayer.setHasKnocked()
        currentGame.resetPassCounter()
        currentPlayer.setScore(updateScore())

        onAllRefreshables { this.refreshAfterPlayerAction() }
    }


}