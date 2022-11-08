package service

import entity.Card

class PlayerService(private var rootService: RootService) {

    private var gameService = rootService.gameService
    private var currentGame = rootService.currentGame
    private var currentPlayer = currentGame?.getCurrentPlayer()


    private fun updateScore() : Double {
        var card0 = currentPlayer!!.getPlayerCards()[0]
        var card1 = currentPlayer!!.getPlayerCards()[1]
        var card2 = currentPlayer!!.getPlayerCards()[2]
        var sum : Double = 0.0

        if(card0.equalsSuit(card1) && card0.equalsSuit(card2)) {
            return 30.5
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

        sum = card0.toDoubleValue() + card1.toDoubleValue() + card2.toDoubleValue()
        return sum
    }

    private fun exchangePlayerCard(playerCardIndex: Int, openCardIndex: Int) {
        var playerCard = currentPlayer!!.getPlayerCards()[playerCardIndex]
        var openCard = currentGame!!.getOpenCards()[openCardIndex]

        currentPlayer!!.getPlayerCards()[playerCardIndex] = openCard
        currentGame!!.getOpenCards()[openCardIndex] = playerCard
    }

    fun exchangeOneCard(playerCardIndex: Int, openCardIndex: Int) {
        checkNotNull(currentPlayer) { "No player currently playing." }
        exchangePlayerCard(playerCardIndex, openCardIndex)

        currentPlayer!!.setScore(updateScore())
//        currentGame!!.nextPlayer()
    }

    fun exchangeAllCards() {
        checkNotNull(currentPlayer) { "No player currently playing." }
        currentPlayer!!.getPlayerCards().forEachIndexed { index, _ -> exchangePlayerCard(index, index) }

        currentPlayer!!.setScore(updateScore())
//        currentGame!!.nextPlayer()
    }


    private fun replaceOpenCardsWithUnusedCards(openCards: MutableList<Card>, unusedCards: MutableList<Card>) {
        if (unusedCards.size >= 3) {
            openCards.forEachIndexed { index: Int, _ -> openCards[index] = unusedCards.removeAt(0) }
        }
    }

    fun pass() {
        checkNotNull(currentPlayer) { "No player currently playing." }

        if (currentGame!!.getOpenCards().size < 3) {
            gameService.endGame()
            return
        }

        if (currentGame!!.getPassCounter() == currentGame!!.getPlayers().size) {
            replaceOpenCardsWithUnusedCards(currentGame!!.getOpenCards(), currentGame!!.getUnusedCards())
            currentGame!!.resetPassCounter()
        }

        else {
            currentGame!!.increasePassCounter()
        }

        currentPlayer!!.setScore(updateScore())
//        currentGame!!.nextPlayer()
    }

    fun knock() {
        checkNotNull(currentPlayer) { "No player currently playing." }
        if (currentPlayer!!.getHasKnocked()) {
            gameService.endGame()
            return
        }
        currentPlayer!!.setHasKnocked()
//        currentGame?.nextPlayer()
    }



}