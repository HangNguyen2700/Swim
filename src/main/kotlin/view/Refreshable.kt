package view

interface Refreshable {
    fun refreshAfterStartGame() {}
    fun refreshAfterExchangeOneCard() {}
    fun refreshAfterExchangeAllCards() {}
    fun refreshAfterPass() {}
    fun refreshAfterKnock(){}
    fun refreshAfterEndGame(){}
    fun refreshAfterNextPlayer(){}
    fun refreshAfterUpdateScore(){}
}