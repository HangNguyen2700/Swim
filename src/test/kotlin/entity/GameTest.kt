package entity

import kotlin.test.*

/**
 * Test cases for [Game]
 *
 * @constructor Create empty Game test
 */
class GameTest {
    private val playerCards1 = mutableListOf<Card>(
        Card(CardSuit.CLUBS, CardValue.QUEEN),
        Card(CardSuit.SPADES, CardValue.TEN),
        Card(CardSuit.DIAMONDS, CardValue.SEVEN),
    )
    private val player1 = Player("Bob", playerCards1)

    private val playerCards2 = mutableListOf<Card>(
        Card(CardSuit.CLUBS, CardValue.EIGHT),
        Card(CardSuit.CLUBS, CardValue.NINE),
        Card(CardSuit.HEARTS, CardValue.KING),
    )
    private val player2 = Player("Alice", playerCards2)

    private val playerCards3 = mutableListOf<Card>(
        Card(CardSuit.DIAMONDS, CardValue.QUEEN),
        Card(CardSuit.SPADES, CardValue.QUEEN),
        Card(CardSuit.DIAMONDS, CardValue.JACK),
    )
    private val player3 = Player("Ana", playerCards3)

    private val players = mutableListOf<Player>(player1, player2, player3)

    private val openCards = mutableListOf<Card>(
        Card(CardSuit.SPADES, CardValue.SEVEN),
        Card(CardSuit.DIAMONDS, CardValue.KING),
        Card(CardSuit.DIAMONDS, CardValue.NINE),
    )

    private val unusedCards = mutableListOf<Card>(
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
    )

    private val game = Game(players, openCards, unusedCards)

    /**
     * Test get players
     *
     */
    @Test
    fun testGetPlayers(){
        assertEquals(mutableListOf(player1, player2, player3), game.getPlayers())
    }

    /**
     * Test get current player
     *
     */
    @Test
    fun testGetCurrentPlayer(){
        assertEquals(player1, game.getCurrentPlayer())
    }

    /**
     * Test set current player
     *
     */
    @Test
    fun testSetCurrentPlayer(){
        game.setCurrentPlayer(player3)
        assertEquals(player3, game.getCurrentPlayer())
    }

    /**
     * Test reset current player
     *
     */
    @Test
    fun testResetCurrentPlayer(){
        game.resetCurrentPlayer()
        assertEquals(player1, game.getCurrentPlayer())
    }

    /**
     * Test next player
     *
     */
    @Test
    fun testNextPlayer(){
        assertEquals(player2, game.nextPlayer())
    }

    /**
     * Test get pass counter
     *
     */
    @Test
    fun testGetPassCounter(){
        assertEquals(0, game.getPassCounter())
    }

    /**
     * Test increase pass counter
     *
     */
    @Test
    fun testIncreasePassCounter(){
        game.increasePassCounter()
        game.increasePassCounter()
        assertEquals(2, game.getPassCounter())
    }

    /**
     * Test reset pass counter
     *
     */
    @Test
    fun testResetPassCounter(){
        game.resetPassCounter()
        assertEquals(0, game.getPassCounter())
    }

    /**
     * Test get open cards
     *
     */
    @Test
    fun testGetOpenCards(){
        assertEquals(openCards, game.getOpenCards())
    }

    /**
     * Test get unuesdcards
     *
     */
    @Test
    fun testGetUnuesdcards(){
        assertEquals(unusedCards, game.getUnusedCards())
    }
}