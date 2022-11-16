package service

import entity.*

/**
 * Game service
 *
 * @property rootService
 * @constructor Create empty Game service
 */
class GameService(private val rootService: RootService) : AbstractRefreshingService() {

    /**
     * Set up and start new game, in which List of Players is initialized and Cards are distributed in separate stacks
     *
     * @param playerNames List of entered player names
     * @param allCards List of 32 shuffled cards to play with
     */
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

        onAllRefreshables { this.refreshAfterStartGame() }
    }

    /**
     * Creates a shuffled 32 cards list of all four suits and cards
     * from 7 to Ace
     */
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

    /**
     * End game and show the winner in GUI
     *
     * @return winner
     */
    fun endGame() {
        val game = rootService.currentGame
        checkNotNull(game) { "No game currently running." }

        onAllRefreshables { this.refreshAfterEndGame() }
        findWinner(game.getPlayers())
    }

}