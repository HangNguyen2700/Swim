package service

import entity.Card
import entity.CardSuit
import entity.CardValue
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

/**
 * Service test
 *
 * @constructor Create empty Service test
 */
class ServiceTest {

    private fun setUpGame(/*vararg refreshables: Refreshable*/): RootService {
        val rootService = RootService()
        assertNull(rootService.currentGame)

        val cards = mutableListOf<Card>(

            //Bob
            Card(CardSuit.CLUBS, CardValue.QUEEN), /*Card(CardSuit.SPADES, CardValue.SEVEN),*/
            Card(CardSuit.SPADES, CardValue.TEN),
            Card(CardSuit.DIAMONDS, CardValue.SEVEN),

            //Alice
            Card(CardSuit.CLUBS, CardValue.EIGHT),
            Card(CardSuit.CLUBS, CardValue.NINE),
            Card(CardSuit.HEARTS, CardValue.KING),

            //Ana
            Card(CardSuit.DIAMONDS, CardValue.QUEEN),
            Card(CardSuit.SPADES, CardValue.QUEEN),
            Card(CardSuit.DIAMONDS, CardValue.JACK),

            //Open Cards
            Card(CardSuit.SPADES, CardValue.SEVEN), /*Card(CardSuit.CLUBS, CardValue.QUEEN)*/
            Card(CardSuit.DIAMONDS, CardValue.KING),
            Card(CardSuit.DIAMONDS, CardValue.NINE),

            //Unused Cards
            Card(CardSuit.SPADES, CardValue.EIGHT),
            Card(CardSuit.HEARTS, CardValue.TEN),
            Card(CardSuit.HEARTS, CardValue.EIGHT),

            Card(CardSuit.CLUBS, CardValue.JACK),
            Card(CardSuit.HEARTS, CardValue.ACE),
            Card(CardSuit.SPADES, CardValue.NINE),

            Card(CardSuit.CLUBS, CardValue.ACE),
            Card(CardSuit.SPADES, CardValue.JACK),
            Card(CardSuit.HEARTS, CardValue.SEVEN),

            Card(CardSuit.CLUBS, CardValue.SEVEN),
            Card(CardSuit.CLUBS, CardValue.KING),
            Card(CardSuit.DIAMONDS, CardValue.EIGHT),

            Card(CardSuit.CLUBS, CardValue.TEN),
            Card(CardSuit.DIAMONDS, CardValue.TEN),
            Card(CardSuit.HEARTS, CardValue.JACK),

            Card(CardSuit.SPADES, CardValue.KING),
            Card(CardSuit.DIAMONDS, CardValue.ACE),
            Card(CardSuit.HEARTS, CardValue.QUEEN),

            Card(CardSuit.SPADES, CardValue.ACE),
            Card(CardSuit.HEARTS, CardValue.NINE),
        )
        rootService.gameService.startGame(mutableListOf("Bob", "Alice", "Ana"), cards)
        assertNotNull(rootService.currentGame)

        return rootService
    }

    /**
     * Test start game
     *
     */
    @Test
    fun testStartGame() {
        val game = setUpGame().currentGame!!

        println(game.getCurrentPlayer().getPlayerCards())
        println(game.getOpenCards().size)
        println(game.getUnusedCards().size)

        assertEquals("Alice", game.getPlayers()[1].getName())
        assertEquals(3, game.getOpenCards().size)
        assertEquals(20, game.getUnusedCards().size)

    }


    /**
     * Test exchange one card
     *
     */
    @Test
    fun testExchangeOneCard() {
        val rootService = setUpGame()
        val playerService = PlayerService(rootService)
        val game = rootService.currentGame!!

        //currentPlayer: Bob
        playerService.exchangeOneCard(2, 1)

        println(game.getCurrentPlayer().getPlayerCards())
        assertEquals(Card(CardSuit.DIAMONDS, CardValue.KING), game.getCurrentPlayer().getPlayerCards()[2])
        assertEquals(Card(CardSuit.DIAMONDS, CardValue.SEVEN), game.getOpenCards()[1])
    }

