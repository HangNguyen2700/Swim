package entity

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * Test cases for [Player]
 *
 * @constructor Create empty Player test
 */

class PlayerTest {
    private val cards = mutableListOf<Card>(
        Card(CardSuit.CLUBS, CardValue.QUEEN),
        Card(CardSuit.SPADES, CardValue.TEN),
        Card(CardSuit.DIAMONDS, CardValue.SEVEN)
    )

    private val player = Player("Alice", cards)

    /**
     * Test get name
     *
     */
    @Test
    fun testGetName(){
        assertEquals("Alice", player.getName())
    }

    /**
     * Test get score
     *
     */
    @Test
    fun testGetScore(){
        assertEquals(0.0, player.getScore())
    }

    /**
     * Test set score
     *
     */
    @Test
    fun testSetScore(){
        assertEquals(12.0, player.setScore(12.0))
        assertEquals(12.0, player.getScore())
    }

    /**
     * Test has knocked
     *
     */
    @Test
    fun testHasKnocked(){
        assertEquals(false, player.getHasKnocked())
    }

    /**
     * Test set has knocked
     *
     */
    @Test
    fun testSetHasKnocked(){
        assertEquals(true, player.setHasKnocked())
        assertEquals(true, player.getHasKnocked())
    }

    /**
     * Test get player cards
     *
     */
    @Test
    fun testGetPlayerCards(){
        assertEquals(cards, player.getPlayerCards())
    }
}