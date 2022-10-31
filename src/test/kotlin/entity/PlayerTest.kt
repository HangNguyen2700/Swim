package entity

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * Test cases for [Player]
 */

class PlayerTest {
    private val playerName = ""
    private val playerScore = 0.0
    private val playerHasKnocked = false

    private val player = Player(playerName, playerScore, playerHasKnocked )

    /**
     * Test if entered data in [Player]object is valid
     */

    @Test
    fun testInitializedPlayer(){
        assertEquals(playerName, player.name)
        assertEquals(playerScore, player.score)
        assertEquals(playerHasKnocked, player.hasKnocked)
    }
}