    /**
     * Test exchange all cards
     *
     */
    @Test
    fun testExchangeAllCards() {
        val rootService = setUpGame()
        val playerService = PlayerService(rootService)
        val game = rootService.currentGame!!

        //currentPlayer: Bob, because setupGame -> startGame -> currentPlayer starts always by Bob
        playerService.exchangeAllCards()

        assertEquals(Card(CardSuit.DIAMONDS, CardValue.KING), game.getCurrentPlayer().getPlayerCards()[1])
        assertEquals(Card(CardSuit.DIAMONDS, CardValue.SEVEN), game.getOpenCards()[2])
    }

    /**
     * Test next player
     *
     */
    @Test
    fun testNextPlayer(){
        val rootService = setUpGame()
        val playerService = PlayerService(rootService)
        val gameService = GameService(rootService)
        val game = rootService.currentGame!!

        //Bob
        gameService.nextPlayer()
        //Alice

        assertEquals("Alice",game.getCurrentPlayer().getName())
    }

    /**
     * Test pass
     *
     */
    @Test
    fun testPass() {
        val rootService = setUpGame()
        val playerService = PlayerService(rootService)
        val game = rootService.currentGame!!

        playerService.pass()
        assertEquals(1, game.getPassCounter())

        playerService.pass()
        playerService.pass()
        assertEquals(0, game.getPassCounter())
        assertEquals(
            mutableListOf<Card>(
                Card(CardSuit.SPADES, CardValue.EIGHT),
                Card(CardSuit.HEARTS, CardValue.TEN),
                Card(CardSuit.HEARTS, CardValue.EIGHT)
            ), game.getOpenCards()
        )
        assertEquals(
            mutableListOf<Card>(
                Card(CardSuit.CLUBS, CardValue.JACK),
                Card(CardSuit.HEARTS, CardValue.ACE),
                Card(CardSuit.SPADES, CardValue.NINE),

                Card(CardSuit.CLUBS, CardValue.ACE),
                Card(CardSuit.SPADES, CardValue.JACK),
                Card(CardSuit.HEARTS, CardValue.SEVEN),

                Card(CardSuit.CLUBS, CardValue.SEVEN),
                Card(CardSuit.CLUBS, CardValue.KING),
                Card(CardSuit.DIAMONDS, CardValue.EIGHT),

                Card(CardSuit.CLUBS, CardValue.TEN),
                Card(CardSuit.DIAMONDS, CardValue.TEN),
                Card(CardSuit.HEARTS, CardValue.JACK),

                Card(CardSuit.SPADES, CardValue.KING),
                Card(CardSuit.DIAMONDS, CardValue.ACE),
                Card(CardSuit.HEARTS, CardValue.QUEEN),

                Card(CardSuit.SPADES, CardValue.ACE),
                Card(CardSuit.HEARTS, CardValue.NINE)
            ), game.getUnusedCards()
        )

        //test pass -> endgame
    }

    /**
     * Test knock
     *
     */
    @Test
    fun testKnock(){
        val rootService = setUpGame()
        val playerService = PlayerService(rootService)
        val game = rootService.currentGame!!

        playerService.knock()
        assertEquals(true, game.getPlayers()[0].getHasKnocked())

    }

    /**
     * Test end game
     *
     */
    @Test
    fun testEndGame(){
        val rootService = setUpGame()
        val playerService = PlayerService(rootService)
        val gameService = GameService(rootService)
        val game = rootService.currentGame!!

        playerService.pass()
        gameService.nextPlayer()
        playerService.pass()
        gameService.nextPlayer()
        playerService.pass()

        val end = gameService.endGame() // list of player
        end.forEach{player -> println(player.getName() + player.getScore())}
    }

}