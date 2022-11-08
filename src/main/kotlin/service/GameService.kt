package service

import entity.*


class GameService(private val rootService: RootService) : AbstractRefreshingService() {

    fun startGame(
        playerNames: MutableList<String>,
        allCards: MutableList<Card> = defaultRandomCardList() as MutableList<Card>
    ) {
        var players: MutableList<Player> = emptyList<Player>().toMutableList()
        var startStack = 0;
        var endStack = 2;

        for (playerName in playerNames) {
            players.add(Player(playerName, allCards.slice(startStack..endStack) as MutableList<Card>))
            startStack += 3
            endStack += 3
        }

        var openCards = allCards.slice(startStack..endStack) as MutableList<Card>
        startStack += 3
        var unusedCards = allCards.slice(startStack..31) as MutableList<Card>

        var game = Game(players, openCards, unusedCards)
        rootService.currentGame = game

//        onAllRefreshables { refreshAfterStartNewGame() }
    }

    private fun defaultRandomCardList() = List(32) { index ->
        Card(
            CardSuit.values()[index / 8],
            CardValue.values()[(index % 8) + 5]
        )
    }.shuffled()


    private fun findWinner(players: List<Player>): Player? {
        var winner = players.maxByOrNull { player: Player -> player.getScore() }
        return winner
    }

    fun endGame(): Player? {
        val game = rootService.currentGame
        checkNotNull(game) { "No game currently running." }

        return findWinner(game.getPlayers())
    }

}