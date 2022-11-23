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
        game.resetCurrentPlayer()
        game.resetPassCounter()

        onAllRefreshables { this.refreshAfterStartGame() }
    }

    fun playersSize(): Int {
        val game = rootService.currentGame
        if (game?.equals(null) == false) {
            return game.getPlayers().size
        }
        return 0
    }


    /**
     * Check if current player has already knocked, then check if all players have passed
     * If all players passed and number of cards in new stack is fewer than 3, end game
     * If all players passed and number of cards in new stack is not fewer than 3, call [replaceOpenCardsWithUnusedCards]
     * and reset passCounter to 0
     * Otherwise call next player
     *
     */
    fun nextPlayer() {
        val game = rootService.currentGame
        checkNotNull(game) { "No game is currently running." }

        if (game.nextPlayer().getHasKnocked()) {
            endGame()

//        } else if (game.getPassCounter() == game.getPlayers().size) {
//            if (game.getUnusedCards().size < 3) {
//                game.resetPassCounter()
//                endGame()
//                return
//            }
//            replaceOpenCardsWithUnusedCards(game.getOpenCards(), game.getUnusedCards())
//            game.resetPassCounter()
        } else {
            game.setCurrentPlayer(game.nextPlayer())
        }

        onAllRefreshables { this.refreshAfterNextPlayer() }
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


    private fun findWinners(players: MutableList<Player>): MutableList<Player> {
        var winners = players.sortedByDescending {player: Player -> player.getScore() }.toMutableList()
        return winners
    }

    /**
     * End game and show the winner in GUI
     *
     * @return winner
     */
    fun endGame() : MutableList<Player> {
        val game = rootService.currentGame
        checkNotNull(game) { "No game is currently running." }

        onAllRefreshables { this.refreshAfterEndGame(findWinners(game.getPlayers())) }
        return findWinners(game.getPlayers())
    }

}