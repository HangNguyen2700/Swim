package entity

import kotlin.test.*

/**
 * Test cases for [Game]
 */

class GameTest {
    private val c1 = Card(CardSuit.SPADES, CardValue.ACE)
    private val c2 = Card(CardSuit.CLUBS, CardValue.JACK)
    private val c3 = Card(CardSuit.HEARTS, CardValue.QUEEN)
    private val c4 = Card(CardSuit.DIAMONDS, CardValue.EIGHT)

    private val game = Game(0, listOf(c1,c2,c3), listOf(c4))

    /**
     * Test if entered data in [Game]object is valid
     */

    @Test
    fun testInitializedGame(){
        assertEquals(0, game.passCounter)
        assertEquals(listOf(c1,c2,c3), game.openCards)
        assertEquals(listOf(c4), game.unusedCards)
    }


}